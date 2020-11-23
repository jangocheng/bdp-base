package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDHxDataProcess
import org.apache.spark.streaming.dstream.DStream

/**
  * 报表处理
  */
case class HxStreamProcessor()  extends StreamProcessStrategy {
  override def processDataStream(stream: DStream[JSONObject]): Unit = {
    RDDHxDataProcess().aggregateHourlyConsumptionMoneyData(stream)
  }
}
