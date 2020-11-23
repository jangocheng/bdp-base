package com.platform.service

import com.alibaba.fastjson.JSONObject
import com.platform.db.hbase.MallDataDB
import org.apache.spark.rdd.RDD


case class MallService() {

  /**
    * 保存计算出的工时的结果
    */
  def saveComputedTime(result: RDD[(JSONObject, Long)]): Unit = {
    MallDataDB().saveComputedTimeData(result)
  }

  def saveInvalidData(result: RDD[(JSONObject, Long)]): Unit = {
    MallDataDB().saveInvalidData(result)
  }


}
