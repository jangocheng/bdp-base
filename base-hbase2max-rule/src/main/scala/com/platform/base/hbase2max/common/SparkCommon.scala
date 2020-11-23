package com.platform.hbase2max.common

object SparkCommon {
  /**
    * spark公共配置
    **/
  val SPARK_CONFIG_CHECK_POINT = "spark.commons.checkpoint"
  //app name
  val SPARK_CONFIG_APP_NAME = "spark.commons.appName"

  //log level
  val SPARK_CONFIG_SET_LOG_LEVEL = "spark.commons.logLevel"

  //是否开始自定义管理内存分配
  val SPARK_CONFIG_MEMORY_USE_LEGACY_MODE = "spark.memory.useLegacyMode"

  //序列化方式
  val SPARK_CONFIG_SERIALIZER_TYPE = "spark.serializer"


  // set user memory在整个 executor memory 的占比。
  val SPARK_CONFIG_MEMORY_FRACTION = "spark.memory.fraction"

  /**
    * shuffle 操作相关
    */

  //内存用来存储的比例,这部分内存中有多少可以用于Memory Store管理RDD Cache的数据
  val SPARK_CONFIG_MEMORY_STORAGE_FRACTION = "spark.storage.memoryFraction"

  //内存分配中execution比例大小
  val SPARK_CONFIG_EXECUTOR_MEMORY_FRACTION = "spark.shuffle.memoryFraction"

  //sparkStreaming.Shuffle.manager的类型(hash,sort)
  val SPARK_SHUFFLE_TYPE = "spark.shuffle.manager"

  //spark.shuffle缓存大小,默认32K 内存充足可以调到64,减少shuffle write过程中溢写磁盘文件的次数，也就可以减少磁盘IO次数
  val SPARK_SHUFFLE_BUFFILE_SIZE = "spark.shuffle.file.buffer"

  //spark shuffle read 拉取属于自己数据时重试的次数
  val SPARK_SHUFFLE_BUFFILE_MAXTRY = "spark.shuffle.io.maxRetries"

  //spark shuffle read 拉取属于自己数据时重试的间隔
  val SPARK_SHUFFLE_BUFFILE_FETCH_INTERVAL = "spark.shuffle.io.retryWait"


  /**
    * 模型启动配置
    */
  val APP_RUN_MODE = " app.mode.standalone"

  val APP_ROOT_PATH = "app.root.path"


  /**
    * hbase配置
    */
  val HBASE_ZOOKEEPER_PORT = "hbase.zookeeper.property.clientPort"
  val HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum"

  /**
    * maxcompute
    */

  val MAX_COMPUTE_ACCESS_ID = "maxcompute.accessId"
  val MAX_COMPUTE_ACCESS_KEY = "maxcompute.accessKey"
  val MAX_COMPUTE_PROJECT_NAME = "maxcompute.projectName"
  val MAX_COMPUTE_END_POINT = "maxcompute.endPoint"
  val MAX_COMPUTE_TUNNEL_URL = "maxcompute.tunnelUrl"

}
