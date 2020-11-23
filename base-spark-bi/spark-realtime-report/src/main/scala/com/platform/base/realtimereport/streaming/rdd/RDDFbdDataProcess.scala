package com.platform.realtimereport.streaming.rdd

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory
import com.platform.streaming.config._
import com.platform.realtimereport.config.ApplicationConfig
import com.platform.realtimereport.persistence.service._

case class RDDZfbDataProcess() {
  private val logger = LoggerFactory.getLogger(getClass)

  def aggregateRegisterAndIdentifiedData(userRDD: RDD[JSONObject]):Unit = {
    val userRDDFiltered = userRDD.filter(ele => {
      val opt = ele.getString(ApplicationCommon.OPERATION_KEY)
      val dataType = ele.getString(ApplicationCommon.NAMESPACE_KEY)
      dataType match {
        case ApplicationConfig.ZFB_USER =>
          ApplicationConfig.OPERATION_INSERT.equals(opt)
        case ApplicationConfig.ZFB_APPLY =>
          isValidApplyData(ele, opt)
        case _ => false
      }

    })

    val amountArray = userRDDFiltered.map(ele => {
      val dataType = ele.getString(ApplicationCommon.NAMESPACE_KEY)

      //由于前面已过滤，此处只用判断记录是user还是apply，若为user的则为注册用户，否则为实名用户
      val key = if (ApplicationConfig.ZFB_USER.equals(dataType)) {
        ApplicationCommon.REGISTER_AMOUNT_KEY
      } else {
        ApplicationCommon.IDENTIFIED_AMOUNT_KEY
      }
      (key, 1L)
    }).reduceByKey(_ + _)

    val amounts = amountArray.collect()
    if (amounts.length > 0) {
      ZfbDataProcessService().persistZfbUserData(amounts)

      val info = new StringBuilder("==================================")
      info.append("amountArray:")
      amountArray.collect.foreach(x => info.append(x))
      info.append("==================================")
      if (AppConfig.RUN_MODE_STANDALONE) {
        println(info.toString())
      }
      logger.info(info.toString())
    }
  }

  /**
    * 判断JSON数据是否是有效的apply数据
    * @param ele JSONObject
    * @return boolean
    */
  def isValidApplyData(ele: JSONObject, opt:String):Boolean = {
    val objectJson = Option.apply(ele.getJSONObject(ApplicationCommon.OBJECT_WRAPPER_KEY))
    if (objectJson.nonEmpty) {
      val step = objectJson.get.getIntValue(ApplicationCommon.ZFB_APPLY_STEP_KEY)
      val isIdentified = step == ApplicationCommon.ZFB_APPLY_STEP_IDENTIFIED
      if (isIdentified && ApplicationConfig.OPERATION_INSERT.equals(opt)) {
        true
      }
//      else if (isIdentified && ApplicationConfig.OPERATION_UPDATE.equals(opt)) {
//        val query = objectJson.get.getJSONObject(ApplicationCommon.OBJECT_QUERY_KEY)
//        query == null
//      }
      else {
        false
      }
    } else {
      false
    }
  }
}
