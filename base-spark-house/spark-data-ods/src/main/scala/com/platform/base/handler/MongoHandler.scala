package com.platform.base.handler

import java.util

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.platform.base.db.CommonDB
import com.platform.base.utils.{DateTimeUtils, HandlerUtils}
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

case class MongoHandler() {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def handler(sparkSession: SparkSession) = {
    //校验MongoOds上次执行结果。
    val mongoOdsNotSuccCount = new CommonDB().getMysqlOrMongoOdsNotSuccCount("mongo_ods")
    //全部执行成功
    if(mongoOdsNotSuccCount==0){
      var break = false
      //校验ods config mongo上次执行结果
      val diffDay = new CommonDB().getLastDayDiffNow("mongo")
      //查询上次执行是否是昨天
      if(diffDay!=0){
        //重置所有任务状态
        new CommonDB().updateAllStatus("mongo")
      }else{
        //今天是否全部成功
        val notSuccCount = new CommonDB().getOdsNotSuccCount("mongo")
        if(notSuccCount==0){
          break = true
        }
      }

      if (!break){
        val configArray = new CommonDB().getOdsConfig("mongo")
        var i = 0
        var status = "succ"
        var flag = true
        for (i <- 0 until configArray.size() if flag){
          val jsonObj = configArray.get(i).asInstanceOf[JSONObject]
          val mongoOdsObj = new CommonDB().getMongoOrMysqlOdsConfigById(jsonObj.getString("ods_id"),"mongo_ods")
          val odsStartTime = DateTimeUtils.getCurrentTime
          val hiveTableName = jsonObj.getString("hive_table")
          val hdfsUrl = mongoOdsObj.getString("hdfs_url")
          val fields = JSON.parseArray(jsonObj.getString("columns_value"))
          try{
            createDwTmpTableExists(sparkSession,jsonObj,mongoOdsObj,fields)
            createOdsTableExists(sparkSession,jsonObj,mongoOdsObj,fields)
            loadTableData(sparkSession,jsonObj,mongoOdsObj)
          }catch {
            case e:Exception=>{
              logger.error("mongo handler from hdfs error: hiveTableName"+hiveTableName.toString+" hdfsUrl:"+hdfsUrl,e)
              status = "fail"
              flag = false
            }
          }
          val odsEndTime = DateTimeUtils.getCurrentTime
          new CommonDB().updateStatus(odsStartTime,odsEndTime,jsonObj.getString("id"),status)
        }
      }else{
        logger.warn("----------------------------Not execute base-data-ods: today mongo all succ--------------------------------")
      }
    }else{
      logger.warn("--------------------Not execute base-data-ods:mongo to hdfs not all succ-------------------------")
    }
  }

  def createDwTmpTableExists(sparkSession: SparkSession,jsonObj: JSONObject,mongoOdsObj:JSONObject,fields:JSONArray) = {
    //判断是否增量
    val hdfsUrl = mongoOdsObj.getString("hdfs_url")
    val increment = mongoOdsObj.getString("increment")
    val partitionValue = mongoOdsObj.getString("partition_value")
    val fullTable = new StringBuffer(jsonObj.getString("hive_tmp_db")).append(".").append(jsonObj.getString("hive_tmp_table")).toString
    if(increment.equals("true")){
      //判断是否存在表。
      val hiveTmpDb = jsonObj.getString("hive_tmp_db")
      sparkSession.sql("use "+hiveTmpDb)
      val hive_tmp_table = jsonObj.getString("hive_tmp_table")
      val exists = sparkSession.catalog.tableExists(hive_tmp_table)
      if(!exists){
         createDwTmpTable(sparkSession,jsonObj,mongoOdsObj,fields)
      }
      //增量添加分区
      val partitionSql =  new HandlerUtils().createAddPartitionSql(fullTable,partitionValue,hdfsUrl)
      val dropPartitionSql = new HandlerUtils().createDropPartitionSql(fullTable,partitionValue)
      sparkSession.sql(dropPartitionSql)
      sparkSession.sql(partitionSql)
    }else{
      createDwTmpTable(sparkSession,jsonObj,mongoOdsObj,fields)
    }
  }


  def createOdsTableExists(sparkSession: SparkSession,jsonObj: JSONObject,mongoOdsObj:JSONObject,fields:JSONArray) = {
    val increment = mongoOdsObj.getString("increment")
    if(increment.equals("true")){
      val hiveDb = jsonObj.getString("hive_db")
      sparkSession.sql("use "+hiveDb)
      val hive_tmp_table = jsonObj.getString("hive_table")
      val exists = sparkSession.catalog.tableExists(hive_tmp_table)
      if(!exists){
        createOdsTable(sparkSession,jsonObj,fields)
      }
    }else{
      createOdsTable(sparkSession,jsonObj,fields)
    }
  }

  def createDwTmpTable(sparkSession: SparkSession,jsonObj: JSONObject,mongoOdsObj:JSONObject,fields:JSONArray): Unit ={
    val hdfsUrl = mongoOdsObj.getString("hdfs_url")
    val increment = mongoOdsObj.getString("increment")

    val fullTable = new StringBuffer(jsonObj.getString("hive_tmp_db")).append(".").append(jsonObj.getString("hive_tmp_table")).toString
    val createSql = new StringBuffer("CREATE EXTERNAL TABLE ").append(fullTable).append("(")
    var i = 0
    val keyObj = new JSONObject()
    for (i <-0 until fields.size()){
      val columnName = fields.get(i).toString

      if (columnName.startsWith("_")){
        keyObj.put("mongo"+columnName,columnName)
        createSql.append("mongo"+columnName).append(" STRING")
      }else{
        keyObj.put(columnName,columnName)
        createSql.append(columnName).append(" STRING")
      }

      if (i!=(fields.size()-1)){
        createSql.append(",")
      }
    }

    createSql.append(") ROW FORMAT SERDE 'com.mongodb.hadoop.hive.BSONSerDe' ")
    createSql.append(" WITH SERDEPROPERTIES('mongo.columns.mapping'='")
    createSql.append(keyObj.toJSONString)
    createSql.append("')")
    createSql.append(" STORED AS INPUTFORMAT 'com.mongodb.hadoop.mapred.BSONFileInputFormat' ")
    createSql.append(" OUTPUTFORMAT 'com.mongodb.hadoop.hive.output.HiveBSONFileOutputFormat' ")
    //如果是全量
    if(increment.equals("false")){
      val dropSql = new StringBuffer("DROP TABLE IF EXISTS ").append(fullTable).toString()
      sparkSession.sql(dropSql)
    }else{
      createSql.append(" PARTITIONED BY (partition_time string) ")
    }
    createSql.append(" LOCATION '")
    createSql.append(hdfsUrl)
    createSql.append("'")
    sparkSession.sql(createSql.toString())

  }


  def createOdsTable(sparkSession: SparkSession,jsonObj: JSONObject,fields:JSONArray)={
    val fullTable = new StringBuffer(jsonObj.getString("hive_db")).append(".").append(jsonObj.getString("hive_table")).toString
    val createSql = new StringBuffer("CREATE TABLE ").append(fullTable).append("(")
    var i = 0
    for (i <-0 until fields.size()){
      val columnName = fields.get(i).toString
      if (columnName.startsWith("_")){
        createSql.append("mongo"+columnName).append(" STRING")
      }else{
        createSql.append(columnName).append(" STRING")
      }
      if (i!=(fields.size()-1)){
        createSql.append(",")
      }
    }
    createSql.append(") PARTITIONED BY (partition_time string)  STORED AS PARQUET TBLPROPERTIES('parquet.compression'='SNAPPY')")

    val dropSql = new StringBuffer("DROP TABLE IF EXISTS ").append(fullTable).toString()

    sparkSession.sql(dropSql)
    sparkSession.sql(createSql.toString)
  }


  def loadTableData(sparkSession: SparkSession,jsonObj: JSONObject,mongoOdsObj:JSONObject) = {
    val increment = mongoOdsObj.getString("increment")
    val partitionValue = mongoOdsObj.getString("partition_value")
    var loadSql = ""
    if (increment.equals("true")){
      loadSql = new HandlerUtils().loadSqlIncrement(jsonObj,partitionValue)
    }else{
      loadSql = new HandlerUtils().loadSqlFull(jsonObj)
    }
    sparkSession.sql(loadSql)
  }

}
