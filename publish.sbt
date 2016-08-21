import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider

crossPaths := false

publishMavenStyle := true

/** Test artifacts are desired (as additional examples). See sbt/sbt#2458 for notes on publishing integration test classes. */
publishArtifact in Test := true
addArtifact(artifact in(IntegrationTest, packageBin), packageBin in IntegrationTest)

releasePublishArtifactsAction := PgpKeys.publishSigned.value

awsProfile := "default"

s3credentials :=
  new ProfileCredentialsProvider(awsProfile.value) |
    new EnvironmentVariableCredentialsProvider()

publishTo := Some(s3resolver.value("Ahlers Consulting", s3("artifacts.ahlers.consulting")))
