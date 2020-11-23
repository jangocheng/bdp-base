package com.platform.base.handler

import java.util

import com.alibaba.fastjson.JSONObject
import com.platform.base.db.CommonDB
import com.platform.base.utils.DateTimeUtils

import org.apache.spark.sql.{Column, SaveMode, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.slf4j.{Logger, LoggerFactory}

case class CommonHandler() {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def handler(sparkSession:SparkSession) = {
    //校验dwd执行结果
    val dwdNotSuccCount = new CommonDB().getDwdNotSuccCount()
    val odsNotSuccCount = new CommonDB().getOdsNotSuccCount()
    if(dwdNotSuccCount==0 || odsNotSuccCount==0){
      val diffDay = new CommonDB().getLastDayDiffNow()
      var break = false
      //查询上次执行是否是昨天
      if(diffDay!=0){
        //重置所有任务状态
        new CommonDB().updateAllStatus()
      }else{
        //今天是否全部成功
        val notSuccCount = new CommonDB().getDimNotSuccCount()
        if(notSuccCount==0){
          break = true
        }
      }

      //今天全部成功，即不执行
      if (!break){
        //获取dim config
        val dimConfig =  new CommonDB().getDimConfig()
        var i = 0
        var status = "succ"
        var flag = true
        var dimObj = new JSONObject()
        for (i <-0 until dimConfig.size() if flag){
          dimObj = dimConfig.getJSONObject(i)
          val dimConfigStartTime = DateTimeUtils.getCurrentTime

          try{
            val df = sparkSession.sql(dimObj.getString("sql"))
            df.persist(StorageLevel.MEMORY_AND_DISK)
            val fullTable = new StringBuffer(dimObj.getString("hive_db")).append(".").append(dimObj.getString("hive_table")).toString
            // 创建临时表
            import scala.collection.JavaConverters._
            val columns = new util.ArrayList[String](df.columns.toList.asJava)
            columns.add("id")
            createTable(sparkSession,fullTable,columns)
            import org.apache.spark.sql.functions._
            //分区
            val partitionTime = () =>{
              DateTimeUtils.getYesterday
            }
            val addPartitionCol = udf(partitionTime)

            //主键ID
            val id = () =>{
              java.util.UUID.randomUUID.toString.replace("-","")
            }
            val addId = udf(id)

            df.withColumn("id",addId()).withColumn("partition_time",addPartitionCol()).write.mode(SaveMode.Overwrite).insertInto(fullTable)
            //清除缓存
            df.unpersist()
          }catch {
            case e:Exception=>{
              logger.error("dim handler from ods error dimConfig===>"+dimObj.toJSONString,e)
              status = "fail"
              flag = false
            }
          }

          val dimConfigEndTime = DateTimeUtils.getCurrentTime

          //更新单个job的某一个执行计划的状态
          new CommonDB().updateStatus(dimConfigStartTime,dimConfigEndTime,dimObj.getString("id"),status)

          if(!flag){
            throw new Exception("程序异常")
          }
        }
      }else{
        logger.warn("--------------------Not execute base-data-dim: today all succ-------------------------")
      }
    }else{
      logger.warn("--------------------Not execute base-data-dim: base-data-dwd or base-data-ods not all succ-------------------------")
    }
  }

  def createTable(sparkSession: SparkSession,fullTable: String,fields:util.List[String])={
    val createSql = new StringBuffer("CREATE TABLE ").append(fullTable).append("(")
    var i = 0
    for (i <-0 until fields.size()){
      createSql.append(fields.get(i)).append(" STRING")
      if (i!=(fields.size()-1)){
        createSql.append(",")
      }
    }
    createSql.append(") PARTITIONED BY (partition_time string)  STORED AS PARQUET TBLPROPERTIES('parquet.compression'='SNAPPY')")
    val dropSql = new StringBuffer("DROP TABLE IF EXISTS ").append(fullTable).toString()
    sparkSession.sql(dropSql)
    sparkSession.sql(createSql.toString)
  }




}
