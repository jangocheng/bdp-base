package com.platform.realtimereport.streaming

import com.alibaba.fastjson.JSONObject
import org.apache.spark.streaming.dstream.DStream

/**
  *
  * author: wlhbdp
  */
case class ProcessorContext(processor: StreamProcessStrategy) {

  def executeStreamProcess(stream: DStream[JSONObject]):Unit = {
    processor.processDataStream(stream)
  }
}
