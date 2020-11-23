package com.platform.migration.common

object CommonConstant {

  //=======================SPARK APPLICATION===================
  //SPARK应用名称
  val SPARK_APP_NAME = "spark.app.name"
  //配置日志打印级别
  val SPARK_LOG_LEVEL = "spark.log.level"
  //SPARK应用目录
  val APP_PARENT_PATH = "app.parent.path"
  //配置SPARK CHECKPOINT目录
  val SPARK_CHECKPOINT = "spark.checkpoint"

  //=================COMPRESSION AND SERIALIZATION==============
  //SPARK序列化方式，
  val SPARK_SERIALIZER = "spark.serializer"

  //================SPARK MEMORY MANAGEMENT=====================
  // EXECUTION AND STORAGE内存
  val SPARK_MEMORY_FRACTION = "spark.memory.fraction"
  //STORAGE MEMORY/（EXECUTION MEMORY+STORAGE MEMORY)内存比例
  val SPARK_MEMORY_STORAGEFRACTION = "spark.memory.storageFraction"

  //=======================SPARK-SHUFFLE配置=====================
  //SPARKSTREAMING_SHUFFLE_MANAGER的类型(HASH,SORT)
  val SPARK_SHUFFLE_MANAGER = "spark.shuffle.manager"
  //SPARK_SHUFFLE缓存大小,默认32K 内存充足可以调到64,减少SHUFFLE WRITE过程中溢写磁盘文件的次数，也就可以减少磁盘IO次数
  val SPARK_SHUFFLE_FILE_BUFFER = "spark.shuffle.file.buffer"
  //SPARK SHUFFLE READ 拉取属于自己数据时重试的次数
  val SPARK_SHUFFLE_IO_MAXRETRIES = "spark.shuffle.io.maxRetries"
  //SPARK SHUFFLE READ 拉取属于自己数据时重试的间隔
  val SPARK_SHUFFLE_IO_RETRYWAIT = "spark.shuffle.io.retryWait"


  //=======================SPARK-SQL相关配置=====================
  // SPARK SQL 解析正则表达式,默认值FALSE
  val SPARK_SQL_PARSER_QUOTEDREGEXCOLUMNNAMES = "spark.sql.parser.quotedRegexColumnNames"
  //区分大小写,默认值TRUE
  val SPARK_SQL_CASESENSITIVE = "spark.sql.caseSensitive"
  //启动使用hive的反序列化
  val SPARK_SQL_HIVE_CONVERTMETASTOREPARQUET = "spark.sql.hive.convertMetastoreParquet"

  //=======================HIVE配置==============================
  //开启动态分区，默认FALSE
  val HIVE_EXEC_DYNAMIC_PARTITION = "hive.exec.dynamic.partition"
  //动态分区的模式，默认STRICT，表示必须指定至少一个分区为静态分区，NONSTRICT模式表示允许所有的分区字段都可以使用动态分区。
  val HIVE_EXEC_DYNAMIC_PARTITION_MODE = "hive.exec.dynamic.partition.mode"
  //设置MAP/REDUCE 输出压缩，
  val HIVE_EXEC_COMPRESS_OUTPUT = "hive.exec.compress.output"
  //输出的压缩格式
  val MAPRED_OUTPUT_COMPRESSION_CODEC = "mapred.output.compression.codec"
  //对于SEQUENCEFILE输出，应使用哪种压缩类型（NONE，RECORD或BLOCK）。 建议使用BLOCK。
  val MAPRED_OUTPUT_COMPRESSION_TYPE = "mapred.output.compression.type"
  //默认输入格式：COMBINEHIVEINPUTFORMAT。 如果遇到COMBINEHIVEINPUTFORMAT问题，请将其设置为HIVEINPUTFORMAT。
  val HIVE_INPUT_FORMAT = "hive.input.format"
  //默认值：FALSE，MERGE SMALL FILES AT THE END OF A MAP-ONLY JOB_
  val HIVE_MERGE_MAPFILES = "hive.merge.mapfiles"
  //默认值：FALSE，MERGE SMALL FILES AT THE END OF A MAP-REDUCE JOB_
  val HIVE_MERGE_MAPREDFILES = "hive.merge.mapredfiles"
  //默认值：256000000，SIZE OF MERGED FILES AT THE END OF THE JOB_
  val HIVE_MERGE_SIZE_PER_TASK = "hive.merge.size.per.task"
  //默认值：16000000
  val HIVE_MERGE_SMALLFILES_AVGSIZE = "hive.merge.smallfiles.avgsize"
  //决定每个MAP处理的最大的文件大小，单位为B
  val MAPRED_MAX_SPLIT_SIZE = "mapred.max.split.size"

  //=======================HBASE配置=============================
  val HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "hbase.zookeeper.property.clientPort"
  val HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum"
  val ZOOKEEPER_ZNODE_PARENT = "zookeeper.znode.parent"
  val HBASE_RPC_TIMEOUT = "hbase.rpc.timeout"
  val HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD = "hbase.client.scanner.timeout.period"

  //常用配置

  val DATA_BASE_TYPE_HBASE = "HBASE"

  val DATA_BASE_TYPE_HIVE = "HIVE"
}
