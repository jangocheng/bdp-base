package com.platform.realtimereport.config

import com.platform.realtimereport.common.constant.ApplicationCommon
import com.typesafe.config.{Config, ConfigFactory}

/**
  *
  * author: wlhbdp
  */
object ApplicationConfig {
  val conf: Config = ConfigFactory.load

  val ZFB_USER:String = conf.getString(ApplicationCommon.MONGO_SHAKE_ZFB_USER)

  val ZFB_APPLY:String = conf.getString(ApplicationCommon.MONGO_SHAKE_ZFB_APPLY)

  val APP_DEVICE_LOCATION_DATATYPE:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_DATATYPE)

  val LOANORDER:String = conf.getString(ApplicationCommon.MONGO_SHAKE_LOANORDER)

  val OPERATION_INSERT:String = conf.getString(ApplicationCommon.MONGO_SHAKE_OPERATION_INSERT)

  val OPERATION_UPDATE:String = conf.getString(ApplicationCommon.MONGO_SHAKE_OPERATION_UPDATE)

  val MYSQL_OPERATION_INSERT:String = conf.getString(ApplicationCommon.MYSQL_OPERATION_INSERT)

  val MYSQL_OPERATION_UPDATE:String = conf.getString(ApplicationCommon.MYSQL_OPERATION_UPDATE)

  val MYSQL_HXDB_IND_INFO:String = conf.getString(ApplicationCommon.MYSQL_HXDB_IND_INFO)

  val APP_DEVICE_LOCATION_STAGE:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_STAGE)

  val APP_DEVICE_LOCATION_STAGE_PAYMENT:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_STAGE_PAYMENT)

  val APP_DEVICE_LOCATION_STAGE_WITHDRAW:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_STAGE_WITHDRAW)

  val APP_DEVICE_LOCATION_LONGITUDE:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_LONGITUDE)

  val APP_DEVICE_LOCATION_LATITUDE:String = conf.getString(ApplicationCommon.APP_DEVICE_LOCATION_LATITUDE)
}
