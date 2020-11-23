package com.platform.streaming.dal

import com.platform.streaming.config.RedisConfig


object RedisOperation {

  private val clients = RedisConfig.clients

  // lpush
  def lp(messages: List[String], queueName:String="list-l") : Option[Long] = {
    clients.withClient {
      client => {
        messages.foreach(client.lpush(queueName, _))
        client.llen(queueName)
      }
    }
  }

  // rpush
  def rp(messages: List[String], queueName:String="list-r"): Option[Long] = {
    clients.withClient {
      client => {
        messages.foreach(client.rpush(queueName, _))
        client.llen(queueName)
      }
    }
  }

  def set(key: String, value: Any): Unit = {
    clients.withClient {
      client => {
        client.set(key, value)
      }
    }
  }

  // set
  def set(key: String, messages: List[String]): Some[Int] = {
    clients.withClient {
      client => {
        var index = 0
        messages.foreach { v =>
          client.set("%s-%d".format(key,index), v)
          index += 1
        }
        Some(index)
      }
    }
  }

  def hmset(key: String, map: Map[Any, Any]): Unit = {
    clients.withClient{
      client => {
        client.hmset(key, map)
      }
    }
  }

  def keyExists(key: Any): Boolean = {
    var ret = false
    clients.withClient({
      client => {
        ret = client.exists(key)
      }
    })
    ret
  }

  def hkeyExists(key: Any, field: Any): Boolean = {
    var ret = false
    clients.withClient({
      client => {
        ret = client.hexists(key, field)
      }
    })
    ret
  }
}
