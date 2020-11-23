package com.platform.migration.common.util

import com.platform.migration.entity.vo.StepParamVO
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.conf.Configuration
import org.slf4j.LoggerFactory

object HbaseScanUtils {

  private val logger = LoggerFactory.getLogger(HbaseScanUtils.getClass)
  private val params: Array[String] = Array(
    "hbase.mapreduce.inputtable",
    "hbase.mapreduce.scan",
    "hbase.mapreduce.scan.row.start",
    "hbase.mapreduce.scan.row.stop",
    "hbase.mapreduce.scan.column.family",
    "hbase.mapreduce.scan.columns",
    "hbase.mapreduce.scan.timestamp",
    "hbase.mapreduce.scan.timerange.start",
    "hbase.mapreduce.scan.timerange.end",
    "hbase.mapreduce.scan.maxversions",
    "hbase.mapreduce.scan.cacheblocks",
    "hbase.mapreduce.scan.cachedrows",
    "hbase.mapreduce.scan.batchsize",
    "hbase.mapreduce.inputtable.shufflemaps"
  )

  def fillParam(hBaseConf: Configuration, stepParams: java.util.List[StepParamVO]): Unit = {
    logger.info("============Hbase 可以设置如下参数：" + params.mkString(",\n"))
    import scala.collection.JavaConversions._
    for (stepParam <- stepParams) {
      //判断参数是否有效
      if (null != stepParam && StringUtils.isNotBlank(stepParam.getParamName)
        && StringUtils.isNotBlank(stepParam.getParamValue)) {

        //判断参数是否是查询Hbase的参数
        if (params.contains(stepParam.getParamName)) {
          hBaseConf.set(stepParam.getParamName, stepParam.getParamValue)
        } else {
          throw new RuntimeException(s"Hbase 的参数必须是：" + params.mkString(",\n") + s"\n 当前不合理的参数是：$stepParam")
        }

      }

    }
  }

}
