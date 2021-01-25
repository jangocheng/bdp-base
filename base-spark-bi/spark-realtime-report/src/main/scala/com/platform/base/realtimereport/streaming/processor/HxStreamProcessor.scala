package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDCoreDataProcess
import org.apache.spark.streaming.dstream.DStream

/**
  * 报表处理
  */
case class CoreStreamProcessor()  extends StreamProcessStrategy {
  override def processDataStream(stream: DStream[JSONObject]): Unit = {
    RDDCoreDataProcess().aggregateHourlyConsumptionMoneyData(stream)
  }
}
