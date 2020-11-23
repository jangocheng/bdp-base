package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.config.ApplicationConfig
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDFfqDataProcess
import org.apache.spark.streaming.dstream.DStream

case class FfqStreamProcessor() extends StreamProcessStrategy {

  override def processDataStream(cachedStream: DStream[JSONObject]): Unit = {
    val ffqDataStream = cachedStream.filter(data =>
        filterValidOrderData(data)
    ).cache()

    ffqDataStream.foreachRDD(rdd => {
      logger.info("Cycling process orderRDD data...")
      if (!rdd.isEmpty()) {
        logger.info("Enter process orderRDD data...")
        RDDFfqDataProcess().aggregateOrderData(rdd)
        RDDFfqDataProcess().aggregateOrderCityData(rdd)
      }
    })
  }

  def filterValidOrderData(data: JSONObject): Boolean = {
    val isOrderData = data.getString(ApplicationCommon.NAMESPACE_KEY) match {
      case ApplicationConfig.FFQ_ORDER => true
      case _ => false
    }
    isOrderData
  }
}
