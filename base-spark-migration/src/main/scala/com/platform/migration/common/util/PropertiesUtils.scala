package com.platform.migration.common.util

import java.util.Properties

import org.slf4j.{Logger, LoggerFactory}

object PropertiesUtils {
  @transient lazy private val logger: Logger = LoggerFactory.getLogger(PropertiesUtils.getClass)
  @transient private val prop = new Properties()

  /**
    * 读取配置嗯健
    *
    * @param fileName 配置文件名称
    * @return 返回Properties 对象
    */
  def read(fileName: String): Properties = {

      val inputStream = PropertiesUtils.getClass.getClassLoader.getResourceAsStream(fileName)

    if (null == inputStream) {
      logger.error(fileName + "文件不存在，请及时检查！！")
      throw new RuntimeException(fileName + "文件不存在，请及时检查！！")
    }
      prop.load(inputStream)
      prop

  }

}
