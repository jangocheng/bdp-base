package com.platform.realtimereport.streaming.rdd

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.persistence.service.FfqDataProcessService
import com.platform.streaming.config.AppConfig
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

import scala.util.matching.Regex


case class RDDFfqDataProcess() {
  private val logger = LoggerFactory.getLogger(getClass)
  def aggregateOrderData(userRDD: RDD[JSONObject]):Unit = {
    val resultArray = userRDD.flatMap(ele => {
      var paidAmount: Float = 0
      var paidNum: Float = 0
      if("UPDATE".equals(ele.getString("dmlType"))){
        val obj = ele.getJSONObject("object")
        if (obj != null && "0".equals(obj.getString("paymentStatus"))
          && obj.getFloat("actualPayment") != null) {
          paidAmount = obj.getFloat("actualPayment")
          paidNum = 1
        }
      }
      List((ApplicationCommon.FFQ_PAID_AMOUNT, paidAmount), (ApplicationCommon.FFQ_PAID_NUM, paidNum))
    }).reduceByKey(_ + _)
    FfqDataProcessService().persistFfqOrderData(resultArray.collect())
    val info = new StringBuilder("==================================")
    info.append("ORDERArray:")
    resultArray.collect.foreach(x => info.append(x))
    info.append("==================================")
    if (AppConfig.RUN_MODE_STANDALONE) {
      println(info.toString())
    }
    logger.info(info.toString())
  }

  def aggregateOrderCityData(userRDD: RDD[JSONObject]):Unit = {
    val resultArray = userRDD.map(ele => {
      val opt = ele.getString(ApplicationCommon.OPERATION_KEY)
      val optObj = ele.getJSONObject(ApplicationCommon.OBJECT_WRAPPER_KEY)
      val receiverAddress = optObj.getString(ApplicationCommon.FFQ_RECEIVER_ADDRESS)
      val paymentStatus = optObj.getString(ApplicationCommon.FFQ_PAYMENT_STATUS_KEY)
      if (opt.equals("UPDATE") && optObj != null && paymentStatus != null && paymentStatus == "1" && receiverAddress != null ){
        val cityPattern: Regex = "(?<=\\s)(.*?)(?=\\s)".r
        val city: String = cityPattern.findFirstIn(receiverAddress) match {
          case Some(s) => s
          case None => "unknown_city"
        }
        (city, 1)
      } else {
        ("unknown_city", 0)
      }
    }).reduceByKey(_+_)
    FfqDataProcessService().persistFfqOrderCityData(resultArray.collect())
    val info = new StringBuilder("==================================")
    info.append("CITYArray:")
    resultArray.collect.foreach(x => info.append(x))
    info.append("==================================")
    if (AppConfig.RUN_MODE_STANDALONE) {
      println(info.toString())
    }
    logger.info(info.toString())
  }
}
