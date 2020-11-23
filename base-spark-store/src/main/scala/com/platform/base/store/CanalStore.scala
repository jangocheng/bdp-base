package com.platform.store

import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}
import org.apache.spark.sql.types.{DataType, LongType, StructType}
import org.apache.spark.storage.StorageLevel

case class CanalStore() {

  def save(dataSet : Dataset[String],sparkSession:SparkSession,schemaType:String,dataType:String): Unit ={
    val schema = DataType.fromJson(schemaType).asInstanceOf[StructType]
    val dataDF =  sparkSession.read.schema(schema).json(dataSet).persist(StorageLevel.MEMORY_AND_DISK)

    import org.apache.spark.sql.functions._
    dataDF.withColumn("partition_month",
      from_unixtime(col("logTs").cast(LongType)/1000.0,"yyyy-MM")
    ).repartition(1).persist().write.mode(SaveMode.Append).insertInto(dataType.replace(".","_"))

    dataDF.unpersist()
    dataSet.unpersist()
  }
}
