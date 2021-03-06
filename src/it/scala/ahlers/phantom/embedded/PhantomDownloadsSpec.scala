package ahlers.phantom.embedded

import ahlers.phantom.embedded.MockArtifactStores.newArtifactStore
import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import de.flapdoodle.embed.process.distribution.Platform._
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

  for (version <- PhantomVersion.values; platform <- Platform.values; bitsize <- BitSize.values) {
    val distribution = new Distribution(version, platform, bitsize)
    val artifactStore = newArtifactStore()

    s"""Distribution "$distribution"""" must {
      platform match {

        /** Supported platforms. */
        case Linux | OS_X | Windows =>
          s"""download and verify artifacts""" taggedAs(Slow, Network, Disk) in {
            artifactStore.checkDistribution(distribution) should be(true)
          }

        case _ =>
          s"not download artifacts" in {
            an[Exception] should be thrownBy artifactStore.checkDistribution(distribution)
          }

      }

    }

  }

}
