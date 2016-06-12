import sbt.Keys._
import sbt._

object BuildSettings {

  val buildOrganization = "com.bob"
  val buildVersion = "1.0"
  val buildScalaVersion = "2.11.6"

  val buildSettings = Defaults.defaultConfigs ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:reflectiveCalls",
      "-language:higherKinds",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-Xlint",
      "-Xfatal-warnings",
      "-encoding", "utf8",
      "-Yno-adapted-args"
    )
  )
}

object Dependencies {

  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % "2.3.5"

  val mysql = "mysql" % "mysql-connector-java" % "5.1.39"

  val sixtwoencrpy = ("com.enniu" % "enniu-security-service" % "1.1.2")
    .exclude("ch.qos.logback", "logback-core")
    .exclude("ch.qos.logback", "logback-classic")

  val jedis = "redis.clients" % "jedis" % "2.8.1"
}


object ProjectBuild extends Build {

  import Dependencies._
  import BuildSettings._

  val utilDeps = Seq(scalikejdbc, mysql)

  val sixtwoDeps = Seq(sixtwoencrpy, jedis)

  lazy val util = Project("util", file("util"), settings = buildSettings)
    .settings(libraryDependencies ++= utilDeps)

  lazy val sixtwocompany = Project("sixtwocompany", file("sixtwocompany"), settings = buildSettings)
    .settings(libraryDependencies ++= sixtwoDeps)
    .dependsOn(util)

  lazy val root = (project in file("."))
    .settings(buildSettings: _*)
    .aggregate(sixtwocompany, util)
    .dependsOn(sixtwocompany, util)
}
