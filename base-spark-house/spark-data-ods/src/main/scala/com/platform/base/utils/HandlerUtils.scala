package com.platform.base.utils

import com.alibaba.fastjson.JSONObject

case class HandlerUtils() {


  def createAddPartitionSql(tableName:String,partitionValue:String,hdfsUrl:String) = {
    val partitionSql =  new StringBuffer("ALTER TABLE ")
    partitionSql.append(tableName)
    partitionSql.append(" ADD PARTITION(partition_time='")
    partitionSql.append(partitionValue)
    partitionSql.append("')")
    partitionSql.append(" LOCATION '")
    partitionSql.append(hdfsUrl)
    partitionSql.append(partitionValue)
    partitionSql.append("/'")
    partitionSql.toString
  }

  def createDropPartitionSql(tableName:String,partitionValue:String) = {
    val dropPartitionSql = new StringBuffer("ALTER TABLE ")
    dropPartitionSql.append(tableName)
    dropPartitionSql.append(" DROP IF EXISTS PARTITION (partition_time='")
    dropPartitionSql.append(partitionValue)
    dropPartitionSql.append("')")
    dropPartitionSql.toString
  }


  def loadSqlIncrement(jsonObj:JSONObject,partitionValue:String) = {
    val loadSql = new StringBuffer()
    loadSql.append("INSERT INTO TABLE ")
      .append(jsonObj.getString("hive_db"))
      .append(".")
      .append(jsonObj.getString("hive_table"))
      .append(" SELECT * FROM ")
      .append(jsonObj.getString("hive_tmp_db"))
      .append(".")
      .append(jsonObj.getString("hive_tmp_table"))
      .append(" WHERE partition_time='")
      .append(partitionValue)
      .append("'")
    loadSql.toString
  }

  def loadSqlFull(jsonObj:JSONObject) = {
    val partitionTime = DateTimeUtils.getYesterday
    val loadSql = new StringBuffer()
    loadSql.append("INSERT OVERWRITE TABLE ")
      .append(jsonObj.getString("hive_db"))
      .append(".")
      .append(jsonObj.getString("hive_table"))
      .append(" SELECT *,'")
      .append(partitionTime)
      .append("'as partition_time FROM ")
      .append(jsonObj.getString("hive_tmp_db"))
      .append(".")
      .append(jsonObj.getString("hive_tmp_table"))
    loadSql.toString
  }

}
