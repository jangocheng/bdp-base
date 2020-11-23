package com.platform.realtimereport.persistence.dal

import com.platform.streaming.config.RedisConfig

/**
  *
  * author: wlhbdp
  */
object RedisOperationX {
  private val clients = RedisConfig.clients

  /**
    * 给指定key的字段对应的值增加value，若不存在则创建该hash
    * @param key key
    * @param field 字段名称
    * @param value 要增加的值
    */
  def hmIncrementByOrCreate(key: Any, field: Any, value: Long): Unit = {
    clients.withClient(client => {
      val exist = client.hexists(key, field)
      if (!exist) {
        client.hset(key, field, value)
      } else {
        client.hincrby(key, field, value)
      }
    })
  }

  /**
    * 给指定key的字段对应的值增加float value，若不存在则创建该hash
    * @param key key
    * @param field 字段名称
    * @param value 要增加的float值
    */
  def hmIncrementByFloatOrCreate(key: Any, field: Any, value: Float): Unit = {
      clients.withClient(client => {
        val exist = client.hexists(key, field)
        if (!exist) {
          client.hset(key, field, value)
        } else {
          client.hincrbyfloat(key, field, value)
        }
      })
  }

  /**
    * 删除hm field
    * @param key hm key
    * @param field hm field
    */
  def delHmField(key: String, field: String): Unit = {
    clients.withClient(client => {
      val exist = client.hexists(key,field)
      if (exist){
        client.hdel(key,field)
      }
    })
  }

  /**
    * 删除key
    * @param key key
    */
  def delKey(key:Any): Unit ={
    clients.withClient(client => {
      val exist = client.exists(key)
      if (exist){
        client.del(key)
      }
    })
  }

  /**
    * 给指定key的字段对应的值增加int value，若不存在则创建该hash
    * @param key key
    * @param field 字段名称
    * @param value 要增加的int值
    */
  def hmIncrementByIntOrCreate(key: Any, field: Any, value: Int): Unit = {
    clients.withClient(client => {
      val exist = client.hexists(key, field)
      if (!exist) {
        client.hset(key, field, value)
      } else {
        client.hincrby(key, field, value)
      }
    })
  }

  def listLpushCreate(key: Any, location: String): Unit = {
    clients.withClient(client => {
        client.lpush(key, location)
    })
  }

  def listLpopDel(key: Any, location: String): Unit = {
    clients.withClient(client => {
      val exist = client.exists(key)
      if (exist) {
        client.lpop(key, location)
      }
    })
  }

}
