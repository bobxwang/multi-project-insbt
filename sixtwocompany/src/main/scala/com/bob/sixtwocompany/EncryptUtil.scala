package com.bob.sixtwocompany

import com.enniu.security.encrypt.{EncryptConfig, EncryptService}
import scala.collection.JavaConverters._

/**
 * Created by bob on 16/6/8.
 */
object EncryptUtil {

  def EncryptService(serviceName: String, serviceAddr: String, servicePort: Int): EncryptService = {
    val encryptConfig = new EncryptConfig()
    encryptConfig.setServiceName(serviceName)
    encryptConfig.setKeyServerAddr(serviceAddr)
    encryptConfig.setKeyServerPort(servicePort)
    val encryptService = new EncryptService(encryptConfig)
    encryptService.setEncryptVersion(2)
    val queryVersion: List[Integer] = List(0, 1, 2)
    encryptService.setQueryVersions(queryVersion.asJava)
    encryptService
  }

  def EncryptServicePro() = {
    EncryptService("risk_calc", "192.168.59.69", 8989)
  }

  def EncryptServiceTest() = {
    EncryptService("risk_calc", "192.168.2.12", 8889)
  }
}