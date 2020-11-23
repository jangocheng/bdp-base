package com.platform.utils

import java.text.SimpleDateFormat


object DateTimeUtils {

  def getDateMonth(str : Long): String ={
    val fm = new SimpleDateFormat("yyyyMMdd")
    fm.format(str.toLong)
  }
}
