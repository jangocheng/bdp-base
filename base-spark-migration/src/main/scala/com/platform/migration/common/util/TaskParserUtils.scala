package com.platform.migration.common.util

import java.io.{FileInputStream, InputStream}

import com.alibaba.fastjson.JSON
import com.platform.migration.entity.TaskSchema
import org.slf4j.{Logger, LoggerFactory}

import scala.io.Source

object TaskParserUtils {
  @transient lazy private val logger: Logger = LoggerFactory.getLogger(TaskParserUtils.getClass)

  def fileToString(file: String): String = {
    try {
      val ins: InputStream = new FileInputStream(file)

      import java.nio.charset.{Charset, CodingErrorAction}

      //解决中文乱码
      val decoder = Charset.forName("UTF-8").newDecoder
      decoder.onMalformedInput(CodingErrorAction.IGNORE)

      val bufferedSource = Source.fromInputStream(ins)(decoder)
      val content: StringBuilder = new StringBuilder()

      bufferedSource.getLines().foreach(line => content.append(line.trim))

      content.toString()

    } catch {
      case ex: Exception =>
        logger.error(s"path:$file,解析task配置文件失败:$ex")
        throw new RuntimeException(s"path:$file,解析task配置文件失败:$ex")
    }
  }

  def fileToTaskSchema(file: String): TaskSchema = {
    val data = TaskParserUtils.fileToString(file)
    JSON.parseObject(data, classOf[TaskSchema])
  }

}
