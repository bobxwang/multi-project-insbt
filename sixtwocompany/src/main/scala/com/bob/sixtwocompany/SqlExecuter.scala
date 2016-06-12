package com.bob.sixtwocompany

import com.bob.util.DBAccess

case class SqlExecuter(sql: String) {

  def srun()(implicit dataAccess: DBAccess) = {
    dataAccess.run(sql)
  }
}

object SqlExecuter {

  implicit def stringToSqlExecuter(sql: String) = SqlExecuter(sql)
}