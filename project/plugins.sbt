resolvers ++=
  ("Era7 (releases)" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com") ::
    ("JGit" at "http://download.eclipse.org/jgit/maven") ::
    Nil

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.4")

/** Covers all languages with byte code instrumentation. */
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

addSbtPlugin("ohnosequences" % "sbt-s3-resolver" % "0.14.0")

// TODO: Enable once Coveralls supports Jacoco reports.
// addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

// addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
