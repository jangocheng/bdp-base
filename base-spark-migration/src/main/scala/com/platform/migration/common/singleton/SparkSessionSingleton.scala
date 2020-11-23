package com.platform.migration.common.singleton

import com.platform.migration.common.CommonConstant
import com.platform.migration.common.config.AppConfig
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSessionSingleton {
  @transient private var instance: SparkSession = _

  def getInstance(conf: SparkConf): SparkSession = {
    if (null != instance)
      return instance

    instance = SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()

    instance.sparkContext.setCheckpointDir(AppConfig.SPARK_CHECKPOINT)
    instance.sparkContext.setLogLevel(AppConfig.SPARK_LOG_LEVEL)

    instance.sqlContext.setConf(CommonConstant.SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES, AppConfig.SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES)
    instance.sqlContext.setConf(CommonConstant.SPARK_SQL_CASESENSITIVE, AppConfig.SPARK_SQL_CASESENSITIVE)
    instance.sqlContext.setConf(CommonConstant.SPARK_SQL_HIVE_CONVERTMETASTOREPARQUET, AppConfig.SPARK_SQL_HIVE_CONVERTMETASTOREPARQUET)

    instance.sqlContext.setConf(CommonConstant.HIVE_EXEC_DYNAMIC_PARTITION, AppConfig.HIVE_EXEC_DYNAMIC_PARTITION)
    instance.sqlContext.setConf(CommonConstant.HIVE_EXEC_DYNAMIC_PARTITION_MODE, AppConfig.HIVE_EXEC_DYNAMIC_PARTITION_MODE)
    instance.sqlContext.setConf(CommonConstant.HIVE_EXEC_COMPRESS_OUTPUT, AppConfig.HIVE_EXEC_COMPRESS_OUTPUT)
    instance.sqlContext.setConf(CommonConstant.MAPRED_OUTPUT_COMPRESSION_CODEC, AppConfig.MAPRED_OUTPUT_COMPRESSION_CODEC)
    instance.sqlContext.setConf(CommonConstant.MAPRED_OUTPUT_COMPRESSION_TYPE, AppConfig.MAPRED_OUTPUT_COMPRESSION_TYPE)
    instance.sqlContext.setConf(CommonConstant.HIVE_INPUT_FORMAT, AppConfig.HIVE_INPUT_FORMAT)
    instance.sqlContext.setConf(CommonConstant.HIVE_MERGE_MAPFILES, AppConfig.HIVE_MERGE_MAPFILES)
    instance.sqlContext.setConf(CommonConstant.HIVE_MERGE_MAPREDFILES, AppConfig.HIVE_MERGE_MAPREDFILES)
    instance.sqlContext.setConf(CommonConstant.HIVE_MERGE_SIZE_PER_TASK, AppConfig.HIVE_MERGE_SIZE_PER_TASK)
    instance.sqlContext.setConf(CommonConstant.HIVE_MERGE_SMALLFILES_AVGSIZE, AppConfig.HIVE_MERGE_SMALLFILES_AVGSIZE)
    instance.sqlContext.setConf(CommonConstant.MAPRED_MAX_SPLIT_SIZE, AppConfig.MAPRED_MAX_SPLIT_SIZE)


    instance
  }


}
