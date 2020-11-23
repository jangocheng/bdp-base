package com.platform.migration.common.util

import java.util

import com.alibaba.fastjson.JSON
import com.platform.migration.common.StructTypeConstant
import com.platform.migration.entity.TaskSchema
import com.platform.migration.entity.vo.{PartitionSchemaVO, StepParamVO, StepSchemaVO, TbSettingSchemaVO}
import org.apache.commons.lang3.StringUtils

object HiveSqlUtils {


  def saveSql(taskSchema: TaskSchema): String = {
    //初始化数据
    val steps: util.List[StepSchemaVO] = taskSchema.getSteps
    val tmpTableName: String = steps.get(steps.size() - 1).getTempView
    val targetTable: String = taskSchema.getTableName
    val targetDb: String = taskSchema.getSaveDB
    val partitions: util.List[PartitionSchemaVO] = taskSchema.getPartitions
    val isIncrement = taskSchema.isHasIncrement


    //拼接保存数据的sql
    val str = new StringBuffer

    //判断是否增量
    if (isIncrement) {
      str.append("INSERT INTO TABLE ")
    } else {
      str.append("INSERT OVERWRITE TABLE ")
    }
    str.append(targetDb + "." + targetTable)

    //分区字段不为空
    if (!partitions.isEmpty) {
      str.append(" partition(")
      import scala.collection.JavaConversions._
      for (index <- 0 until partitions.size()) {
        str.append(partitions(index).getPartitionName)
        if (!(index == partitions.size() - 1)) {
          str.append(",")
        }
      }
      str.append(") ")
    }
    str.append(" SELECT * FROM " + tmpTableName)
    str.toString
  }

  def fillParam(sql: String, params: java.util.List[StepParamVO]): String = {
    import scala.collection.JavaConversions._
    for (param <- params) {
      if (null != param && StringUtils.isNotBlank(param.getParamName)
        && StringUtils.isNotBlank(param.getParamValue)) {
        sql.replace("#{" + param.getParamName + "}", param.getParamValue)
      }
    }
    sql
  }

  private def generateColumn(tableSchemaStr: String): String = {
    val str = new StringBuffer
    val tableSchema = JSON.parseObject(tableSchemaStr)
    val columns = JSON.parseArray(tableSchema.getString(StructTypeConstant.COLUMNS))

    import scala.collection.JavaConversions._
    columns.foreach(columnStr => {
      val column = JSON.parseObject(columnStr.toString)
      str.append("\n\t")
      str.append(column.getString(StructTypeConstant.COLUMN_NAME))
      str.append(" " + column.getString(StructTypeConstant.COLUMN_TYPE).toUpperCase + ",")

    })
    str.toString.substring(0, str.lastIndexOf(","))
  }

  private def generatePartition(partitions: java.util.List[PartitionSchemaVO]): String = {
    val str = new StringBuffer
    str.append("PARTITIONED BY ( ")

    import scala.collection.JavaConversions._
    for (index <- 0 until partitions.size()) {
      str.append(partitions(index).getPartitionName + " " + partitions(index).getPartitionType)
      if (index != partitions.size() - 1) {
        str.append(",")
      }
    }
    str.append(") ")
    str.toString
  }

  private def generateTbProperties(settings: TbSettingSchemaVO): String = {
    val str = new StringBuffer

    if (StringUtils.isNotBlank(settings.getRowFormat)) {
      str.append(" " + settings.getRowFormat)
    }

    if (StringUtils.isNotBlank(settings.getStoredType)) {
      str.append("  STORED AS " + settings.getStoredType)
    }

    if (StringUtils.isNotBlank(settings.getTbProperties)) {
      str.append(" TBLPROPERTIES( " + settings.getTbProperties + ")")
    }

    str.toString
  }

  def generateCreateTableForHive(taskSchema: TaskSchema): String = {
    val str = new StringBuffer
    str.append("CREATE TABLE if not exists ").append(taskSchema.getSaveDB + "." + taskSchema.getTableName).append("(")
    str.append(generateColumn(taskSchema.getTableSchema))
    str.append(") ")
    if (!taskSchema.getPartitions.isEmpty) {
      str.append(generatePartition(taskSchema.getPartitions))
    }
    str.append(generateTbProperties(taskSchema.getSettings))
    str.toString
  }

  def createTableSql(taskSchema: TaskSchema,fields:util.List[String]) ={
    val createSql = new StringBuffer("CREATE TABLE if not exists ").append(taskSchema.getSaveDB + "." + taskSchema.getTableName).append("(")

    for (i <-0 until fields.size()){
      createSql.append(fields.get(i)).append(" STRING")
      if (i!=(fields.size()-1)){
        createSql.append(",")
      }
    }
    createSql.append(") ")
    if (!taskSchema.getPartitions.isEmpty) {
      createSql.append(generatePartition(taskSchema.getPartitions))
    }
    createSql.append(generateTbProperties(taskSchema.getSettings))
    createSql.toString
  }





}
