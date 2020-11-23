package com.platform.streaming.config

import com.redis.RedisClientPool

/**
  *
  * author: wlhbdp
  *  19:16
  */
object RedisConfig {

  val clients = new RedisClientPool(host=AppConfig.REDIS_URL, port=AppConfig.REDIS_PORT,
    maxIdle = AppConfig.REDIS_MAXIDLE, secret = Option.apply(AppConfig.REDIS_SECRET), database = AppConfig.REDIS_DB,
    timeout = AppConfig.REDIS_TIMEOUT, maxConnections = AppConfig.REDIS_MAX_CONNECTIONS,
    whenExhaustedBehavior = AppConfig.REDIS_EXHAUSTED_BEHAVIOUR.toByte)


}
