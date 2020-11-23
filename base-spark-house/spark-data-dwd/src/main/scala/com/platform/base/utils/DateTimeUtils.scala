package com.platform.base.utils

import java.time._
import java.time.format.DateTimeFormatter
import java.util.Date

import org.joda.time.format.DateTimeParser

import scala.collection.mutable

object DateTimeUtils {

  def getYesterday: String ={
    val map = new mutable.HashMap[String,LocalDateTime]()
    val date= LocalDate.now()
    val zeroHourTime = LocalTime.of(0,0,0,0)
    val startDateTime = LocalDateTime.of(date.minusDays(1),zeroHourTime)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    startDateTime.format(formatter)
  }

  def getCurrentTime: String ={
    val startDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    startDateTime.format(formatter)
  }

}
