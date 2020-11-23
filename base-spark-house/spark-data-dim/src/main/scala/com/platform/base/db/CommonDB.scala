package com.platform.base.db

import java.sql.{PreparedStatement, ResultSet}

import com.alibaba.druid.pool.DruidPooledConnection
import com.alibaba.fastjson.{JSONArray, JSONObject}
import com.platform.base.conf.ConnectionFactory
import org.slf4j.{Logger, LoggerFactory}


case class CommonDB() {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def getDimConfig() = {
    val sql = "SELECT * FROM dim_config WHERE switchs='on' AND job_status!='succ' ORDER BY step"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var obj = new JSONObject()
    val dimArray = new JSONArray()
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      rs = pstmt.executeQuery()
      while (rs.next()){
        obj = new JSONObject()
        obj.put("sql",rs.getString("sqls"))
        obj.put("hive_table",rs.getString("hive_table"))
        obj.put("hive_db",rs.getString("hive_db"))
        obj.put("step",rs.getString("step"))
        obj.put("id",rs.getString("id"))
        dimArray.add(obj)
      }
    }catch {
      case e:Exception=>{
        logger.error(s"getDimConfig from db error $e")
        throw new RuntimeException(
          s"get dim config from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    dimArray
  }


  def getDimNotSuccCount() = {
    val sql = "SELECT COUNT(*) FROM dim_config WHERE switchs='on' AND job_status!='succ'"
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
        logger.error(s"getDimNotSuccCount from db error $e")
        throw new RuntimeException(
          s"getDimNotSuccCount from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }


  def getDwdNotSuccCount() = {
    val sql = "SELECT COUNT(*) FROM dwd_config WHERE switchs='on' AND job_status!='succ'"
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
        logger.error(s"getDwdNotSuccCount from db error $e")
        throw new RuntimeException(
          s"getDwdNotSuccCount from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }

  def getOdsNotSuccCount() = {
    val sql = "SELECT COUNT(*) FROM ods_config WHERE switchs='on' AND job_status!='succ'"
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
        logger.error("getOdsNotSuccCount from db error:",e)
        throw new RuntimeException(
          "getOdsNotSuccCount from db error:",e)
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }


  def getLastDayDiffNow() = {
    val sql = "SELECT TO_DAYS(NOW())-TO_DAYS(MAX(end_time)) AS diffDay FROM dim_config where switchs='on' AND job_status='succ'"
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
        logger.error(s"getLastDayDiffNow dim_config from db error $e")
        throw new RuntimeException(
          s"getLastDayDiffNow dim_config from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }


  def updateStatus(startTime:String,endTime:String,id:String,status:String): Unit ={
    val sql = "update dim_config SET job_status=?,start_time=?,end_time=? WHERE id=?"
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
        logger.error("updateStatus dim_config from db error",e)
        throw new RuntimeException("updateStatus dim_config from db error",e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }



  def updateAllStatus(): Unit ={
    val sql = "update dim_config SET job_status='init'"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.executeUpdate()
    }catch {
      case e:Exception=>{
        logger.error("updateAllStatus dim_config from db error",e)
        throw new RuntimeException("updateAllStatus dim_config from db error",e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }

}
