package com.platform.model

import java.net.URI

import com.platform.conf.{AppConfig, SparkSessionConf, SparkSessionSingleton}
import com.platform.db.MysqlDAL
import ml.combust.mleap.spark.SparkSupport._
import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.bundle.SparkBundleContext
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.mleap.SparkUtil
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}


object XgboostAntifraud {
  def main(args: Array[String]): Unit = {

    val logger: Logger = LoggerFactory.getLogger(getClass)

    //check if model exists in repo
    val modelName = "xgboostTest"
    val checkInfo = MysqlDAL().findModel(modelName)
    var modelVersion: Int = 0
    if(checkInfo != null){
      modelVersion = checkInfo.getOrElse("modelVersion", 0).asInstanceOf[Int]
    }
    if(modelVersion != 0){
      modelVersion = modelVersion + 1
    }

    // initial
    val SparkConf = SparkSessionConf.initSparkConf()
    val sc = SparkSessionSingleton.getInstance(SparkConf)

    // define schema
    val schema = new StructType(Array(
      StructField("y", IntegerType, nullable = true),
      StructField("sixMonthApplyTimes", IntegerType, nullable = true),
      StructField("grayScore", IntegerType, nullable = true),
      StructField("finalScore", IntegerType, nullable = true),
      StructField("classCode", StringType, nullable = true),
      StructField("seniority", DoubleType, nullable = true),
      StructField("cardPay", IntegerType, nullable = true),
      StructField("hireSource", StringType, nullable = true),
      StructField("eduDegreeCode", StringType, nullable = true),
      //      StructField("b", IntegerType, nullable = true),
      StructField("empStatus", StringType, nullable = true),
      //      StructField("D", StringType, nullable = true),
      StructField("factoryCode", StringType, nullable = true),
      StructField("directContactBlacklistNum", IntegerType, nullable = true),
      StructField("secondContactBlacklistNum", IntegerType, nullable = true),
      StructField("directContactTotalNum", IntegerType, nullable = true),
      StructField("contactsRouterRatio", DoubleType, nullable = true),
      StructField("indirectContactBlacklistNum", IntegerType, nullable = true),
      StructField("searchedOrgNum", IntegerType, nullable = true),
      StructField("oneMonthApplyLoan", IntegerType, nullable = true),
      StructField("threeMonthApplyLoan", IntegerType, nullable = true)
    ))

    // read data
    val data = sc.read.schema(schema).csv("hdfs:///user/hdfs/model/data/input_revised.csv")

    // data split
    val Array(trainSet, testSet) = data.randomSplit(Array(0.8, 0.2), seed = 12345L)

    // indexers
    val colTypeArray = data.dtypes
    var colArray: Array[String] = Array()
    colTypeArray.foreach(ele =>
      if ("StringType".equals(ele._2)) {
        colArray = colArray :+ ele._1
      }
    )
    val indexers = colArray.map {
      col => new StringIndexer().setInputCol(col).setOutputCol(col + "_indexed")
    }

    // assembler
    val columnsArray: Array[String] = Array(
      "sixMonthApplyTimes",
      "grayScore",
      "finalScore",
      "classCode_indexed",
      "seniority",
      "cardPay",
      "hireSource_indexed",
      "eduDegreeCode_indexed",
      "empStatus_indexed",
      "factoryCode_indexed",
      "directContactBlacklistNum",
      "secondContactBlacklistNum",
      "directContactTotalNum",
      "contactsRouterRatio",
      "indirectContactBlacklistNum",
      "searchedOrgNum",
      "oneMonthApplyLoan",
      "threeMonthApplyLoan"
    )
    val assembler: VectorAssembler = new VectorAssembler().setInputCols(columnsArray).setOutputCol("features")

    // feature transformation model
    val featureTransformModel = new Pipeline().setStages(indexers ++ Array(assembler)).fit(data)

    //  XGboost transformation model
    val xgbParam = Map(
      "eta" -> 0.1f,
      "missing" -> -999,
      "objective" -> "multi:softprob",
      "num_class" -> 2,
      "num_round" -> 1000,
      "num_workers" -> 2,
      "max_depth" -> 6
    )
    val xgbClassifierModel = new XGBoostClassifier(xgbParam).
      setLabelCol("y").
      setFeaturesCol("features").
      fit(featureTransformModel.transform(trainSet)).
      setLeafPredictionCol("leaf_prediction").
      setContribPredictionCol("contrib_prediction").
      setTreeLimit(2).
      setPredictionCol("prediction")

    // pipeline model
    val pipelineModel = SparkUtil.createPipelineModel(Array(featureTransformModel, xgbClassifierModel))


    // evaluate
    val train_predict = pipelineModel.transform(trainSet).select("y", "prediction")

    // print result
    //    train_predict.show(10)
    //    for(item <-pipelineModel.transform(trainSet).head(10)){
    //      println(item)
    //    }
    val test_transform = pipelineModel.transform(testSet)
    val test_predict = test_transform.select("y", "prediction")
    println(test_transform.printSchema())

    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("y").setPredictionCol("prediction")
    val train_accuracy = evaluator.evaluate(train_predict)
    val test_accuracy = evaluator.evaluate(test_predict)
    println(s"Train Accuracy = $train_accuracy,  Test Accuracy = $test_accuracy")

    // save bundle
    implicit val sbc: SparkBundleContext = SparkBundleContext.
      defaultContext.withDataset(pipelineModel.transform(trainSet),
      registerHdfs = true)

    // format path

    val hdfsPath: String = AppConfig.MODEL_PATH
    val path: String = hdfsPath + modelName + "_" + modelVersion.toString + ".zip"
    logger.warn(s"HDFS Path:$path")

    //serialize

    // save to hdfs
    pipelineModel.writeBundle.save(new URI("hdfs://"+path))

    // load model from hdfs
    val verificationModel = new URI("hdfs://"+path).loadMleapBundle().get.root
    val verify_train_prediction = verificationModel.transform(testSet)
    // print result
    train_predict.show(10)
    for(item <-verify_train_prediction.head(10)){
      println(item)
    }

    // save bundle local
    //    for(bundle <- managed(BundleFile("jar:file:/tmp/mleap-examples/simple-json.zip"))) {
    //      pipelineModel.writeBundle.save(bundle)(sbc).get
    //    }

    MysqlDAL().saveModel(modelName, modelVersion, path)

    // stop
    sc.stop()
  }
}
