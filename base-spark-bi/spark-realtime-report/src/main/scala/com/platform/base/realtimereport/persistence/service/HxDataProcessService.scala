package com.platform.realtimereport.persistence.service

import com.alibaba.fastjson.JSON
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.common.util.DateTimeUtil
import com.platform.realtimereport.persistence.dal.RedisOperationX
import com.platform.streaming.config.RedisConfig
import org.slf4j.LoggerFactory

import scala.util.parsing.json.JSONObject

/**
  * 报表数据持久化
  **/
case class CoreDataProcessService() {
  private val log = LoggerFactory.getLogger(getClass)
  def persistCoreHourlyConsumptionData(payment: Double, withdraw: Double, hour: Long) = {
    val key = ApplicationCommon.REDIS_KEY_HOURLY_CONSUMPTION.concat("_").concat(DateTimeUtil().nowToString(DateTimeUtil().DATEANDTIME_NO_SPLIT))
    var map: Map[String,String] = Map()
    map += (ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_PAYMENT -> payment.toString)
    map += (ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_WITHDRAW -> withdraw.toString)

    //更新数据
    RedisConfig.clients.withClient(client => {
      val exist = client.hexists(key, hour)
      if (exist) {
        val json = JSON.parseObject(client.hget(key, hour).get)
        val oldPayment = json.get(ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_PAYMENT).toString.toDouble
        val oldWithdraw = json.get(ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_WITHDRAW).toString.toDouble
        map += (ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_PAYMENT -> (payment + oldPayment).toString)
        map += (ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_WITHDRAW -> (withdraw + oldWithdraw).toString)
      }
      client.hset(key,hour,JSONObject(map))
      client.expire(key,ApplicationCommon.EXPIRE_24H_IN_SECS)
    })

  }

  def persistCoreNearlydaysConsumptionData(paymentResult: Double, withdrawResult: Double) = {
    val key = ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_PREFIX

    //删除指定的前n天(当天)数据
    val delKey = key.concat(DateTimeUtil().otherDayToString(ApplicationCommon.REDIS_OTHER_DAY_DELETE))
    RedisOperationX.delKey(delKey)

    //更新数据
    val newKey = key.concat(DateTimeUtil().nowToString(DateTimeUtil().DATEANDTIME_NO_SPLIT))
    RedisOperationX.hmIncrementByFloatOrCreate(newKey,ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_PAYMENT,paymentResult.toFloat)
    RedisOperationX.hmIncrementByFloatOrCreate(newKey,ApplicationCommon.REDIS_KEY_CONSUMPTION_CONTRACT_WITHDRAW,withdrawResult.toFloat)
  }

  /**
    * 将数据保存到Redis中
    * @param amountArray Array[(String, Long)]类型的数组
    */
  def persistCoreChannelData(amountArray: Map[String,Long]): Unit = {
    val key = ApplicationCommon.REDIS_KEY_CHANNEL_CONTRACT_PREFIX

    //删除指定的前n天(当天)数据
    val delKey = key.concat(DateTimeUtil().otherDayToString(ApplicationCommon.REDIS_OTHER_DAY_DELETE))
    RedisOperationX.delKey(delKey)

    //更新数据
    val newKey = key.concat(DateTimeUtil().nowToString(DateTimeUtil().DATEANDTIME_NO_SPLIT))

    amountArray.keys.foreach{ field =>
        val value = amountArray(field)
        RedisOperationX.hmIncrementByOrCreate(newKey,field,value)
    }

  }

}
