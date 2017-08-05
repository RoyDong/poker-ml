version := "0.1"
name := "poker-ml"
organization := "com.roydong"
scalaVersion := "2.11.8"
assemblyJarName in assembly := "poker-ml_2.11-0.1.jar"
compileOrder := CompileOrder.JavaThenScala
javacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-source", "1.8",
  "-target", "1.8"
)

PB.protocVersion := "-v:com.google.protobuf:protoc:3.0.0"
PB.pythonExe := "C:/Python27/Python.exe"
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.1.0" % "provided",
  "org.json4s" %% "json4s-native" % "3.5.1",
  "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion
)


assemblyExcludedJars in assembly := {
  val jars = Seq(
    "config-1.2.1.jar",
    "hadoop-lzo-0.4.20.jar",
    "mariadb-java-client-1.5.9.jar",
    "mchange-commons-java-0.2.11.jar",
    "mysql-connector-java-5.1.41-bin.jar",
    "scala-redis_2.11-1.0.jar"
  )
  (fullClasspath in assembly).value.filter {
    x => jars.contains(x.data.getName)
  }
}

