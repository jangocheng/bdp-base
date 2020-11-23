package com.platform.db

import java.sql.{PreparedStatement, ResultSet}

import com.alibaba.druid.pool.DruidPooledConnection
import com.platform.conf.ConnectionFactory
import org.slf4j.{Logger, LoggerFactory}




case class MysqlDAL() {

  val logger: Logger = LoggerFactory.getLogger(getClass)


  //find model

  def findModel(modelName : String) :Map[String, Any]  = {
    var connect : DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var rs : ResultSet = null
    try {
      connect = ConnectionFactory.getConnection
      pstmt = connect.prepareStatement(s"""select path, max(model_version) as model_version from model_repo where model_name = ? group by model_name""" )
      pstmt.setString(1, modelName)
      rs = pstmt.executeQuery()
      if(rs.next()){
        val path: String = rs.getString("path")
        val modelVersion: Int= rs.getInt("model_version")
        Map("path" -> path, "modelVersion" -> modelVersion)
      }else{
        null
      }
    } catch {
      case e: Exception => {
        logger.error(s"findModel from db error $e")
        throw new IllegalStateException(
          s"findModel from db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(rs, pstmt, connect)
    }
  }


  def saveModel(modelName: String, modelVersion: Int, path: String) : Unit ={
    var connect : DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var successCount = 0
    try {
      connect = ConnectionFactory.getConnection
      pstmt = connect.prepareStatement(s"""insert into model_repo (model_name, model_version, path) values(?,?,?)""")
      pstmt.setString(1, modelName)
      pstmt.setInt(2, modelVersion)
      pstmt.setString(3, path)

      successCount = pstmt.executeUpdate()
      if (successCount<=0) {
        logger.error(s"save model to db error : success count zero!")
        throw new Exception()
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
        logger.error(s"save offset to db error $e")
        throw new IllegalStateException(
          s"save offset to db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(null, pstmt, connect)
    }
  }

  def updateModel(modelName: String, modelVersion: Int, path: String) : Unit ={
    var connect : DruidPooledConnection = null
    var pstmt : PreparedStatement = null
    var successCount = 0
    try {
      connect = ConnectionFactory.getConnection
      pstmt = connect.prepareStatement(s"""insert overwrite model_repo}(model_name, model_version, path) values(?,?,?)""")
      pstmt.setString(1, modelName)
      pstmt.setInt(2, modelVersion)
      pstmt.setString(3, path)

      successCount = pstmt.executeUpdate()
      if (successCount<=0) {
        logger.error(s"save offset to db error : success count zero!")
        throw new Exception()
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
        logger.error(s"save offset to db error $e")
        throw new IllegalStateException(
          s"save offset to db error $e")
      }
    }finally {
      ConnectionFactory.closeConnection(null, pstmt, connect)
    }
  }
}
