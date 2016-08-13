val versionScalaTest = "(,2.3["

/** Compile and runtime dependencies. */
libraryDependencies ++=
  "de.flapdoodle.embed" % "de.flapdoodle.embed.process" % "1.50.2" ::
    Nil

/** Test dependencies. */
libraryDependencies ++=
  "org.scalamock" %% "scalamock-scalatest-support" % "(,3.3[" % Test ::
    "org.scalatest" %% "scalatest" % versionScalaTest % Test ::
    Nil


pomPostProcess := {
  import scala.xml._
  import scala.xml.transform.{RewriteRule, RuleTransformer}

  (node: Node) =>
    new RuleTransformer(new RewriteRule {
      override def transform(node: Node): NodeSeq = node match {

        /** See http://stackoverflow.com/a/27853219/700420 for source. There's no clear SBT config. allowing for a Java-only artifact sans Scala and test libraries. As an alternative, simply remove from resulting POM any dependencies not relevant to clients. */
        case dependencyEl: Elem
          if dependencyEl.label == "dependency" && dependencyEl.child.exists(_.text.contains("scala")) =>

          val organization = (dependencyEl \ "groupId").text
          val artifact = (dependencyEl \ "artifactId").text
          val version = (dependencyEl \ "version").text

          Comment(s"Provided dependency $organization#$artifact;$version has been omitted.")

        case _ => node

      }
    }).transform(node).head
}
