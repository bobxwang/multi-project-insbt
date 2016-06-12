package com.bob.multiproject

import com.bob.sixtwocompany.XXCertDataAccess
import com.bob.sixtwocompany.SqlExecuter._

/**
 * Created by bob on 16/6/8.
 */
object ProApp {

  def main(args: Array[String]): Unit = {

    implicit val xxCertDataAccess = XXCertDataAccess(false)
    val rs = "select mobile,HEX(uuid) from t_user_mobile limit 10".srun
    rs.map(x => {
      x.foreach(y => println(y))
    }).ensuring(x => {
      println("invoke done")
      true
    })
    System.exit(0)
  }
}