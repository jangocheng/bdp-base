package com.platform.migration.common.singleton

import com.platform.migration.common.CommonConstant
import com.platform.migration.common.config.AppConfig
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration

object HbaseConfSingleton {

  @transient private var instance: Configuration = _

  def getInstance(): Configuration = {
    if (instance == null) {
      instance = HBaseConfiguration.create
      instance.set(CommonConstant.HBASE_ZOOKEEPER_QUORUM, AppConfig.HBASE_ZOOKEEPER_QUORUM)
      instance.set(CommonConstant.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT, AppConfig.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT)
      instance.set(CommonConstant.HBASE_RPC_TIMEOUT, AppConfig.HBASE_RPC_TIMEOUT)
      instance.set(CommonConstant.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, AppConfig.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD)
    }
    instance
  }

}
