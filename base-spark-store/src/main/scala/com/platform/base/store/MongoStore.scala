package com.platform.store

import com.platform.base.common.DataTypeCommon
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.types.LongType

case class MongoStore() {

  def save(dataSet : DataFrame, sparkSession:SparkSession): Unit ={
    import org.apache.spark.sql.functions._
    //新增分区列
    dataSet.withColumn("partition_month",
      from_unixtime(col("logTs").cast(LongType)/1000.0,"yyyy-MM")
    ).repartition(1).persist().write.mode(SaveMode.Append).insertInto(DataTypeCommon.base_MONGO_COMMON)

  }
}
