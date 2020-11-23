package com.platform.realtimereport.consumer

import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.streaming.{ProcessorContext, StreamProcessStrategy}
import com.platform.streaming.service.BaseService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges}
import org.reflections.Reflections
import org.slf4j.LoggerFactory
import com.platform.streaming.config.AppConfig
import scala.collection.JavaConverters._
import scala.collection.mutable

case class StreamingDataHandler() {
  private val log = LoggerFactory.getLogger(getClass)

  def handleData(stream: InputDStream[ConsumerRecord[String, String]]): Unit = {
    val cacheStream = stream.flatMap(x =>
        OriginalDataHandler().filterOriginalData(x.value())
    ).filter(ele => ele!= null && !ele.isEmpty)

    //缓存RDD，供后续计算
    cacheStream.persist(StorageLevel.MEMORY_ONLY_SER_2)

    //所有RDD处理的入口
    val instances = initialRDDInstances(classOf[StreamProcessStrategy], ApplicationCommon.PROCESSOR_PACKAGE_NAME)
    instances.foreach(clazz =>
      if(clazz.isInstanceOf[StreamProcessStrategy]) {
        if (log.isInfoEnabled) {
          log.info("RDD处理类：" + clazz.getClass.getName)
        }
        //调用RDD处理逻辑
        ProcessorContext(clazz).executeStreamProcess(cacheStream)
      }
    )

    //更新Kafka offset
    stream.foreachRDD(rdd => {
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges]
      log.debug(s"Kafka Offsets directly get from Kafka ${offsetRanges.offsetRanges.mkString("[", " , ", "]")}")
      BaseService().updateOffset(rdd, AppConfig.KAFKA_GROUPID)
      stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges.offsetRanges)
    })
  }

  /**
    * 通过反射获取指定路径下实现了指定接口的所有类实例
    * @param underlying 指定接口
    * @param packageName 指定路径
    * @tparam T 接口类名称
    * @return 实现了指定接口的所有类实例
    */
  def initialRDDInstances[T](underlying: Class[T], packageName:String): mutable.Set[T] = {
    val reflects = new Reflections(packageName)
    val instances = reflects.getSubTypesOf(underlying).asScala.map { sub =>
      sub.newInstance()
    }
    instances
  }

}
