package com.platform.base.db

import java.sql.{PreparedStatement, ResultSet, ResultSetMetaData}
import java.util
import java.util.ArrayList

import com.alibaba.druid.pool.DruidPooledConnection
import com.alibaba.fastjson.{JSONArray, JSONObject}
import com.platform.base.conf.ConnectionFactory

import org.slf4j.{Logger, LoggerFactory}


case class CommonDB() {

  val logger: Logger = LoggerFactory.getLogger(getClass)


  def getMysqlOrMongoOdsNotSuccCount(tableName:String) = {
    val sql = "SELECT COUNT(*) FROM "+tableName+" WHERE switchs='on' AND job_status!='succ'"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var count = 0l
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      rs = pstmt.executeQuery()
      while (rs.next()){
        count = rs.getLong(1)
      }
    }catch {
      case e:Exception=>{
        logger.error("get getMysqlOrMongoOdsNotSuccCount from db error,tableName:"+tableName,e)
        throw new RuntimeException(
          "get getMysqlOrMongoOdsNotSuccCount from db error,tableName:"+tableName,e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }

  def getOdsNotSuccCount(odsType:String) = {
    val sql = "SELECT COUNT(*) FROM ods_config WHERE switchs='on' AND job_status!='succ' and ods_type=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var count = 0l
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,odsType)
      rs = pstmt.executeQuery()
      while (rs.next()){
        count = rs.getLong(1)
      }
    }catch {
      case e:Exception=>{
        logger.error("getOdsNotSuccCount from db error,odsType:"+odsType,e)
        throw new RuntimeException(
          "getOdsNotSuccCount from db error,odsType:"+odsType,e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }

  def getLastDayDiffNow(odsType:String) = {
    val sql = "SELECT TO_DAYS(NOW())-TO_DAYS(MAX(end_time)) AS diffDay FROM ods_config where switchs='on' AND job_status='succ' and ods_type=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var count = 0l
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,odsType)
      rs = pstmt.executeQuery()
      while (rs.next()){
        count = rs.getLong(1)
      }
    }catch {
      case e:Exception=>{
        logger.error("getLastDayDiffNow ods_config from db error,odsType:"+odsType,e)
        throw new RuntimeException(
          "getLastDayDiffNow ods_config from db error,odsType:"+odsType,e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }

  def updateAllStatus(odsType:String): Unit ={
    val sql = "update ods_config SET job_status='init' where ods_type=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,odsType)
      pstmt.executeUpdate()
    }catch {
      case e:Exception=>{
        logger.error("updateAllStatus ods_config from db error,odsType:"+odsType,e)
        throw new RuntimeException("updateAllStatus ods_config from db error,odsType:"+odsType,e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }

  def updateStatus(startTime:String,endTime:String,id:String,status:String): Unit ={
    val sql = "update ods_config SET job_status=?,start_time=?,end_time=? WHERE id=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,status)
      pstmt.setString(2,startTime)
      pstmt.setString(3,endTime)
      pstmt.setString(4,id)
      pstmt.executeUpdate()
    }catch {
      case e:Exception=>{
        logger.error("updateStatus ods_config from db error",e)
        throw new RuntimeException("updateStatus ods_config from db error",e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }

  def getOdsConfig(odsType:String) = {
    val sql = "SELECT * FROM ods_config WHERE switchs='on' AND job_status!='succ' and ods_type=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var obj = new JSONObject()
    val dimArray = new JSONArray()
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,odsType)
      rs = pstmt.executeQuery()
      while (rs.next()){
        obj = new JSONObject()
        obj.put("hive_db",rs.getString("hive_db"))
        obj.put("hive_table",rs.getString("hive_table"))
        obj.put("hive_tmp_db",rs.getString("hive_tmp_db"))
        obj.put("hive_tmp_table",rs.getString("hive_tmp_table"))
        obj.put("id",rs.getString("id"))
        obj.put("columns_value",rs.getString("columns_value"))
        obj.put("ods_id",rs.getInt("ods_id"))
        dimArray.add(obj)
      }
    }catch {
      case e:Exception=>{
        logger.error("getOdsConfig from db error,odsType:"+odsType,e)
        throw new RuntimeException(
          "get ods config from db error,odsType:"+odsType,e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    dimArray
  }

  def getMongoOrMysqlOdsConfigById(odsId:String,tableName:String) = {
    val sql = "SELECT * FROM "+tableName+" WHERE id=?"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    val obj = new JSONObject()
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,odsId)
      rs = pstmt.executeQuery()
      while (rs.next()){
        obj.put("increment",rs.getString("increment"))
        obj.put("hdfs_url",rs.getString("hdfs_url"))
        obj.put("partition_value",rs.getString("partition_value"))
      }
    }catch {
      case e:Exception=>{
        logger.error("getMongoOrMysqlOdsConfigById from db error,tableName:"+tableName,e)
        throw new RuntimeException(
          "getMongoOrMysqlOdsConfigById from db error,tableName:"+tableName,e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    obj
  }
}
