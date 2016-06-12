package com.bob.sixtwocompany

import com.bob.util.DBAccess
import com.enniu.security.encrypt.EncryptService
import scalikejdbc.{SQLInterpolationString, ConnectionPool, WrappedResultSet}
import scalikejdbc.StringSQLRunner._

class XXCertDataAccess(env: Boolean) extends DBAccess {

  if (env) {
    val xxun = "usercert"
    val xxpd = "KPUA0YkVNxTLhGVM"
    val xxul = "jdbc:mysql://m.usercert.51.nb:3310/usercert?useUnicode=true&characterEncoding=UTF-8"
    ConnectionPool.singleton(xxul, xxun, xxpd)
  } else {
    val xxun = "root"
    val xxpd = "uc123"
    val xxul = "jdbc:mysql://10.0.40.140:3306/usercert?useUnicode=true&characterEncoding=UTF-8"
    ConnectionPool.singleton(xxul, xxun, xxpd)
  }

  implicit val encrypt = if (env) EncryptUtil.EncryptServicePro else EncryptUtil.EncryptServiceTest

  def toMap(rs: WrappedResultSet)(implicit edservice: EncryptService): Map[String, Any] = {
    (1 to rs.metaData.getColumnCount).foldLeft(Map[String, Any]()) { (result, i) =>
      val label = rs.metaData.getColumnLabel(i)
      Some(rs.any(label)).map { nullableValue => {
        val templable = label.toUpperCase()
        if (templable == "NAME" || templable == "MOBILE"
          || templable == "IDCARD" || templable == "CARD"
          || templable.contains("NUM") || templable.contains("ALIAS")
        ) {
          result + (label -> edservice.aesDecrypt(nullableValue.toString))
        } else {
          result + (label -> nullableValue)
        }
      }
      }.getOrElse(result)
    }
  }

  override def run(executeSql: String): Option[List[Map[String, Any]]] = {
    val sqlTrim = executeSql.trim.toUpperCase
    if (sqlTrim.startsWith("SELECT")) {
      val results: List[Map[String, Any]] = new SQLInterpolationString(StringContext(executeSql)).sql()
        .map(x => toMap(x)).list().apply()
      Some(results)
    } else {
      println("just support select operation")
      val b = true
      if (!b) {
        executeSql.run
      }
      None
    }
  }
}

/**
 * Created by bob on 16/6/12.
 */
object XXCertDataAccess {

  def apply(env: Boolean) = {
    new XXCertDataAccess(env)
  }
}