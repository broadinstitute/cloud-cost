lazy val root = (project in file("."))
  .settings(
    organization := "org.broadinstitute",
    name := "ccm",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Dependencies.rootDependencies,
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  )

// Note that this task has to be scoped globally
bloopAggregateSourceDependencies in Global := true