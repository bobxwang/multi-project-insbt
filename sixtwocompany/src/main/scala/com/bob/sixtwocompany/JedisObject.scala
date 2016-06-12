package com.bob.sixtwocompany

import redis.clients.jedis.{HostAndPort, JedisCluster}
import java.util

/**
 * Created by bob on 16/6/12.
 */
object JedisObject {

  def redisClient(redisnode: String): JedisCluster = {
    val jedisnode = redisnode
    val nodes: util.HashSet[HostAndPort] = new util.HashSet[HostAndPort]
    for (ipPort <- jedisnode.split(",")) {
      val ipAndPort: Array[String] = ipPort.split(":")
      var port: Integer = null
      try {
        port = Integer.valueOf(ipAndPort(1).trim)
      }
      catch {
        case e: NumberFormatException => {
          println(String.format("jedisClusterNodes节点 %s port 无效", ipPort))
        }
      }
      nodes.add(new HostAndPort(ipAndPort(0).trim, port))
    }
    new JedisCluster(nodes)
  }

  def redisClientPro(): JedisCluster = {
    redisClient("172.16.22.41:6384,172.16.22.42:6384,172.16.22.43:6384,172.16.22.44:6384,172.16.22.45:6384,172.16.22.46:6384,172.16.22.47:6384,172.16.22.48:6384")
  }

  def redisClientTest(): JedisCluster = {
    redisClient("")
  }

}
