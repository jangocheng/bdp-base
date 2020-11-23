package com.platform.hbase2max

import com.platform.hbase2max.common.utils.{DateTimeUtils, SparkConfUtils}
import com.platform.hbase2max.store.DataStore
import org.apache.spark.SparkContext
import org.slf4j.LoggerFactory

object Hbase2Max {
  @transient lazy val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    //1.确认启动配置和参数
    checkStartConfAndParams(args)
    val hbaseTableNames = args(0).split(",")
    val tables = args(1).split(",")
    val hasIncrementDeal: Boolean = args(2).toBoolean


    //2.初始化sparkContext
    val sparkConf = SparkConfUtils.initSparkConf()
    val sparkContext = new SparkContext(sparkConf)

    try {
      //3.判断是否是增量
      if (hasIncrementDeal) {
        for (index <- tables.indices) {
          val timer = DateTimeUtils.getDayRange
          val start = timer.getOrElse("start", "0")
          val end = timer.getOrElse("end", "0")
          val table = tables(index)
          val hbaseTable = hbaseTableNames(index)
          val pt: String = DateTimeUtils.getYesterdayBizDate
          logger.info(s"====================hbaseTable:$hbaseTable\tmaxcomputeTable:$table\tpt:$pt\tstart:$start\tend:$end")
          DataStore().handle(sparkContext, table, hbaseTable, pt, start, end)
        }

      } else {
        val lowers = args(3).split(",")
        DataStore().handleAll(sparkContext, tables, hbaseTableNames, lowers)
      }

      sparkContext.stop()
    } catch {
      case ex: Exception =>
        logger.error(s"同步数据失败,同步的表：$tables，error:$ex")
    }


  }

  def checkStartConfAndParams(args: Array[String]): Unit = {
    val os = System.getProperty("os.name")
    if (os.contains("Windows")) {
      println("OS:" + os)
      println("当前系统为Windows系统，需要下载winutils.exe，并设置hadoop.home.dir变量")
      System.setProperty("hadoop.home.dir", "C:\\dev\\winutils")
    }
    println("usage:")
    println("[Main class]=>com.platform.HiveEtl")
    println("[VM options]=>-Dspark.master=local[*]")
    println("[Program arguments]=><day> <taskName...>")
    println("args:")
    for (index <- args.indices) {
      println(s"args[$index]=" + args(index))
    }

    //检查参数
    if (args.length < 4) {
      sys.error("please input program arguments!")
      sys.exit(1)
    }

  }

}
