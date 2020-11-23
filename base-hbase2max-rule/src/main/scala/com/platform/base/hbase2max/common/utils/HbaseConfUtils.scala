package com.platform.hbase2max.common.utils

import com.platform.hbase2max.common.SparkCommon
import com.platform.hbase2max.common.conf.Hbase2MaxConf
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.conf.Configuration

object HbaseConfUtils {
  def initHbaseConfiguration(): Configuration = {
    val conf = HBaseConfiguration.create
    conf.set(SparkCommon.HBASE_ZOOKEEPER_QUORUM, Hbase2MaxConf.HBASE_ZOOKEEPER_QUORUM)
    conf.set(SparkCommon.HBASE_ZOOKEEPER_PORT, Hbase2MaxConf.HBASE_ZOOKEEPER_PORT)
    conf
  }

}
