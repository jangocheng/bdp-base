package com.platform.store

import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}
import org.apache.spark.storage.StorageLevel

case class ProduceStore() {

  def save(dataSet : Dataset[String],sparkSession:SparkSession,schemaType:String,dataType:String): Unit ={
    val schema = DataType.fromJson(schemaType).asInstanceOf[StructType]
    val dataDF =  sparkSession.read.schema(schema).json(dataSet).persist(StorageLevel.MEMORY_AND_DISK)

    import org.apache.spark.sql.functions._
    dataDF.withColumn("partition_month",substring(col("createTime"),1,6))
      .repartition(1).persist().write.mode(SaveMode.Append).insertInto(dataType)

    dataDF.unpersist()
    dataSet.unpersist()
  }

}
