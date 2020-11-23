package com.platform.realtimereport.consumer

import com.alibaba.fastjson.{JSON, JSONException, JSONObject}
import com.platform.streaming.config.AppConfig
import org.slf4j.{Logger, LoggerFactory}

/**
  * 原始数据预处理，仅保留json格式数据
  * author: wlhbdp
  */
case class OriginalDataHandler() {

  private val logger:Logger = LoggerFactory.getLogger(getClass)

  /**
    * 过滤原始数据中的null
    * @param line 字符串
    * @return 过滤后的json数据
    */
  def filterOriginalData(line: String): Option[JSONObject] = {
    var result: Option[JSONObject] = None
    try {
      result = line match {
        case null => None
        case _ => {
          val json = JSON.parseObject(line)
          if (AppConfig.RUN_MODE_STANDALONE) {
            println(line)
          }
          Some(json)
        }
      }
    } catch {
      case e: Exception =>
        logger.error("数据转JSON失败：[" + line +"]", e)
      None
    }

    result
  }

}
