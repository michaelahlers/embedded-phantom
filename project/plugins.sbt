resolvers += "Era7 (releases)" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")

addSbtPlugin("ohnosequences" % "sbt-s3-resolver" % "0.14.0")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
