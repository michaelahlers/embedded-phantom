package ahlers.phantom.embedded

import java.util

import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import de.flapdoodle.embed.process.io.directories.TempDirInPlatformTempDir
import org.scalatest._
import org.scalatest.tagobjects.{Disk, Network, Slow}

import scala.collection.convert.WrapAsScala._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomDownloadsSpec
  extends FlatSpec
          with Matchers {

  /** A fully-transient artifact store. */
  val artifactStore =
  new PhantomExtractedArtifactStoreBuilder()
    .defaults()
    .extractDir(new TempDirInPlatformTempDir)
      .download({
        new PhantomDownloadConfigBuilder()
          .defaults()
          .artifactStorePath(new TempDirInPlatformTempDir)
          .build()
      })
      .build()


  val supportedPlatforms = util.EnumSet.of(Platform.Linux, Platform.OS_X, Platform.Windows)

  val supportedDistributions =
    for {
      version <- PhantomVersion.values
      platform <- supportedPlatforms
      bitsize <- BitSize.values
    } yield new Distribution(version, platform, bitsize)

  supportedDistributions foreach { distribution =>
    it must s"download PhantomJS for $distribution" taggedAs(Slow, Network, Disk) in {
      artifactStore.checkDistribution(distribution) should be(true)
    }
  }

  val unsupportedDistributions =
    for {
      version <- PhantomVersion.values
      platform <- util.EnumSet.complementOf(supportedPlatforms)
      bitsize <- BitSize.values
    } yield new Distribution(version, platform, bitsize)

  unsupportedDistributions foreach { distribution =>
    it must s"fail on $distribution" in {
      an[Exception] should be thrownBy artifactStore.checkDistribution(distribution)
    }
  }


}
