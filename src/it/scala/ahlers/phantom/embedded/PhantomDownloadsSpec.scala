package ahlers.phantom.embedded

import ahlers.phantom.embedded.MockArtifactStores.artifactStore
import de.flapdoodle.embed.process.distribution.BitSize._
import de.flapdoodle.embed.process.distribution.Distribution
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


  //for (version <- PhantomVersion.values; platform <- Platform.values; bitsize <- BitSize.values) {
  for (version <- PhantomVersion.values; platform <- List(Linux /*, OS_X*/); bitsize <- List(B64)) {
    val distribution = new Distribution(version, platform, bitsize)

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
