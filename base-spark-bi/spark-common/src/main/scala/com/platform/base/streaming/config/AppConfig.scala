package com.platform.streaming.config

import com.platform.streaming.constant.SparkCommon
import com.typesafe.config.{Config, ConfigFactory}

object AppConfig {

  val conf: Config = ConfigFactory.load

  /**
    * 设置spark app的名字
    */
  val APP_NAME : String =  conf.getString(SparkCommon.SPARK_CONFIG_APP_NAME)

  /**
    * stream调度间隔
    */
  val SCHEDULING_INTERVAL : Long = conf.getLong(SparkCommon.SPARK_STREAMING_SCHEDUL_INTERVAL)


  /**
    * check point url
    */
  val CHECK_POINT : String =  conf.getString(SparkCommon.SPARK_CONFIG_CHECK_POINT)
  /**
    * spark日志级别
    */
  val LOG_LEVEL : String = conf.getString(SparkCommon.SPARK_CONFIG_SET_LOG_LEVEL)

  /**
    * spark RDD 分区数
    */
  val NUM_RDD : Int = conf.getInt(SparkCommon.SPARK_CONFIG_NUM_RDD_PARTITIONS)

  /**
    * 内存for存储比例
    */
  val MEMORY_FOR_STORAGE_FRACTION : String = conf.getString(SparkCommon.SPARK_CONFIG_MEMORY_STORAGE_FRACTION)

  /**
    * 内存exceute的比例
    */
  val MEMORY_FOR_SHUFFLE_FRACTION : String = conf.getString(SparkCommon.SPARK_CONFIG_EXECUTOR_MEMORY_FRACTION)

  /**
    * set user memory在整个 executor memory 的占比。
    */
  val MEMORY_FRACTION : String = conf.getString(SparkCommon.SPARK_CONFIG_MEMORY_FRACTION)
  /**
    * 是否开启调整内存存储比例功能
    */
  val MEMORY_LEGACY_MODE : String = conf.getString(SparkCommon.SPARK_CONFIG_MEMORY_USE_LEGACY_MODE)


  /**
    * shuffle类型
    */
  val SHUFFLE_TYPE : String = conf.getString(SparkCommon.SPARK_SHUFFLE_TYPE)

  /**
    * shuffle缓存大小
    */
  val SHUFFLE_BUFF_SIZE : String = conf.getString(SparkCommon.SPARK_SHUFFLE_BUFFILE_SIZE)

  /**
    * shuffle read 拉取数据的最大次数
    */
  val SHUFFLE_FETCH_DATA_TIMES : String = conf.getString(SparkCommon.SPARK_SHUFFLE_BUFFILE_MAXTRY)

  /**
    * shuffle read 拉取数据的间隔
    */

  val SHUFFLE_FECTH_DATA_INTERVAL : String = conf.getString(SparkCommon.SPARK_SHUFFLE_BUFFILE_FETCH_INTERVAL)

  /**
    * spark 程序序列化方式
    */
  val SERIALIZER_TYPE : String = conf.getString(SparkCommon.SPARK_CONFIG_SERIALIZER_TYPE)

  /**
    * spark Streaming 设置每个kafka partition读取消息的最大速率（默认为0,其表示不限制）
    */
  val KAFKA_MAX_RATE_PER_PARTITION : String = conf.getString(SparkCommon.SPARK_STREAMING_KAFKA_MAX_RATE_PER_PARTITION)

  /**
    * 开启反压,限制每个batch接收到的消息量，降低数据倾斜的风险
    */
  val BACKPRESSURE_ENABLED : String = conf.getString(SparkCommon.SPARK_STREAMING_BACKPRESSURE_ENABLED)

  /**
    * kafka topics
    */
  val REAL_TIME_KAFKA_TOPICS : String = conf.getString(SparkCommon.SPARK_STREAMING_KAFKA_REALTIME_TOPICS)

  /**
    * kafka brokers
    */
  val KAFKA_BROKERS : String = conf.getString(SparkCommon.SPARK_STREAMING_KAFKA_BROKERS)


  /**
    * kafka offset
    */
  val KAFKA_OFFSET : String = conf.getString(SparkCommon.SPARK_STREAMING_KAFKA_OFFSET)

  /**
    * kafka groupid
    */
  val KAFKA_GROUPID : String = conf.getString(SparkCommon.SPARK_STREAMING_KAFKA_GROUP_ID)



  /**
    * jdbc url
    */
  val DB_JDBC_URL : String = conf.getString(SparkCommon.DB_JDBC_URL)

  /**
    * jdbc username
    */
  val DB_USER_NAME : String = conf.getString(SparkCommon.DB_USER_NAME)

  /**
    * jdbc password
    */
  val DB_PASSWORD :String = conf.getString(SparkCommon.DB_PASSWORD)

  /**
    * db driver
    */
  val DB_DRIVER_CLASS_NAME : String = conf.getString(SparkCommon.DB_DRIVER_CLASS_NAME)

  /**
    * Redis连接相关配置
    */
  val REDIS_URL: String = conf.getString(SparkCommon.REDIS_URL)

  val REDIS_PORT: Int = conf.getInt(SparkCommon.REDIS_PORT)

  val REDIS_MAXIDLE:Int = conf.getInt(SparkCommon.REDIS_MAXIDLE)

  val REDIS_DB: Int = conf.getInt(SparkCommon.REDIS_DB)

  val REDIS_SECRET: String = conf.getString(SparkCommon.REDIS_SECRET)

  val REDIS_TIMEOUT:Int = conf.getInt(SparkCommon.REDIS_TIMEOUT)

  val REDIS_MAX_CONNECTIONS: Int = conf.getInt(SparkCommon.REDIS_MAX_CONNECTIONS)

  val REDIS_EXHAUSTED_BEHAVIOUR: Int = conf.getInt(SparkCommon.REDIS_EXHAUSTED_BEHAVIOUR)

  /**
    * offset保存表
    */
  val TABLE_KAFKA_OFFSET : String = conf.getString(SparkCommon.DB_TABLE_KAFKA_OFFSET)

  /**
    * spark是否运行在standalone模式下
    */
  val RUN_MODE_STANDALONE: Boolean = conf.getBoolean(SparkCommon.SPARK_RUN_MODE_STANDALONE)
}
