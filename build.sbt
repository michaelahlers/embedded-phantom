lazy val EmbeddedPhantom =
  (project in file("."))
    .settings(
      //itJacoco.settings,
      jacoco.settings
    )
