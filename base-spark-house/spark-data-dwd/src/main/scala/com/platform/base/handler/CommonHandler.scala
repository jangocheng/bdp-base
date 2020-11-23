package com.platform.base.handler

import java.util

import com.alibaba.fastjson.JSONObject
import com.platform.base.db.CommonDB
import com.platform.base.utils.DateTimeUtils

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.slf4j.{Logger, LoggerFactory}

case class CommonHandler() {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def handler(sparkSession:SparkSession) = {
    //校验ods执行结果
    val odsNotSuccCount = new CommonDB().getOdsNotSuccCount()
    if(odsNotSuccCount==0){
      val diffDay = new CommonDB().getLastDayDiffNow()
      var break = false
      //查询上次执行是否是昨天
      if(diffDay!=0){
        //重置所有执行计划状态
        new CommonDB().updateAllStatus("dwd_execute")
        new CommonDB().updateAllStatus("dwd_config")
      }else{
        //今天是否全部成功
        val notSuccCount = new CommonDB().getDwdNotSuccCount()
        if(notSuccCount==0){
          break = true
        }
      }

      //今天全部成功，即不执行
      if(!break){
        //获取dwd config
        val dwdConfig =  new CommonDB().getDwdConfig()
        //获取项目名array
        val projectNameArray = dwdConfig.keySet().toArray()

        projectNameArray.foreach(projectName=>{
          //获取单个项目所有JOB任务
          val projectNameConfig = dwdConfig.getJSONArray(projectName.asInstanceOf[String])
          //循环获取单个JOB任务
          var flag = true
          for (i <-0 until projectNameConfig.size() if flag){
            val id = projectNameConfig.get(i).asInstanceOf[JSONObject].getString("id")
            //获取单个JOB任务的多个执行计划任务
            val dwdExecuteArray = new CommonDB().getDwdExecuteConfig(id)
            val dwdConfigStartTime = DateTimeUtils.getCurrentTime
            var status = "succ"
            //循环获取单个JOB任务的每一步执行任务
            for (j <-0 until dwdExecuteArray.size() if flag){
              val executeStartTime = DateTimeUtils.getCurrentTime
              val dwdExecute = dwdExecuteArray.get(j).asInstanceOf[JSONObject]
              try{
                val df = sparkSession.sql(dwdExecute.getString("sql"))
                df.persist(StorageLevel.MEMORY_AND_DISK)
                //判断存储级别，ALL 全部， TABLE，只存放在table中，VIEW只存放在view中
                if(dwdExecute.getString("storage").equals("ALL") || dwdExecute.getString("storage").equals("TABLE")){
                  val fullTable = new StringBuffer(dwdExecute.getString("hive_db")).append(".").append(dwdExecute.getString("hive_table")).toString
                  val isGenerateId = dwdExecute.getBoolean("generate_id")
                  // 创建临时表
                  import scala.collection.JavaConverters._
                  createTable(sparkSession,fullTable,isGenerateId,df.columns.toList.asJava)
                  import org.apache.spark.sql.functions._
                  val partitionTime = () =>{
                    DateTimeUtils.getYesterday
                  }
                  val addPartitionCol = udf(partitionTime)
                  //若需要生成ID
                  if (isGenerateId) {
                    df.withColumn("id", monotonically_increasing_id)
                      .withColumn("partition_time",addPartitionCol()).write.mode(SaveMode.Overwrite).insertInto(fullTable)
                  } else {
                    df.withColumn("partition_time",addPartitionCol()).write.mode(SaveMode.Overwrite).insertInto(fullTable)
                  }

                  if(dwdExecute.getString("storage").equals("ALL")){
                    df.createTempView(dwdExecute.getString("tmp_view"))
                  }else{
                    //非最后一步，存储在table中，df即被释放
                    if (j!=(dwdExecuteArray.size()-1)){
                      df.unpersist()
                    }
                  }
                }else{
                  df.createTempView(dwdExecute.getString("tmp_view"))
                }

                //最后一步清空df
                if (j==(dwdExecuteArray.size()-1)){
                  df.unpersist()
                }
              }catch {
                case e:Exception=>{
                  logger.error("dwd handler from ods error dwdExecute===>"+dwdExecute.toJSONString,e)
                  status = "fail"
                  flag = false
                }
              }
              val executeEndTime = DateTimeUtils.getCurrentTime
              //更新单个job的某一个执行计划的状态
              new CommonDB().updateStatus(executeStartTime,executeEndTime,dwdExecute.getString("id"),status,"dwd_execute")
            }
            val dwdConfigEndTime = DateTimeUtils.getCurrentTime
            //更新单个JOB的执行状态
            new CommonDB().updateStatus(dwdConfigStartTime,dwdConfigEndTime,id,status,"dwd_config")
          }

          if(!flag){
            throw new Exception("程序异常")
          }
        })
      }else{
        logger.warn("--------------------Not execute base-data-dwd: today all succ-------------------------")
      }
    }else{
      logger.warn("--------------------Not execute base-data-dwd: base-data-ods not all succ-------------------------")
    }
  }

  def createTable(sparkSession: SparkSession,fullTable: String,isGenerateId:Boolean,fields:util.List[String]): DataFrame ={
    val createSql = new StringBuffer("CREATE TABLE ").append(fullTable).append("(")

    for (i <-0 until fields.size()){
      createSql.append(fields.get(i)).append(" STRING")
      if (i!=(fields.size()-1)){
        createSql.append(",")
      }
    }
    if (isGenerateId) {
      createSql.append(",").append("id").append(" BIGINT")
    }
    createSql.append(") PARTITIONED BY (partition_time string)  STORED AS PARQUET TBLPROPERTIES('parquet.compression'='SNAPPY')")
    val dropSql = new StringBuffer("DROP TABLE IF EXISTS ").append(fullTable).toString()
    sparkSession.sql(dropSql)
    sparkSession.sql(createSql.toString)
  }


}
