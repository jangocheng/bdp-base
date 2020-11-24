package com.platform.realtimereport.persistence.service

import java.util.{Calendar, Date}

import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.persistence.dal.RedisOperationX
import org.apache.commons.lang3.time.DateUtils


case class LoanDataProcessService () {

  def persistLoanOrderData(resultArray: Array[(String, Float)]): Unit = {
    val nowHour:Long = DateUtils.getFragmentInHours(new Date(), Calendar.DAY_OF_YEAR)
    resultArray.foreach(param => {
      RedisOperationX.hmIncrementByFloatOrCreate(param._1, nowHour, param._2)
    })
  }

  def persistLoanOrderCityData(resultArray: Array[(String, Int)]): Unit = {
    resultArray.foreach(param => {
      RedisOperationX.hmIncrementByIntOrCreate(ApplicationCommon.LOANCITY_RANK, param._1, param._2)
    })
  }
}
