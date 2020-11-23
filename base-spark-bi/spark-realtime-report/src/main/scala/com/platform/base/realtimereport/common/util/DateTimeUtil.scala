package com.platform.realtimereport.common.util

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils

/**
  * datetime format tool
  */
case class DateTimeUtil() {

  val DATEANDTIME_NO_SPLIT = "yyyyMMdd"
  val DATEANDTIME_SPLIT = "yyyy/MM/dd"
  val DATE_SPLIT = "yyyy/MM/dd HH:mm:ss"
  val DATE_HOUR_SPLIT = "yyyyMMddHH"

  /**
    * 参数是否为当前小时段
    * @param str 时间参数
    * @return true 当前时段 false 非当前时段
    */
  def isCurTimeSlot(str: String): Boolean = {
    if(StringUtils.isBlank(str)) {
      false
    }
    val curHour = DateUtils.getFragmentInHours(Calendar.getInstance, Calendar.DAY_OF_YEAR)
    val df = new SimpleDateFormat(DATE_SPLIT)
    val realHour = df.parse(str).getHours()
    curHour==realHour
  }

  /**
    * 获取当前日期
    * @return yyyyMMdd
    */
  def nowToString(formatStr:String): String = {
    val cal = Calendar.getInstance
    dateToString(cal, formatStr)
  }

  /**
    * 获取当前日期前n天日期
    * @return yyyyMMdd
    */
  def otherDayToString(otherDay:Int): String = {
    val cal = DateUtils.addDays(Calendar.getInstance.getTime,otherDay)
    dateToString(cal, DATEANDTIME_NO_SPLIT)
  }

  /**
    * 格式化指定时间
    * @return yyyyMMdd
    */
  def dateToString(date: Calendar, format: String): String = {
    val df = new SimpleDateFormat(format)
    df.format(date.getTime)
  }

  /**
    * 格式化指定时间
    * @return yyyyMMdd
    */
  def dateToString(date: Date, format: String): String = {
    val df = new SimpleDateFormat(format)
    df.format(date.getTime)
  }

}
