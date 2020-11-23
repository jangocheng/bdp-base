package com.platform.realtimereport.streaming.processor

import com.alibaba.fastjson.JSONObject
import com.platform.realtimereport.common.constant.ApplicationCommon
import com.platform.realtimereport.config.ApplicationConfig
import com.platform.realtimereport.streaming.StreamProcessStrategy
import com.platform.realtimereport.streaming.rdd.RDDZfbDataProcess
import org.apache.spark.streaming.dstream.DStream

/**
  *
  * author: wlhbdp
  */
case class ZfbStreamProcessor() extends StreamProcessStrategy {

  override def processDataStream(cacheStream: DStream[JSONObject]): Unit = {
    //过滤支付宝user数据
    val zfbUserDataStream = cacheStream.filter(data =>
      filterRegisterAndIdentifiedData(data)
    ).cache()

    //对支付宝user数据进行统计计算
    logger.info("Begin process userRDD data...")
    zfbUserDataStream.foreachRDD(rdd => {
      logger.info("Cycling process userRDD data...")
      if (!rdd.isEmpty()) {
        logger.info("Enter process userRDD data...")
        RDDZfbDataProcess().aggregateRegisterAndIdentifiedData(rdd)
      }
    })
  }

  /**
    * 过滤mongodb中collection为userDO的json数据
    * @param data jsonObject
    * @return
    */
  def filterRegisterAndIdentifiedData(data: JSONObject): Boolean = {
    //若数据是userDO，则为注册用户
    //若数据是applyDO，当操作为insert，或操作为update且query为null，且两种操作下step=11
    val isUserDOOrApplyDO = data.getString(ApplicationCommon.NAMESPACE_KEY) match {
      case ApplicationConfig.ZFB_USER => true
      case ApplicationConfig.ZFB_APPLY => true
      case _ => false
    }

    isUserDOOrApplyDO
  }
}
