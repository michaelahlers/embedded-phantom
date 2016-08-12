val versionScalaTest = "2.2.6"

/** Compile and runtime dependencies. */
libraryDependencies ++=
  "de.flapdoodle.embed" % "de.flapdoodle.embed.process" % "(,1.51[" ::
    Nil

/** Test dependencies. */
libraryDependencies ++=
  "org.scalamock" %% "scalamock-scalatest-support" % "(,3.3[" % Test ::
    "org.scalatest" %% "scalatest" % versionScalaTest % Test ::
    Nil
