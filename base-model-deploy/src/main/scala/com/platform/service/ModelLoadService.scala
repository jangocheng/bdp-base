package com.platform.service

import java.net.URI
import java.util

import com.platform.entity.ModelRecord
import ml.bundle.hdfs.HadoopBundleFileSystem
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.MleapSupport._
import ml.combust.mleap.runtime.frame.{DefaultLeapFrame, Row, Transformer}
import ml.combust.mleap.tensor.DenseTensor
import org.apache.hadoop.fs.FileSystem
import org.springframework.stereotype.Service
import resource._


/**
  *
  * author: wlhbdp
  * create: 2020-05-27 14:28
  */
@Service
case class ModelLoadService() {

  /**
    * 从本地文件系统上加载模型
    * @param path 模型路径，形如: jar:file:/tmp/simple-spark-pipeline.zip
    * @return ml.combust.mleap.runtime.frame.Transformer
    */
  def loadModelFromFile(path:String): Transformer = {
    val bundle = (for(bundleFile <- managed(BundleFile(path))) yield {
      bundleFile.loadMleapBundle().get
    }).opt.get

    // transform the dataframe using our pipeline
    val transformer: Transformer = bundle.root
    transformer
  }

  def loadXgBoostModelFromHDFS(path:String, fs: FileSystem): Transformer = {
    val hdfsPath = "hdfs://" + path
    val bundleFs = new HadoopBundleFileSystem(fs)

    // Create an implicit custom mleap context for saving/loading
    implicit val customMleapContext: MleapContext = MleapContext.defaultContext.copy(
      registry = MleapContext.defaultContext.bundleRegistry.registerFileSystem(bundleFs)
    )
    new URI(hdfsPath).loadMleapBundle().get.root
  }

  /**
    * 利用 ml.combust.mleap.runtime.frame.Transformer 对输入数据进行预测
    * @param modelRecord 输入数据，包含数据的schema
    * @param transformer ml.combust.mleap.runtime.frame.Transformer
    * @return Seq[Row]
    */
  def predict(modelRecord: ModelRecord, transformer: Transformer): util.ArrayList[Object] = {
    // create a simple LeapFrame to transform
    val frame = DefaultLeapFrame(modelRecord.schema, modelRecord.rows)
    val framePredict = transformer.transform(frame).get
    val dataPredict = framePredict.dataset
    val outputSchema = framePredict.schema

    // outputSchema.print()
    val index = outputSchema.indexOf(modelRecord.output.fieldName).get
    val rows = new util.ArrayList[Object]()
    for (n <- dataPredict.indices) {
      if (modelRecord.output.fieldType == "tensor") {
        val target = dataPredict(n).getAs[DenseTensor[Double]](index)
        rows.add(target.values)
      } else {
        val target = dataPredict(n).getAs[Object](index)
        rows.add(target)
      }
    }

    rows
  }

  def predict(modelRecord: ModelRecord, transformer: Transformer, isXgboost:Boolean): Seq[Row] = {
    val frame = DefaultLeapFrame(modelRecord.schema, modelRecord.rows)
    val framePredict = transformer.transform(frame).get
    val dataPredict = framePredict.dataset
    dataPredict
  }

}
