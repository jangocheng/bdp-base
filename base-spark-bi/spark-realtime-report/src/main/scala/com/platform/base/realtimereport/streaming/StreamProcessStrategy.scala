package com.platform.realtimereport.streaming

import com.alibaba.fastjson.JSONObject
import org.apache.spark.streaming.dstream.DStream
import org.slf4j.{Logger, LoggerFactory}

/**
  *
  * author: wlhbdp
  */
trait StreamProcessStrategy {

  val logger:Logger = LoggerFactory.getLogger(getClass)

  def processDataStream(stream: DStream[JSONObject])
}
