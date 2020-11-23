package com.platform.hbase2max.common.conf

import com.platform.hbase2max.common.SparkCommon
import com.platform.hbase2max.common.utils.PropertyUtils

object Hbase2MaxConf {

  private val appConfigProps = PropertyUtils.init("application.properties")

  /**
    * 设置spark app的名字
    */
  val APP_NAME: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_APP_NAME)

  /**
    * check point url
    */
  val CHECK_POINT: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_CHECK_POINT)
  /**
    * spark日志级别
    */
  val LOG_LEVEL: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_SET_LOG_LEVEL)


  /**
    * 内存for存储比例
    */
  val MEMORY_FOR_STORAGE_FRACTION: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_MEMORY_STORAGE_FRACTION)

  /**
    * 内存exceute的比例
    */
  val MEMORY_FOR_SHUFFLE_FRACTION: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_EXECUTOR_MEMORY_FRACTION)

  /**
    * set user memory在整个 executor memory 的占比。
    */
  val MEMORY_FRACTION: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_MEMORY_FRACTION)
  /**
    * 是否开启调整内存存储比例功能
    */
  val MEMORY_LEGACY_MODE: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_MEMORY_USE_LEGACY_MODE)


  /**
    * shuffle类型
    */
  val SHUFFLE_TYPE: String = appConfigProps.getProperty(SparkCommon.SPARK_SHUFFLE_TYPE)

  /**
    * shuffle缓存大小
    */
  val SHUFFLE_BUFF_SIZE: String = appConfigProps.getProperty(SparkCommon.SPARK_SHUFFLE_BUFFILE_SIZE)

  /**
    * shuffle read 拉取数据的最大次数
    */
  val SHUFFLE_FETCH_DATA_TIMES: String = appConfigProps.getProperty(SparkCommon.SPARK_SHUFFLE_BUFFILE_MAXTRY)

  /**
    * shuffle read 拉取数据的间隔
    */

  val SHUFFLE_FECTH_DATA_INTERVAL: String = appConfigProps.getProperty(SparkCommon.SPARK_SHUFFLE_BUFFILE_FETCH_INTERVAL)

  /**
    * spark 程序序列化方式
    */
  val SERIALIZER_TYPE: String = appConfigProps.getProperty(SparkCommon.SPARK_CONFIG_SERIALIZER_TYPE)


  /**
    * App
    */
  val RUN_MODE_STANDALONE: Boolean = {
    appConfigProps.getProperty(SparkCommon.APP_RUN_MODE, "false").toBoolean
  }

  val APP_ROOT_PATH = appConfigProps.getProperty(SparkCommon.APP_ROOT_PATH, "/spark-task")

  /**
    * zookeeper的root
    */
  val HBASE_ZOOKEEPER_PORT: String = appConfigProps.getProperty(SparkCommon.HBASE_ZOOKEEPER_PORT)
  /**
    * zookeeper的服务
    */
  val HBASE_ZOOKEEPER_QUORUM: String = appConfigProps.getProperty(SparkCommon.HBASE_ZOOKEEPER_QUORUM)

  /**
    * maxcompute的配置
    */
  val MAX_COMPUTE_ACCESS_ID: String = appConfigProps.getProperty(SparkCommon.MAX_COMPUTE_ACCESS_ID)
  val MAX_COMPUTE_ACCESS_KEY: String = appConfigProps.getProperty(SparkCommon.MAX_COMPUTE_ACCESS_KEY)
  val MAX_COMPUTE_PROJECT_NAME: String = appConfigProps.getProperty(SparkCommon.MAX_COMPUTE_PROJECT_NAME)
  val MAX_COMPUTE_END_POINT: String = appConfigProps.getProperty(SparkCommon.MAX_COMPUTE_END_POINT)
  val MAX_COMPUTE_TUNNEL_URL: String = appConfigProps.getProperty(SparkCommon.MAX_COMPUTE_TUNNEL_URL)


}
