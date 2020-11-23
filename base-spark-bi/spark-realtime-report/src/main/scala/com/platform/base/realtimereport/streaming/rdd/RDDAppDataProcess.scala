package com.platform.realtimereport.streaming.rdd

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.config.ApplicationConfig
import com.platform.realtimereport.persistence.service.AppDataProcessService
import org.apache.spark.rdd.RDD


case class RDDAppDataProcess (){

  def aggregateDeviceLocationData(deviceLocationRDD: RDD[JSONObject]): Unit = {
    val deviceLocations = deviceLocationRDD.map(ele =>{
      val usageType = ele.getString(ApplicationConfig.APP_DEVICE_LOCATION_STAGE)
      val longitude = ele.getString(ApplicationConfig.APP_DEVICE_LOCATION_LONGITUDE)
      val latitude = ele.getString(ApplicationConfig.APP_DEVICE_LOCATION_LATITUDE)
      val location = new JSONObject()
      location.put("usageType", usageType)
      location.put("longitude", longitude)
      location.put("latitude", latitude)
      location.toString()
    }).collect()
    AppDataProcessService().persistDeviceLocationData(deviceLocations)
    for (elem <- deviceLocations) {
      println(elem)
    }
  }
}