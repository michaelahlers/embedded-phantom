package ahlers.phantom.embedded

import ahlers.phantom.embedded.PhantomVersion._
import de.flapdoodle.embed.process.distribution.Platform._
import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomSignatureSpec
  extends FlatSpec
          with Matchers {

  val distributions =
    for {
      version <- PhantomVersion.values
      platform <- Platform.values
      bitsize <- BitSize.values
    } yield new Distribution(V211, platform, bitsize)

  distributions foreach { distribution =>

    distribution.getPlatform match {

      case Linux | OS_X | Windows =>
        it must s"""provide a signature for distribution "$distribution"""" in {
          PhantomSignature.byDistribution(distribution).distribution should be(distribution)
        }

      case _ =>
        it must s"""provide not signature for unsupported distribution "$distribution"""" in {
          an[IllegalArgumentException] should be thrownBy PhantomSignature.byDistribution(distribution)
        }

    }

  }

}
