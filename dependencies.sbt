val versionScalaTest = "(,2.3["

/**
 * Compile and runtime dependencies.
 *
 * - Includes Google's JSR-305 library as a workaround for [https://issues.scala-lang.org/browse/SI-8978 SI-8978].
 */
libraryDependencies ++=
  "ch.qos.logback" % "logback-classic" % "1.1.7" ::
    "com.google.auto.value" % "auto-value" % "1.2" ::
    "com.google.guava" % "guava" % "19.0" ::
    "com.google.code.findbugs" % "jsr305" % "(,3.1[" ::
    "commons-codec" % "commons-codec" % "1.10" ::
    "de.flapdoodle.embed" % "de.flapdoodle.embed.process" % "1.50.2" ::
    "org.apache.commons" % "commons-lang3" % "3.4" ::
    Nil

/**
 * Unit test dependencies.
 */
libraryDependencies ++=
  "nl.jqno.equalsverifier" % "equalsverifier" % "2.1.5" % Test ::
    "org.scalamock" %% "scalamock-scalatest-support" % "(,3.3[" % Test ::
    "org.scalatest" %% "scalatest" % versionScalaTest % Test ::
    "org.feijoas" %% "mango" % "0.12" % Test ::
    Nil

/**
 * Integration test dependencies.
 */
libraryDependencies ++=
  "com.novocode" % "junit-interface" % "0.11" % IntegrationTest ::
    "junit" % "junit" % "4.12" % IntegrationTest ::
    "org.hamcrest" % "hamcrest-core" % "1.3" % IntegrationTest ::
    "org.scalatest" %% "scalatest" % versionScalaTest % IntegrationTest ::
    Nil


pomPostProcess := {
  import scala.xml._
  import scala.xml.transform.{RewriteRule, RuleTransformer}

  (node: Node) =>
    new RuleTransformer(new RewriteRule {
      override def transform(node: Node): NodeSeq = node match {

        /** See http://stackoverflow.com/a/27853219/700420 for source. There's no clear SBT config. allowing for a Java-only artifact sans Scala and test libraries. As an alternative, simply remove from resulting POM any dependencies not relevant to clients. */
        case dependencyEl: Elem
          if dependencyEl.label == "dependency" &&
            (dependencyEl \ "scope").text.contains("test") ||
            (dependencyEl \ "artifactId").text.contains("scala") =>

          val organization = (dependencyEl \ "groupId").text
          val artifact = (dependencyEl \ "artifactId").text
          val version = (dependencyEl \ "version").text

          Comment(s"Provided dependency $organization:$artifact:$version has been omitted.")

        case _ => node

      }
    }).transform(node).head
}
