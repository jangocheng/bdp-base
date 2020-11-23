package com.platform.realtimereport.persistence.service

import java.util.{Calendar, Date}

import com.platform.realtimereport.persistence.dal.RedisOperationX
import org.apache.commons.lang3.time.DateUtils

case class ZfbDataProcessService() {

  /**
    * 将数据保存到Redis中
    * @param amountArray Array[(String, Long)]类型的数组
    */
  def persistZfbUserData(amountArray: Array[(String, Long)]): Unit = {
    val nowHour:Long = DateUtils.getFragmentInHours(new Date(), Calendar.DAY_OF_YEAR)
    amountArray.foreach(param => {
      RedisOperationX.hmIncrementByOrCreate(param._1, nowHour, param._2)
    })
  }
}
