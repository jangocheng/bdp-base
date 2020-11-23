package com.platform.migration

import java.util

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.WriteConfig
import com.platform.migration.common.singleton.{HbaseConfSingleton, SparkConfSingleton, SparkSessionSingleton}
import com.platform.migration.common.util.HbaseScanUtils
import com.platform.migration.entity.vo.StepParamVO
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}


object SyncTrackStart {

  def main(args: Array[String]): Unit = {
    val uri = "mongodb://track:aGtu2MigEF4VQhAn@dds-wz916e942d4ca8341.mongodb.rds.aliyuncs.com:3717/?authSource=track"
    //val uri = "mongodb://root:baseconn.88@192.168.84.48:27017/?authSource=track"


    val sparkConf: SparkConf = SparkConfSingleton.getInstance()
    val sparkSession: SparkSession = SparkSessionSingleton.getInstance(sparkConf)


    syncDeviceInfo(sparkSession, uri)
    syncDeviceLocation(sparkSession, uri)
    syncAppCall(sparkSession, uri)
    SyncAppContact(sparkSession, uri)

    sparkSession.stop()
  }

  def syncAppCall(sparkSession: SparkSession, uri: String) = {
    val sql = "select unix_timestamp(createTime,'yyyyMMddHHmmss')*1000 as createTime, globalUserId,userid as cardId,null as userName, mobile, stage,null as relId, contactName, startContactTime, contactDuration, contactType, phoneNumber,token from base_data.app_call where partition_month>'201910' and createtime<'20200513110000'"
    val df = sparkSession.sql(sql)


    val writeConf = WriteConfig(
      Map(
        "spark.mongodb.output.uri" -> uri,
        "spark.mongodb.output.database" -> "track",
        "spark.mongodb.output.collection" -> "app_call_tmp"))

    df.show(5)

    MongoSpark.save(df, writeConf)
  }

  def syncDeviceInfo(sparkSession: SparkSession, uri: String) = {
    val sql = "SELECT unix_timestamp(createTime, 'yyyyMMddHHmmss') * 1000 AS createTime, globalUserId , userid AS cardId, NULL AS userName, mobile, stage, relId , type, osVersion, resolution, language, ssid , bssid, deviceName, moduleName, mcc, mnc , version, channel, deviceNum, manufacturer, product , cpu, cpuInfo, wlanMac, btMac, `time` , iActiveNetType, ip, sdFreeSize, sdAllSize, phoneType , phoneStatus, cpuSerialNum, carrier, phoneState, phoneLocation , density, imsi, appId, openUdId, uuid , idfv, fcuuid, idfa, hasSupportTouchId, serialNo , androidId, orientation, hasDebug, linkSpeed, realIp FROM base_data.device_info WHERE partition_month > '201910' AND createtime < '20200513110000'"
    val df = sparkSession.sql(sql)

    val writeConf = WriteConfig(
      Map(
        "spark.mongodb.output.uri" -> uri,
        "spark.mongodb.output.database" -> "track",
        "spark.mongodb.output.collection" -> "device_info_tmp"))

    df.show(5)

    MongoSpark.save(df, writeConf)

  }

  def syncDeviceLocation(sparkSession: SparkSession, uri: String) = {
    val sql = "SELECT unix_timestamp(createTime, 'yyyyMMddHHmmss') * 1000 AS createTime, globalUserId , userid AS cardId, NULL AS userName, mobile, stage, relId , longitude, latitude, province, city, district , street, streetNumber, lbsLongitude, lbsLatitude, address , adCode, altitude, radius, indoorLocMode, locationDescribe , poiList FROM base_data.device_location WHERE partition_month > '201910' AND createtime < '20200513110000'"
    val df = sparkSession.sql(sql)


    val writeConf = WriteConfig(
      Map(
        "spark.mongodb.output.uri" -> uri,
        "spark.mongodb.output.database" -> "track",
        "spark.mongodb.output.collection" -> "device_location_tmp"))

    df.show(5)

    MongoSpark.save(df, writeConf)


  }


  def SyncAppContact(sparkSession: SparkSession, uri: String) = {

    val params: util.List[StepParamVO] = getParams

    //初始化Hbase配置
    val hBaseConf = HbaseConfSingleton.getInstance()
    HbaseScanUtils.fillParam(hBaseConf, params)

    //读取数据
    val rdd = sparkSession.sparkContext.newAPIHadoopRDD(hBaseConf,
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result])

    val tableSchema: String = getTaskSchema

    //rdd转换DataFrame
    val df = rddToDataFrame(sparkSession, rdd, tableSchema)

    df.createTempView("tmp_app_contact_yhq")
    val newDF = sparkSession.sql("SELECT unix_timestamp(createTime, 'yyyyMMddHHmmss') * 1000 AS createTime, globalUserId , userid AS cardId, NULL AS userName, mobile, stage, NULL AS relId , phoneName, phoneNumber, phoneType, timeContacted, lastTimeContacted FROM tmp_app_contact_yhq WHERE substr(createTime, 1, 6) > '201910' AND createTime < '20200513110000'")

    //保存数据
    val writeConf = WriteConfig(
      Map(
        "spark.mongodb.output.uri" -> uri,
        "spark.mongodb.output.database" -> "track",
        "spark.mongodb.output.collection" -> "app_contact_tmp"))

    newDF.show(5)

    MongoSpark.save(newDF, writeConf)

  }


  private def rddToDataFrame(sparkSession: SparkSession, rdd: RDD[(ImmutableBytesWritable, Result)], tableSchemaStr: String): DataFrame = {
    //定义row的StructType
    val tableSchema: StructType = DataType.fromJson(tableSchemaStr).asInstanceOf[StructType]

    //rdd 转成row
    val keys: util.List[String] = new util.ArrayList[String]()
    tableSchema.foreach(column => {
      keys.add(column.name)
    })

    val rowRDD: RDD[Row] = rdd.map(record => {

      val cells = record._2.rawCells()
      val map: util.HashMap[String, String] = new util.HashMap()

      cells.foreach(cell => {
        val qualifier = new String(cell.getQualifierArray, cell.getQualifierOffset, cell.getQualifierLength)
        val value = new String(cell.getValueArray, cell.getValueOffset, cell.getValueLength)
        map.put(qualifier, value)
      })

      val values: Array[String] = new Array[String](keys.size())
      for (index <- 0 until keys.size()) {
        values(index) = map.get(keys.get(index))
      }

      Row.fromSeq(values)

    })

    //rdd转化DataFrame
    sparkSession.sqlContext.createDataFrame(rowRDD, tableSchema)
  }


  def getParams: util.List[StepParamVO] = {
    val step = new StepParamVO()
    step.setParamName("hbase.mapreduce.inputtable")
    step.setParamValue("APP_CONTACT")
    util.Arrays.asList(step)
  }

  def getTaskSchema: String = {
    val taskSchema: String = "{\"type\":\"struct\",\"fields\":[{\"nullable\":true,\"name\":\"createTime\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"dataType\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"deviceNum\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"firstRecordTime\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"lastRecordTime\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"lastTimeContacted\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"phoneName\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"phoneNumber\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"phoneType\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"stage\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"timeContacted\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"token\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"userId\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"globalUserId\",\"type\":\"string\"},{\"nullable\":true,\"name\":\"mobile\",\"type\":\"string\"}]}"
    taskSchema
  }

}
