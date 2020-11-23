package com.platform.base.db

import java.sql.{PreparedStatement, ResultSet}

import com.alibaba.druid.pool.DruidPooledConnection
import com.alibaba.fastjson.{JSONArray, JSONObject}
import com.platform.base.conf.ConnectionFactory

import org.slf4j.{Logger, LoggerFactory}


case class CommonDB() {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def getDwdConfig() = {
    val sql = "SELECT * FROM dwd_config WHERE switchs='on' AND job_status!='succ' ORDER BY step"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var obj = new JSONObject()
    var projectListMap = new JSONObject()
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      rs = pstmt.executeQuery()
      while (rs.next()){
        obj = new JSONObject()
        val projectName = rs.getString("project_name")

        obj.put("project_name",projectName)
        obj.put("job_name",rs.getString("job_name"))
        obj.put("job_status",rs.getString("job_status"))
        obj.put("job_remark",rs.getString("job_remark"))
        obj.put("step",rs.getString("step"))
        obj.put("id",rs.getString("id"))

        if (projectListMap.containsKey(projectName)){
          val dbTypeObj = projectListMap.getJSONArray(projectName)
          dbTypeObj.add(obj)
          projectListMap.put(projectName,dbTypeObj)
        }else{
          val dbTypeObj = new JSONArray()
          dbTypeObj.add(obj)
          projectListMap.put(projectName,dbTypeObj)
        }
      }
    }catch {
      case e:Exception=>{
        logger.error(s"getDwdConfig from db error $e")
        throw new RuntimeException(
          s"get dwd config from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    projectListMap
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
    val sql = "SELECT TO_DAYS(NOW())-TO_DAYS(MAX(end_time)) AS diffDay FROM dwd_config"
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
        logger.error(s"getLastDayDiffNow dwd_config from db error $e")
        throw new RuntimeException(
          s"getLastDayDiffNow dwd_config from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    count
  }


  def updateStatus(startTime:String,endTime:String,id:String,status:String,table:String): Unit ={
    val sql = "update "+table+" SET job_status=?,start_time=?,end_time=? WHERE id=?"
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
        logger.error("updateStatus "+table+" from db error",e)
        throw new RuntimeException("updateStatus "+table+" from db error",e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }



  def updateAllStatus(table:String): Unit ={
    val sql = "update "+table+" SET job_status='init'"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.executeUpdate()
    }catch {
      case e:Exception=>{
        logger.error("updateAllStatus "+table+" from db error",e)
        throw new RuntimeException("updateAllStatus "+table+" from db error",e)
      }
    }finally {
      ConnectionFactory.closeConnection(null,pstmt,connect)
    }
  }


  def getDwdExecuteConfig(id:String) = {
    val sql = "SELECT * FROM dwd_execute WHERE dwd_config_id=? ORDER BY step"
    var connect: DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    var obj = new JSONObject()
    val executeList = new JSONArray()
    try{
      connect = ConnectionFactory.getConnection()
      pstmt = connect.prepareStatement(sql)
      pstmt.setString(1,id)
      rs = pstmt.executeQuery()
      while (rs.next()){
        obj = new JSONObject()

        obj.put("id",rs.getString("id"))
        obj.put("sql",rs.getString("sqls"))
        obj.put("step",rs.getString("step"))
        obj.put("storage",rs.getString("storage"))
        obj.put("tmp_view",rs.getString("tmp_view"))
        obj.put("hive_table",rs.getString("hive_table"))
        obj.put("hive_db",rs.getString("hive_db"))
        obj.put("generate_id", rs.getBoolean("generate_id"))
        executeList.add(obj)
      }
    }catch {
      case e:Exception=>{
        logger.error(s"getDwdExecuteConfig from db error $e")
        throw new RuntimeException(
          s"get dwd execute config from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs,pstmt,connect)
    }
    executeList
  }



}
