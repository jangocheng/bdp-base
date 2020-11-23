package com.platform.base

import com.platform.base.conf.{SparkETLConf, SparkSessionSingleton}
import com.platform.base.handler.{MongoHandler, MysqlHandler}

object BaseDataOds {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "hive")
    val sparkConf = SparkETLConf.initSparkConf()
    val sparkSession = SparkSessionSingleton.getInstance(sparkConf)
    new MysqlHandler().handler(sparkSession)
    new MongoHandler().handler(sparkSession)
    sparkSession.stop()
  }
}
