package ahlers.phantom.embedded

import de.flapdoodle.embed.process.distribution.Platform._
import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import de.flapdoodle.embed.process.io.directories.TempDirInPlatformTempDir
import de.flapdoodle.embed.process.store.MockLocalArtifactStore
import org.scalatest._
import org.scalatest.tagobjects.{Disk, Network, Slow}

/**
 * Uses a fully-transient artifact store to avoid affecting subsequent test runs.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomDownloadsSpec
  extends WordSpec
          with Matchers {

  val downloadConfig =
    new PhantomDownloadConfigBuilder()
      .defaults()
      .artifactStorePath(new TempDirInPlatformTempDir)
      .build()

  val artifactStore =
    new PhantomExtractedArtifactStoreBuilder()
      .defaults()
      .extractDir(new TempDirInPlatformTempDir)
      .download(downloadConfig)
      .build()

  for (version <- PhantomVersion.values; platform <- Platform.values; bitsize <- BitSize.values) {
    val distribution = new Distribution(version, platform, bitsize)

    s"""Distribution "$distribution"""" must {
      platform match {

        /** Supported platforms. */
        case Linux | OS_X | Windows =>
          s"""download and verify artifacts""" taggedAs(Slow, Network, Disk) in {
            artifactStore.checkDistribution(distribution) should be(true)
            MockLocalArtifactStore.getArtifact(downloadConfig, distribution)
          }

        case _ =>
          s"not download artifacts" in {
            an[Exception] should be thrownBy artifactStore.checkDistribution(distribution)
          }

      }

    }

  }

}
