package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDAppDataProcess
import org.apache.spark.streaming.dstream.DStream
import com.platform.streaming.config._
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.config.ApplicationConfig
case class AppStreamProcessor() extends StreamProcessStrategy {

  override def processDataStream(cacheStream: DStream[JSONObject]): Unit = {
    //过滤支付宝App数据
    val appDataStream = cacheStream.filter(data =>
      filterUsageData(data)
    ).cache()

    //对支付宝App数据进行处理
    logger.info("Begin process appRDD data...")
    appDataStream.foreachRDD(rdd => {
      logger.info("Cycling process appRDD data...")
      if (!rdd.isEmpty()) {
        logger.info("Enter process appRDD data...")
        RDDAppDataProcess().aggregateDeviceLocationData(rdd)
      }
    })
  }

  /**
    * 过滤App数据中提现与支付地理位置信息的json数据
    * @param data jsonObject
    */

  def filterUsageData(data: JSONObject): Boolean = {
    val isUsageData = data.getString(ApplicationCommon.NAMESPACE_KEY) match {
      case ApplicationConfig.APP_DEVICE_LOCATION_DATATYPE => true
      case _=> false
    }

    val isPayment = data.getString(ApplicationConfig.APP_DEVICE_LOCATION_STAGE) match {
      case ApplicationConfig.APP_DEVICE_LOCATION_STAGE_PAYMENT => true
      case _=> false
    }

    val isWithDraw = data.getString(ApplicationConfig.APP_DEVICE_LOCATION_STAGE) match {
      case ApplicationConfig.APP_DEVICE_LOCATION_STAGE_WITHDRAW => true
      case _=> false
    }
    if(isUsageData && (isPayment || isWithDraw)){
      val info = new StringBuilder("==================================")
      info.append("Device_location_AppStreamProcessor")
      info.append("==================================")
      if (AppConfig.RUN_MODE_STANDALONE) {
        println(info.toString())
      }
      logger.info(info.toString())
    }
    isUsageData && (isPayment || isWithDraw)
  }
}
