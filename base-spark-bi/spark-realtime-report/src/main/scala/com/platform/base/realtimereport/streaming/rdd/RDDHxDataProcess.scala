package com.platform.realtimereport.streaming.rdd

import java.util.Calendar

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.persistence.service.CoreDataProcessService
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import org.apache.spark.streaming.dstream.DStream
import org.slf4j.LoggerFactory


/**
  * 报表数据过滤&计算
  **/
case class RDDCoreDataProcess () {

  private val logger = LoggerFactory.getLogger(getClass)

  /** 每日实时消费金额 */
  def aggregateHourlyConsumptionMoneyData(stream: DStream[JSONObject]): Unit = {
    stream.foreachRDD(rdd => {

      if(!rdd.isEmpty()){

        val hour = DateUtils.getFragmentInHours(Calendar.getInstance, Calendar.DAY_OF_YEAR)

        //过滤
        val result = rdd.filter({
          x =>
            x.getString(ApplicationCommon.NAMESPACE_KEY).equals(ApplicationCommon.NAMESPACE_KEY_CHANNEL_CONTRACT) &&
              x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_CONTRACTSTATUS).equals(ApplicationCommon.CONSUMPTION_CONTRACT_CONTRACTSTATUS_VALUE)
//              x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_OCCURDATE).equals(DateTimeUtil().nowToString(DateTimeUtil().DATEANDTIME_SPLIT)) &&
//              x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_CONTRACTSTATUS).equals(ApplicationCommon.CONSUMPTION_CONTRACT_CONTRACTSTATUS_VALUE) &&
//              StringUtils.isNotBlank(x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_DEALDATE)) &&
//              DateTimeUtil().isCurTimeSlot(x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_DEALDATE))
        })

        //计算支付数据
        val paymentResult = result.filter({
          x =>
            x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSTYPE).equals(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSTYPE_PAYMENT_VALUE)
        }).map(
          x => {
            var businessSum:Double = 0
            var discount:Double = 0
            var freeBillAmount:Double = 0
            var returnMoney:Double = 0

            if(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM)!=null && StringUtils.isNotEmpty(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM).toString)){
              businessSum = x.getDoubleValue(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM)
            }
            if(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_DISCOUNT)!=null && StringUtils.isNotEmpty(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_DISCOUNT).toString)){{
              discount = x.getDoubleValue(ApplicationCommon.CONSUMPTION_CONTRACT_DISCOUNT)
            }}
            if(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_FREEBILLAMOUNT)!=null && StringUtils.isNotEmpty(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_FREEBILLAMOUNT).toString)){
              freeBillAmount = x.getDoubleValue(ApplicationCommon.CONSUMPTION_CONTRACT_FREEBILLAMOUNT)
            }
            if(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_RETURNMONEY)!=null && StringUtils.isNotEmpty(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_RETURNMONEY).toString)){
              returnMoney = x.getDoubleValue(ApplicationCommon.CONSUMPTION_CONTRACT_RETURNMONEY)
            }

            businessSum-discount-freeBillAmount-returnMoney
          }
        ).stats().sum

        //计算结算数据
        val withdrawResult = result.filter({
          x =>
            x.getString(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSTYPE).equals(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSTYPE_WITHDRAW_VALUE)
        }).map(
          x => {
            var businessSum:Double = 0
            if(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM)!=null && StringUtils.isNotBlank(x.get(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM).toString)){
              businessSum = x.getDoubleValue(ApplicationCommon.CONSUMPTION_CONTRACT_BUSINESSSUM)
            }
            businessSum
          }
        ).stats().sum

        //计算&持久化
        CoreDataProcessService().persistCoreHourlyConsumptionData(paymentResult,withdrawResult,hour)

      }
    })
  }
}
