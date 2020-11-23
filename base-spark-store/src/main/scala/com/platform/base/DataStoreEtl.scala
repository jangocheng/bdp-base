package com.platform

import com.platform.confs.{AppConfig, SparkETLConf, SparkSessionSingleton}
import com.platform.handler.StoreHandler


object DataStoreEtl {

  def main(args: Array[String]): Unit = {
    val sparkConf = SparkETLConf.initSparkConf
    val sparkSession = SparkSessionSingleton.getInstance(sparkConf)
    sparkSession.sql(AppConfig.SPARK_SQL_USER_DB)
    new StoreHandler().applyApplication(sparkSession)
    new StoreHandler().applyMongo(sparkSession)
    new StoreHandler().applyCanal(sparkSession)
    new StoreHandler().applySalary(sparkSession)
    sparkSession.stop()
  }
}
