package com.platform.realtimereport

import java.io.FileNotFoundException

import com.platform.realtimereport.consumer.StreamingDataHandler
import com.platform.streaming.config.{AppConfig, ConnectionFactory, StreamingConf}
import com.platform.streaming.service.KafkaManager
import com.platform.streaming.stream.StreamingManager
import org.apache.spark.SparkException
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.slf4j.LoggerFactory
/**
  * 实时报表streaming
  * author: wlhbdp
  */
object RealTimeReportStreaming {
  private val log = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    //读取Spark Streaming配置
    try {
      StreamingConf.checkConfig()
    } catch {
      case fe: FileNotFoundException => log.error("Config file not found!", fe)
      case ex: Exception => {
        log.error("Get config properties failed!", ex)
      }
    }

    try {
      val streamingCtx = createStreaming()
      //初始化MySQL数据源
      ConnectionFactory.getDataSource
      streamingCtx.start()
      log.info("Real time report streaming starts success...")
      streamingCtx.awaitTermination()
    } catch {
      case se: SparkException => log.error("Spark streaming starts failure!", se)
      case e: Exception => log.error("Exception occured!", e)
    }
  }

  def createStreaming(): StreamingContext = {
    val sc = new StreamingContext(StreamingConf.initStreamingConf(),
        Seconds(AppConfig.SCHEDULING_INTERVAL))
    sc.checkpoint(AppConfig.CHECK_POINT)
    sc.sparkContext.setLogLevel(AppConfig.LOG_LEVEL)
    handleStreamingData(sc)

    sc
  }

  def handleStreamingData(sc: StreamingContext): Unit = {
    val fromOffsets = KafkaManager().initOffset()
    val generalStreaming = StreamingManager().createAndAggregateStream(sc, fromOffsets,
        StreamingConf.getKafkaParams)
    if (fromOffsets == null || generalStreaming == null) {
      throw new RuntimeException("kafka offsets init failed or creating userStreaming failed！")
    }
    val streamingDataHandler = StreamingDataHandler()
    streamingDataHandler.handleData(generalStreaming)
  }
}
