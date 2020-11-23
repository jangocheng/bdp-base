package com.platform.utils

import com.alibaba.fastjson.{JSON, JSONException, JSONObject}
import org.apache.spark.sql.Row
import org.slf4j.{Logger, LoggerFactory}

object DataAnalyticsOperate extends Serializable {

  private val logger: Logger = LoggerFactory.getLogger(DataAnalyticsOperate.getClass)

  def originDataHandler(line: String): Option[JSONObject] = {
    var res: Option[JSONObject] = None
    try {

      res = line match {
        case null => None
        case _ => {
          val json = JSON.parseObject(line)
          Some(json)
        }
      }

    } catch {
      case ex: JSONException => {
        logger.error("数据转换Json失败", ex)
      }
    }
    res
  }

}
