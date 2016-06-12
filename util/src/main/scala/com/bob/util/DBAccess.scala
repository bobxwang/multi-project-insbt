package com.bob.util

import scalikejdbc.{ReadOnlyAutoSession, DB}

/**
 * Created by bob on 16/6/12.
 */
trait DBAccess {

  Class.forName("com.mysql.jdbc.Driver")

  def describe(table: String) = println(DB.describe(table))

  def tables() = println(DB.showTables())

  implicit val session = ReadOnlyAutoSession

  def run(executeSql: String): Option[List[Map[String, Any]]]
}