package ahlers.phantom.embedded

import java.io.{File, PrintWriter}

import ahlers.phantom.embedded.PhantomVersion._
import de.flapdoodle.embed.process.distribution.BitSize._
import de.flapdoodle.embed.process.distribution.Platform._
import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import nl.jqno.equalsverifier.EqualsVerifier
import org.apache.commons.codec.binary.Hex
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

  it should "have correct equals and hashCode" in {
    val first = PhantomSignature.byDistribution(new Distribution(V211, Linux, B32))
    val second = PhantomSignature.byDistribution(new Distribution(V211, Linux, B32))
    first should not be theSameInstanceAs(second)
    EqualsVerifier.forRelaxedEqualExamples(first, second)
  }

  it must "digest files using same algorithm" in {
    /* SHA-256 sum of sample string. */
    val expected: Array[Byte] = Hex.decodeHex("7519e1d06d0e0f235540c1a9aa2ddd0e4afa45f3c211a96332f22ae0a97d1ab9".toCharArray)

    val signature =
      PhantomSignature
        .builder()
        .distribution(new Distribution(null, null, null))
        .algorithm("SHA-256")
        .digest(expected)
        .build()

    val sample = File.createTempFile(getClass.getSimpleName, "")

    new PrintWriter(sample.getAbsolutePath) {
      println("The quick red fox jumps over the lazy brown dog.")
      close()
    }

    signature.digest(sample) should contain theSameElementsInOrderAs expected

    sample.delete()
  }

}
