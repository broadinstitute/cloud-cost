import sbt._

object Dependencies {
  val Http4sVersion = "0.18.21"
  val Specs2Version = "4.1.0"
  val LogbackVersion = "1.2.3"
  
  val rootDependencies = List(
    "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
    "org.http4s"      %% "http4s-circe"        % Http4sVersion,
    "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
    "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
    "ch.qos.logback"  %  "logback-classic"     % LogbackVersion
  )
}
