package com.platform.migration.common.singleton

import com.platform.migration.common.CommonConstant
import com.platform.migration.common.config.AppConfig
import org.apache.spark.SparkConf

object SparkConfSingleton {
  @transient private var instance: SparkConf = _

  def getInstance(): SparkConf = {
    if (null != instance) {
      return instance
    }

    instance = new SparkConf()
    instance.setAppName(AppConfig.SPARK_APP_NAME)
    instance.set(CommonConstant.SPARK_SERIALIZER, AppConfig.SPARK_SERIALIZER)
    instance.set(CommonConstant.SPARK_MEMORY_FRACTION, AppConfig.SPARK_MEMORY_FRACTION)
    instance.set(CommonConstant.SPARK_MEMORY_STORAGEFRACTION, AppConfig.SPARK_MEMORY_STORAGEFRACTION)
    instance.set(CommonConstant.SPARK_SHUFFLE_MANAGER, AppConfig.SPARK_SHUFFLE_MANAGER)
    instance.set(CommonConstant.SPARK_SHUFFLE_FILE_BUFFER, AppConfig.SPARK_SHUFFLE_FILE_BUFFER)
    instance.set(CommonConstant.SPARK_SHUFFLE_IO_MAXRETRIES, AppConfig.SPARK_SHUFFLE_IO_MAXRETRIES)
    instance.set(CommonConstant.SPARK_SHUFFLE_IO_RETRYWAIT, AppConfig.SPARK_SHUFFLE_IO_RETRYWAIT)

    instance
  }


}
