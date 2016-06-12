assemblyJarName in assembly := "root.jar"

mainClass in assembly := Some("com.bob.multiproject.ProApp")

assemblyOption in assembly := (assemblyOption in assembly)
  .value.copy(includeScala = true)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

libraryDependencies ~= {
  _ map {
    case m if m.organization == "com.typesafe.play" =>
      m.exclude("commons-logging", "commons-logging").
        exclude("com.typesafe.play", "sbt-link")
    case m => m
  }
}