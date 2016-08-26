package ahlers.phantom.embedded

import ahlers.phantom.embedded.PhantomVersion._
import de.flapdoodle.embed.process.distribution.BitSize._
import de.flapdoodle.embed.process.distribution.Distribution
import de.flapdoodle.embed.process.distribution.Platform._
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomSignaturesSpec
  extends FlatSpec
          with Matchers {

  val distributions =
    for {
      version <- PhantomVersion.values
      platform <- Linux :: OS_X :: Windows :: Nil
      bitsize <- B32 :: B64 :: Nil
    } yield new Distribution(V211, platform, bitsize)

  distributions foreach { distribution =>
    it must s"""provide a signature for "$distribution"""" in {
      PhantomSignatures.byDistribution(distribution).distribution should be(distribution)
    }
  }

}
