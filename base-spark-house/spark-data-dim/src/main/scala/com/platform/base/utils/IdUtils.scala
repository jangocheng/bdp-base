package com.platform.base.utils

import java.util.UUID

object IdUtils{

  def createUUID() = {
    val uuid = UUID.randomUUID()
    uuid.toString().replace("-","")
  }
}
