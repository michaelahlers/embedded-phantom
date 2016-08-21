lazy val EmbeddedPhantom =
  (project in file("."))
    .enablePlugins(SiteScaladocPlugin)
    .configs(IntegrationTest)
    .settings(Defaults.itSettings: _*)
    .settings(jacoco.settings)
    .settings(ghpages.settings)

/** Slow or otherwise resource-intensive tests are tagged appropriately and their execution can be controlled by adding to `local-build.sbt` (which is ignored by Git) test arguments like the following. */
//testOptions in Test ++=
//  Tests.Argument("-l", "org.scalatest.tags.Slow") ::
//    Nil
