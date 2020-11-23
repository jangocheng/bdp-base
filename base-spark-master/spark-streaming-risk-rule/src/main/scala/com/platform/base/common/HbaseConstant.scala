package com.platform.common

import org.apache.hadoop.hbase.util.Bytes

object HbaseConstant {
  /**
    * Hbase BEE考勤无效数据 FAMILY
    */
  val FAMILY_MALL_WOKE_NOT_ATTENDANCE: Array[Byte] = Bytes.toBytes("MALL_WOKE_NOT_ATTENDANCE_INFO")

  /**
    * Hbase BEE考勤无效数据
    */
  val TABLE_MALL_WOKE_NOT_ATTENDANCE = "MALL_WOKE_NOT_ATTENDANCE"

  /**
    * Hbase BEE考勤时长数据 FAMILY
    */
  val FAMILY_MALL_WOKE_ATTENDANCE_TIME: Array[Byte] = Bytes.toBytes("MALL_WOKE_ATTENDANCE_TIME_INFO")


  /**
    * Hbase BEE考勤时长数据
    */
  val TABLE_MALL_WOKE_ATTENDANCE_TIME = "MALL_WOKE_ATTENDANCE_TIME"


}
