package com.platform.migration.process

import java.util

import com.platform.migration.common.CommonConstant
import com.platform.migration.common.singleton.HbaseConfSingleton
import com.platform.migration.common.util.{HbaseScanUtils, HiveSqlUtils}
import com.platform.migration.entity.TaskSchema
import com.platform.migration.entity.vo.StepSchemaVO
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

case class TaskProcessing() {
  def processForMongo(sparkSession: SparkSession, taskSchema: TaskSchema, stepSchema: StepSchemaVO): _root_.org.apache.spark.sql.DataFrame = ???


  def process(taskSchema: TaskSchema, sparkSession: SparkSession): DataFrame = {
    var df = sparkSession.emptyDataFrame

    import scala.collection.JavaConversions._
    for (stepSchema <- taskSchema.getSteps) {
      stepSchema.getDbType match {
        case CommonConstant.DATA_BASE_TYPE_HBASE => {
          df = TaskProcessing().processForHbase(sparkSession, taskSchema, stepSchema)
        }
        case CommonConstant.DATA_BASE_TYPE_HIVE => {
          df = TaskProcessing().processForHive(sparkSession, taskSchema, stepSchema)
        }
        case _ =>
          sys.error("Steps中的operationType值不在可选择范围内，请重新开发")
          sys.exit(1)
      }
    }
    df
  }


  /**
    * 读取hive
    *
    * @param sparkSession
    * @param taskSchema
    * @param stepSchema
    * @return
    */
  def processForHive(sparkSession: SparkSession, taskSchema: TaskSchema, stepSchema: StepSchemaVO): DataFrame = {
    //判断sql的有效性
    if (!StringUtils.isNotBlank(stepSchema.getSql)) {
      throw new RuntimeException(s"查询的sql不能为空，请及时查看! 信息如下：\nstep:$stepSchema")
    }

    //根据sql读取数据
    val sql: String = HiveSqlUtils.fillParam(stepSchema.getSql, stepSchema.getParams)
    val df: DataFrame = sparkSession.sql(sql)

    //保存数据
    if (stepSchema.getTempView != null && stepSchema.getTempView.nonEmpty) {
      df.createTempView(stepSchema.getTempView)
    }
    df
  }


  /**
    * 读取hbase
    *
    * @param sparkSession
    * @param taskSchema
    * @param stepSchema
    * @return
    */
  def processForHbase(sparkSession: SparkSession, taskSchema: TaskSchema, stepSchema: StepSchemaVO): DataFrame = {

    //初始化Hbase配置
    val hBaseConf = HbaseConfSingleton.getInstance()
    HbaseScanUtils.fillParam(hBaseConf, stepSchema.getParams)

    //读取数据
    val rdd = sparkSession.sparkContext.newAPIHadoopRDD(hBaseConf,
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result])

    //rdd转换DataFrame
    val df = rddToDataFrame(sparkSession, rdd, taskSchema.getTableSchema)

    //保存数据
    if (stepSchema.getTempView != null && stepSchema.getTempView.nonEmpty) {
      df.createTempView(stepSchema.getTempView)
    }
    df
  }

  private def rddToDataFrame(sparkSession: SparkSession, rdd: RDD[(ImmutableBytesWritable, Result)], tableSchemaStr: String): DataFrame = {
    //定义row的StructType
    val tableSchema: StructType = DataType.fromJson(tableSchemaStr).asInstanceOf[StructType]

    //rdd 转成row
    val keys: util.List[String] = new util.ArrayList[String]()
    tableSchema.foreach(column => {
      keys.add(column.name)
    })

    val rowRDD: RDD[Row] = rdd.map(record => {

      val cells = record._2.rawCells()
      val map: util.HashMap[String, String] = new util.HashMap()

      cells.foreach(cell => {
        val qualifier = new String(cell.getQualifierArray, cell.getQualifierOffset, cell.getQualifierLength)
        val value = new String(cell.getValueArray, cell.getValueOffset, cell.getValueLength)
        map.put(qualifier, value)
      })

      val values: Array[String] = new Array[String](keys.size())
      for (index <- 0 until keys.size()) {
        values(index) = map.get(keys.get(index))
      }

      Row.fromSeq(values)

    })

    //rdd转化DataFrame
    sparkSession.sqlContext.createDataFrame(rowRDD, tableSchema)
  }


}

