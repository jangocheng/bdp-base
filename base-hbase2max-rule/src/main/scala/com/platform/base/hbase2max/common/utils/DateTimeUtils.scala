package com.platform.hbase2max.common.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, LocalTime, ZoneOffset}

import scala.collection.mutable

object DateTimeUtils {
  def getDayRange: mutable.HashMap[String, String] = {
    val map = new mutable.HashMap[String, String]()
    val date = LocalDate.now()
    val zeroHourTime = LocalTime.of(0, 0, 0, 0)
    val startDateTime = LocalDateTime.of(date.minusDays(1), zeroHourTime)
    val endDateTime = LocalDateTime.of(date, zeroHourTime)
    map.put("start", startDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli().toString)
    map.put("end", endDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli().toString)
    map
  }


  def getDayRange(time: LocalDate): mutable.HashMap[String, String] = {
    val map = new mutable.HashMap[String, String]()
    val zeroHourTime = LocalTime.of(0, 0, 0, 0)
    val startDateTime = LocalDateTime.of(time.minusDays(1), zeroHourTime)
    val endDateTime = LocalDateTime.of(time, zeroHourTime)
    map.put("start", startDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli.toString)
    map.put("end", endDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli().toString)
    map
  }

  def getYesterdayBizDate: String = {
    val date = LocalDate.now()
    DateTimeFormatter.ofPattern("yyyyMMdd").format(date.minusDays(1))
  }

  def getYesterday: LocalDate = {
    val date = LocalDate.now()
    date.minusDays(1)
  }

  def getBizDate(localDate: LocalDate): String = {
    DateTimeFormatter.ofPattern("yyyyMMdd").format(localDate)
  }


  def main(args: Array[String]): Unit = {
    System.out.println(getDayRange(getYesterday))
  }


}
