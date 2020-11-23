package com.platform.migration

import com.platform.migration.common.singleton.{SparkConfSingleton, SparkSessionSingleton}
import com.platform.migration.common.util.TaskParserUtils
import com.platform.migration.db.HiveDataDB
import com.platform.migration.entity.TaskSchema
import com.platform.migration.process.TaskProcessing
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

object MigrationStart {

  private val logger: Logger = LoggerFactory.getLogger(MigrationStart.getClass)

  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME", "hive")

    //启动配置提示
    promptStartConf()

    //参数验证
    validParams(args)

    //初始化数据
    val taskPathList = args(0).split(",")
    val sparkConf: SparkConf = SparkConfSingleton.getInstance()
    val sparkSession: SparkSession = SparkSessionSingleton.getInstance(sparkConf)

    taskPathList.foreach(taskPath => {
      val taskSchema: TaskSchema = TaskParserUtils.fileToTaskSchema(taskPath)
      try {
        //执行任务
        val df = TaskProcessing().process(taskSchema, sparkSession)
        //保存数据
        HiveDataDB().save(sparkSession, taskSchema, df)
        //删除当前任务中的临时数据
        dropTempView(sparkSession, taskSchema)

      } catch {
        case ex: Exception =>
          logger.error(taskSchema.getTaskName + s"任务执行失败。。。\n error message:$ex")
      }
    })

    sparkSession.stop()

  }


  def promptStartConf(): Unit = {
    val os = System.getProperty("os.name")
    if (os.contains("Windows")) {
      logger.info("OS:" + os)
      logger.info("当前系统为Windows系统，需要下载winutils.exe，并设置hadoop.home.dir变量")
      System.setProperty("hadoop.home.dir", "C:\\dev\\winutils")
    }
    logger.info("start config:")
    logger.info("[VM options]=>-Dspark.master=local[*]")
    logger.info("[Program arguments]=> <taskSchemaPath>,<taskSchemaPath>")
  }

  def validParams(args: Array[String]): Unit = {
    logger.info("args:")
    for (index <- args.indices) {
      logger.info(s"args[$index]=" + args(index))
    }

    //检查参数
    if (1 != args.length) {
      logger.error("please input program arguments!")
      sys.exit(1)
    }
  }

  def dropTempView(sparkSession: SparkSession, taskSchema: TaskSchema): Unit = {
    import scala.collection.JavaConversions._
    for (stepSchema <- taskSchema.getSteps) {
      sparkSession.catalog.dropTempView(stepSchema.getTempView)
    }
  }

}



