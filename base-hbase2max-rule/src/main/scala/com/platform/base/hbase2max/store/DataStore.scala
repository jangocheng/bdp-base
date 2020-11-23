package com.platform.hbase2max.store

import com.alibaba.fastjson.JSONObject
import com.platform.hbase2max.common.utils.{DateTimeUtils, HbaseConfUtils}
import com.platform.hbase2max.db.MaxComputeDB
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkContext
import org.slf4j.{Logger, LoggerFactory}

case class DataStore() {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  /**
    * 处理全量数据
    *
    * @param sparkContext
    * @param tableNames
    * @param hbaseTableNames
    * @param lowerTimes
    */
  def handleAll(sparkContext: SparkContext, tableNames: Array[String], hbaseTableNames: Array[String], lowerTimes: Array[String]): Unit = {
    for (index <- lowerTimes.indices) {

      val tableName = tableNames(index)
      val hbaseTableName = hbaseTableNames(index)
      val lowerTime: String = lowerTimes(index)
      var upper = DateTimeUtils.getYesterday

      while (!lowerTime.equals(DateTimeUtils.getBizDate(upper))) {

        val pt = DateTimeUtils.getBizDate(upper.minusDays(1))
        val timer = DateTimeUtils.getDayRange(upper)
        val start = timer.getOrElse("start", "0")
        val end = timer.getOrElse("end", "0")
        logger.info(s"============================>hbaseTable:$hbaseTableName\tmaxcomputeTable:$tableName\tpt:$pt\tstart:$start\tend:$end")
        handle(sparkContext, tableName, hbaseTableName, pt, start, end)
        upper = upper.minusDays(1)
      }
    }
  }

  /**
    * 处理增量数据
    *
    * @param sparkContext
    * @param tableName
    * @param hbaseTableName
    * @param pt
    * @param start
    * @param end
    */
  def handle(sparkContext: SparkContext, tableName: String, hbaseTableName: String, pt: String, start: String, end: String) = {
    //初始化hbase配置
    val hbaseConf = HbaseConfUtils.initHbaseConfiguration()
    hbaseConf.set(TableInputFormat.INPUT_TABLE, hbaseTableName)
    hbaseConf.set(TableInputFormat.SCAN_TIMERANGE_START, start)
    hbaseConf.set(TableInputFormat.SCAN_TIMERANGE_END, end)

    //读取数据
    val rdd = sparkContext.newAPIHadoopRDD(hbaseConf,
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result])

    //数据处理
    val result = rdd.map(element => {
      val cells = element._2.rawCells()
      val jsonObject = new JSONObject()
      for (cell <- cells) {
        val qulifier = new String(cell.getQualifierArray, cell.getQualifierOffset, cell.getQualifierLength)
        val value = new String(cell.getValueArray, cell.getValueOffset, cell.getValueLength)
        jsonObject.put(qulifier, value)

      }
      jsonObject
    })

    //保存数据
    MaxComputeDB().saveData(sparkContext, result, tableName, pt)

  }

}
