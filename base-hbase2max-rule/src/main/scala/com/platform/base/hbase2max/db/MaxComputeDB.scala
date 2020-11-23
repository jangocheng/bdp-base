package com.platform.hbase2max.db

import com.alibaba.fastjson.JSONObject
import com.aliyun.odps.TableSchema
import com.aliyun.odps.data.Record
import com.platform.hbase2max.common.conf.Hbase2MaxConf
import org.apache.spark.SparkContext
import org.apache.spark.aliyun.odps.OdpsOps
import org.apache.spark.rdd.RDD


case class MaxComputeDB() {
  def saveData(sc: SparkContext, rdd: RDD[JSONObject], tableName: String, pt: String): Unit = {
    //odps连接对象
    val odpsOps = new OdpsOps(sc, Hbase2MaxConf.MAX_COMPUTE_ACCESS_ID, Hbase2MaxConf.MAX_COMPUTE_ACCESS_KEY,
      Hbase2MaxConf.MAX_COMPUTE_END_POINT, Hbase2MaxConf.MAX_COMPUTE_TUNNEL_URL)
    //rdd转换成record方法
    val write = (kv: JSONObject, record: Record, schema: TableSchema) => {
      kv.keySet().toArray().foreach(k => {
        record.set(k.toString.toLowerCase(), kv.getString(k.toString))
      })
    }
    //保存数据
    odpsOps.saveToTable(Hbase2MaxConf.MAX_COMPUTE_PROJECT_NAME, tableName, "pt=" + pt, rdd, write, defaultCreate = true)

  }


}
