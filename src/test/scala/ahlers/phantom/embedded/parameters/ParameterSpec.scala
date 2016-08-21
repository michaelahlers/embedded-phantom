package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.{IPhantomProcessConfig, PhantomVersion}
import de.flapdoodle.embed.process.distribution.{BitSize, Distribution, Platform}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
trait ParameterSpec[V]
  extends WordSpec
          with Matchers
          with MockFactory {

  def parameter: IParameter

  def formats: PartialFunction[Distribution, List[(V, List[String])]]

  def config(distribution: Distribution, values: V): IPhantomProcessConfig

  "Value formatter" when {

    val distributions =
      for {
        version <- PhantomVersion.values
        platform <- Platform.values
        bitsize <- BitSize.values
      } yield new Distribution(version, platform, bitsize)

    distributions foreach { distribution =>

      s"""handling distribution "$distribution"""" must {

        formats(distribution) foreach { case (values, arguments) =>
          val processConfig = config(distribution, values)

          s"""format values "$values" as arguments ${arguments.mkString("\"", "\",\"", "\"")}""" in {
            parameter.format(distribution, config(distribution, values)) should contain theSameElementsInOrderAs arguments
          }
        }

      }

    }

  }

}
