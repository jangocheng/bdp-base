package com.platform.realtimereport.persistence.service

import com.platform.realtimereport.persistence.dal.RedisOperationX

case class AppDataProcessService (){

  def persistDeviceLocationData(deviceLocationArray: Array[String]): Unit = {
      deviceLocationArray.foreach(param =>
        RedisOperationX.listLpushCreate("zfb_usage_data", param)
      )
  }
}
