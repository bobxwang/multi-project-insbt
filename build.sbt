assemblyJarName in assembly := "root.jar"

mainClass in assembly := Some("com.bob.multiproject.ProApp")

assemblyOption in assembly := (assemblyOption in assembly)
  .value.copy(includeScala = true)