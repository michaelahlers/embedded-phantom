import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider

crossPaths := false

publishMavenStyle := true

/** Test artifacts are desired (as additional examples). */
publishArtifact in Test := true

awsProfile := "default"

s3credentials :=
  new ProfileCredentialsProvider(awsProfile.value) |
    new EnvironmentVariableCredentialsProvider()

publishTo := Some(s3resolver.value("Ahlers Consulting", s3("artifacts.ahlers.consulting")))
