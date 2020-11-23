package com.platform.hbase2max.common.utils

import com.platform.hbase2max.common.SparkCommon
import com.platform.hbase2max.common.conf.Hbase2MaxConf
import org.apache.spark.SparkConf

object SparkConfUtils {
  def initSparkConf(): SparkConf = {
    val sparkConf = new SparkConf().setAppName(Hbase2MaxConf.APP_NAME)
    sparkConf.set(SparkCommon.SPARK_CONFIG_SERIALIZER_TYPE, Hbase2MaxConf.SERIALIZER_TYPE)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_STORAGE_FRACTION, Hbase2MaxConf.MEMORY_FOR_STORAGE_FRACTION)
    sparkConf.set(SparkCommon.SPARK_CONFIG_EXECUTOR_MEMORY_FRACTION, Hbase2MaxConf.MEMORY_FOR_SHUFFLE_FRACTION.toString)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_USE_LEGACY_MODE, Hbase2MaxConf.MEMORY_LEGACY_MODE)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_FRACTION, Hbase2MaxConf.MEMORY_FRACTION)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_TYPE, Hbase2MaxConf.SHUFFLE_TYPE)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_SIZE, Hbase2MaxConf.SHUFFLE_BUFF_SIZE)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_MAXTRY, Hbase2MaxConf.SHUFFLE_FETCH_DATA_TIMES)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_FETCH_INTERVAL, Hbase2MaxConf.SHUFFLE_FECTH_DATA_INTERVAL)
    sparkConf
  }

}
