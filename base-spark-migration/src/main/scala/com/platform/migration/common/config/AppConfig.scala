package com.platform.migration.common.config

import com.platform.migration.common.CommonConstant
import com.platform.migration.common.util.PropertiesUtils

object AppConfig {
  @transient private val prop = PropertiesUtils.read("application.properties")
  //=======================SPARK APPLICATION===================
  //SPARK应用名称
  val SPARK_APP_NAME: String = prop.getProperty(CommonConstant.SPARK_APP_NAME)
  //配置日志打印级别
  val SPARK_LOG_LEVEL: String = prop.getProperty(CommonConstant.SPARK_LOG_LEVEL)
  //SPARK应用目录
  val APP_PARENT_PATH: String = prop.getProperty(CommonConstant.APP_PARENT_PATH)
  //配置SPARK CHECKPOINT目录
  val SPARK_CHECKPOINT: String = prop.getProperty(CommonConstant.SPARK_CHECKPOINT)

  //=================COMPRESSION AND SERIALIZATION==============
  //SPARK序列化方式，
  val SPARK_SERIALIZER: String = prop.getProperty(CommonConstant.SPARK_SERIALIZER)

  //================SPARK MEMORY MANAGEMENT=====================
  // EXECUTION AND STORAGE内存
  val SPARK_MEMORY_FRACTION: String = prop.getProperty(CommonConstant.SPARK_MEMORY_FRACTION)
  //STORAGE MEMORY/（EXECUTION MEMORY+STORAGE MEMORY)内存比例
  val SPARK_MEMORY_STORAGEFRACTION: String = prop.getProperty(CommonConstant.SPARK_MEMORY_STORAGEFRACTION)

  //=======================SPARK-SHUFFLE配置=====================
  //SPARKSTREAMING_SHUFFLE_MANAGER的类型(HASH,SORT)
  val SPARK_SHUFFLE_MANAGER: String = prop.getProperty(CommonConstant.SPARK_SHUFFLE_MANAGER)
  //SPARK_SHUFFLE缓存大小,默认32K 内存充足可以调到64,减少SHUFFLE WRITE过程中溢写磁盘文件的次数，也就可以减少磁盘IO次数
  val SPARK_SHUFFLE_FILE_BUFFER: String = prop.getProperty(CommonConstant.SPARK_SHUFFLE_FILE_BUFFER)
  //SPARK SHUFFLE READ 拉取属于自己数据时重试的次数
  val SPARK_SHUFFLE_IO_MAXRETRIES: String = prop.getProperty(CommonConstant.SPARK_SHUFFLE_IO_MAXRETRIES)
  //SPARK SHUFFLE READ 拉取属于自己数据时重试的间隔
  val SPARK_SHUFFLE_IO_RETRYWAIT: String = prop.getProperty(CommonConstant.SPARK_SHUFFLE_IO_RETRYWAIT)


  //=======================SPARK-SQL相关配置=====================
  // SPARK SQL 解析正则表达式,默认值FALSE
  val SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES: String = prop.getProperty(CommonConstant.SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES)
  //区分大小写,默认值TRUE
  val SPARK_SQL_CASESENSITIVE: String = prop.getProperty(CommonConstant.SPARK_SQL_CASESENSITIVE)
  //启动使用hive的反序列化
  val SPARK_SQL_HIVE_CONVERTMETASTOREPARQUET: String = prop.getProperty(CommonConstant.SPARK_SQL_HIVE_CONVERTMETASTOREPARQUET)

  //=======================HIVE配置==============================
  //开启动态分区，默认FALSE
  val HIVE_EXEC_DYNAMIC_PARTITION: String = prop.getProperty(CommonConstant.HIVE_EXEC_DYNAMIC_PARTITION)
  //动态分区的模式，默认STRICT，表示必须指定至少一个分区为静态分区，NONSTRICT模式表示允许所有的分区字段都可以使用动态分区。
  val HIVE_EXEC_DYNAMIC_PARTITION_MODE: String = prop.getProperty(CommonConstant.HIVE_EXEC_DYNAMIC_PARTITION_MODE)
  //设置MAP/REDUCE 输出压缩，
  val HIVE_EXEC_COMPRESS_OUTPUT: String = prop.getProperty(CommonConstant.HIVE_EXEC_COMPRESS_OUTPUT)
  //输出的压缩格式
  val MAPRED_OUTPUT_COMPRESSION_CODEC: String = prop.getProperty(CommonConstant.MAPRED_OUTPUT_COMPRESSION_CODEC)
  //对于SEQUENCEFILE输出，应使用哪种压缩类型（NONE，RECORD或BLOCK）。 建议使用BLOCK。
  val MAPRED_OUTPUT_COMPRESSION_TYPE: String = prop.getProperty(CommonConstant.MAPRED_OUTPUT_COMPRESSION_TYPE)
  //默认输入格式：COMBINEHIVEINPUTFORMAT。 如果遇到COMBINEHIVEINPUTFORMAT问题，请将其设置为HIVEINPUTFORMAT。
  val HIVE_INPUT_FORMAT: String = prop.getProperty(CommonConstant.HIVE_INPUT_FORMAT)
  //默认值：FALSE，MERGE SMALL FILES AT THE END OF A MAP-ONLY JOB_
  val HIVE_MERGE_MAPFILES: String = prop.getProperty(CommonConstant.HIVE_MERGE_MAPFILES)
  //默认值：FALSE，MERGE SMALL FILES AT THE END OF A MAP-REDUCE JOB_
  val HIVE_MERGE_MAPREDFILES: String = prop.getProperty(CommonConstant.HIVE_MERGE_MAPREDFILES)
  //默认值：256000000，SIZE OF MERGED FILES AT THE END OF THE JOB_
  val HIVE_MERGE_SIZE_PER_TASK: String = prop.getProperty(CommonConstant.HIVE_MERGE_SIZE_PER_TASK)
  //默认值：16000000
  val HIVE_MERGE_SMALLFILES_AVGSIZE: String = prop.getProperty(CommonConstant.HIVE_MERGE_SMALLFILES_AVGSIZE)
  //决定每个MAP处理的最大的文件大小，单位为B
  val MAPRED_MAX_SPLIT_SIZE: String = prop.getProperty(CommonConstant.MAPRED_MAX_SPLIT_SIZE)

  //=======================HBASE配置=============================
  val HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT: String = prop.getProperty(CommonConstant.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT)
  val HBASE_ZOOKEEPER_QUORUM: String = prop.getProperty(CommonConstant.HBASE_ZOOKEEPER_QUORUM)
  val HBASE_RPC_TIMEOUT: String = prop.getProperty(CommonConstant.HBASE_RPC_TIMEOUT)
  val HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD: String = prop.getProperty(CommonConstant.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD)
}
