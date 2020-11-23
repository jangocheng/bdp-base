package com.platform.base.conf

import com.platform.base.common.SparkCommon
import org.apache.spark.SparkConf

object SparkETLConf {

  def initSparkConf():SparkConf = {
    val sparkConf = new SparkConf().setAppName(AppConfig.APP_NAME)
    sparkConf.set(SparkCommon.SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES,AppConfig.SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES)
    sparkConf.set(SparkCommon.SPARK_DEBUG_MAXTOSTRINGFIELDS,AppConfig.SPARK_DEBUG_MAXTOSTRINGFIELDS)
    sparkConf.set(SparkCommon.SPARK_CONFIG_SERIALIZER_TYPE, AppConfig.SERIALIZER_TYPE)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_STORAGE_FRACTION,AppConfig.MEMORY_FOR_STORAGE_FRACTION)
    sparkConf.set(SparkCommon.SPARK_CONFIG_EXECUTOR_MEMORY_FRACTION,AppConfig.MEMORY_FOR_SHUFFLE_FRACTION.toString)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_USE_LEGACY_MODE,AppConfig.MEMORY_LEGACY_MODE)
    sparkConf.set(SparkCommon.SPARK_CONFIG_MEMORY_FRACTION,AppConfig.MEMORY_FRACTION)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_TYPE, AppConfig.SHUFFLE_TYPE)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_SIZE,AppConfig.SHUFFLE_BUFF_SIZE)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_MAXTRY,AppConfig.SHUFFLE_FETCH_DATA_TIMES)
    sparkConf.set(SparkCommon.SPARK_SHUFFLE_BUFFILE_FETCH_INTERVAL,AppConfig.SHUFFLE_FECTH_DATA_INTERVAL)
    return sparkConf
  }


}
