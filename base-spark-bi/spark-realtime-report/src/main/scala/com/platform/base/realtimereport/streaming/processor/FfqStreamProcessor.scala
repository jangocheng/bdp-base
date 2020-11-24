package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.config.ApplicationConfig
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDLoanDataProcess
import org.apache.spark.streaming.dstream.DStream

case class LoanStreamProcessor() extends StreamProcessStrategy {

  override def processDataStream(cachedStream: DStream[JSONObject]): Unit = {
    val loanDataStream = cachedStream.filter(data =>
        filterValidOrderData(data)
    ).cache()

    loanDataStream.foreachRDD(rdd => {
      logger.info("Cycling process orderRDD data...")
      if (!rdd.isEmpty()) {
        logger.info("Enter process orderRDD data...")
        RDDLoanDataProcess().aggregateOrderData(rdd)
        RDDLoanDataProcess().aggregateOrderCityData(rdd)
      }
    })
  }

  def filterValidOrderData(data: JSONObject): Boolean = {
    val isOrderData = data.getString(ApplicationCommon.NAMESPACE_KEY) match {
      case ApplicationConfig.LOANORDER => true
      case _ => false
    }
    isOrderData
  }
}
