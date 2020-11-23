package com.platform.base.common

import com.platform.utils.PropertiUtils

object HDFSCommon {

  private val hdfsProps = PropertiUtils.init("hdfs.properties")

  /**
    * application hdfs 路径
    */
  val HDFS_URL_APPLICATION = hdfsProps.getProperty("hdfs.url.application")

  /**
    * mongoshake hdfs 路径
    */
  val HDFS_URL_MONGO_SHAKE = hdfsProps.getProperty("hdfs.url.mongoshake")

  /**
    * canal hdfs 路径
    */
  val HDFS_URL_CANAL = hdfsProps.getProperty("hdfs.url.canal")

  /**
    * salary hdfs 路径
    */
  val HDFS_URL_SALARY = hdfsProps.getProperty("hdfs.url.salary")

}
