package com.platform.handler


import com.platform.base.common.HDFSCommon
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

case class StoreHandler() {

  def applyApplication(sparkSession: SparkSession): Unit ={

    val hadoopConf = sparkSession.sparkContext.hadoopConfiguration
    val path = new Path(HDFSCommon.HDFS_URL_APPLICATION)
    val hdfs = FileSystem.get(hadoopConf)
    val applicationHdfs = hdfs.listStatus(path)

    applicationHdfs.foreach(catalog =>{
      //获取文件目录
      val folders = hdfs.listStatus(catalog.getPath)
      if (folders.size==0){
        //删除该路径
        if(hdfs.exists(catalog.getPath)){
          hdfs.delete(catalog.getPath,false)
        }
      }else{
        folders.foreach(fileArray =>{
          val filePath = fileArray.getPath
          if(!filePath.getName.endsWith("tmp")){
            new DataHandler().createApplicationDataHandler(filePath.toString,sparkSession)
            if (hdfs.exists(filePath)){
              hdfs.delete(filePath,true)
            }
          }
        })
      }
    })
  }



  def applyCanal(sparkSession: SparkSession): Unit ={
    val hadoopConf = sparkSession.sparkContext.hadoopConfiguration
    val path = new Path(HDFSCommon.HDFS_URL_CANAL)
    val hdfs = FileSystem.get(hadoopConf)
    val canalHdfs = hdfs.listStatus(path)

    canalHdfs.foreach(catalog =>{
      //获取文件目录
      val folders = hdfs.listStatus(catalog.getPath)
      if (folders.size==0){
        //删除该路径
        if(hdfs.exists(catalog.getPath)){
          hdfs.delete(catalog.getPath,false)
        }
      }else{
        folders.foreach(fileArray =>{
          val filePath = fileArray.getPath
          if(!filePath.getName.endsWith("tmp")){
            new DataHandler().createCanalDataHandler(filePath.toString,sparkSession)
            if (hdfs.exists(filePath)){
              hdfs.delete(filePath,true)
            }
          }
        })
      }
    })
  }


  def applyMongo(sparkSession: SparkSession): Unit ={
    val hadoopConf = sparkSession.sparkContext.hadoopConfiguration
    val path = new Path(HDFSCommon.HDFS_URL_MONGO_SHAKE)
    val hdfs = FileSystem.get(hadoopConf)
    val mongoHdfs = hdfs.listStatus(path)

    mongoHdfs.foreach(catalog =>{
      //获取文件目录
      val folders = hdfs.listStatus(catalog.getPath)
      if (folders.size==0){
        //删除该路径
        if(hdfs.exists(catalog.getPath)){
          hdfs.delete(catalog.getPath,false)
        }
      }else{
        folders.foreach(fileArray =>{
          val filePath = fileArray.getPath
          if(!filePath.getName.endsWith("tmp")){
            new DataHandler().createMongoShakeDataHandler(filePath.toString,sparkSession)
            if (hdfs.exists(filePath)){
              hdfs.delete(filePath,true)
            }
          }
        })
      }
    })
  }


  def applySalary(sparkSession: SparkSession): Unit ={
    val hadoopConf = sparkSession.sparkContext.hadoopConfiguration
    val path = new Path(HDFSCommon.HDFS_URL_SALARY)
    val hdfs = FileSystem.get(hadoopConf)
    val salaryHdfs = hdfs.listStatus(path)

    salaryHdfs.foreach(catalog =>{
      //获取文件目录
      val folders = hdfs.listStatus(catalog.getPath)
      if (folders.size==0){
        //删除该路径
        if(hdfs.exists(catalog.getPath)){
          hdfs.delete(catalog.getPath,false)
        }
      }else{
        folders.foreach(fileArray =>{
          val filePath = fileArray.getPath
          if(!filePath.getName.endsWith("tmp")){
            new DataHandler().createSalaryDataHandler(filePath.toString,sparkSession)
            if (hdfs.exists(filePath)){
              hdfs.delete(filePath,true)
            }
          }
        })
      }
    })
  }


}
