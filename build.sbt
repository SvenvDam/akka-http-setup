name := "akka-http-setup"

version := "0.1"

scalaVersion := "2.13.4"

val akkaVersion = "2.6.12"
val akkaHttpVersion = "10.2.3"
val logbackVersion = "1.2.3"
val scalaLoggingVersion = "3.9.2"
val configVersion = "1.4.1"
val pureconfigVersion = "0.14.0"
val scalatestVersion = "3.2.3"

libraryDependencies ++= Seq(
  // akka deps
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"       % akkaVersion,
  "com.typesafe.akka" %% "akka-http"        % akkaHttpVersion,
  // logging deps
  "ch.qos.logback"             % "logback-classic" % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging"  % scalaLoggingVersion,
  // config deps
  "com.typesafe"          % "config"      % configVersion,
  "com.github.pureconfig" %% "pureconfig" % pureconfigVersion
) ++ Seq(
  // test deps
  "org.scalatest"     %% "scalatest"                % scalatestVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion
).map(_ % Test)

assembly / assemblyJarName := "server.jar"

assembly / assemblyMergeStrategy := {
  case PathList("reference.conf")    => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _                             => MergeStrategy.first
}

scalacOptions += "-language:postfixOps"