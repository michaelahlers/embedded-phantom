package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.{IPhantomConfig, PhantomVersion}
import de.flapdoodle.embed.process.distribution.IVersion
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

  def formats: PartialFunction[IVersion, List[(V, List[String])]]

  def config(version: IVersion, values: V): IPhantomConfig

  "Value formatter" when {

    PhantomVersion.values foreach { version =>
      s"""handling version "$version"""" must {

        formats(version) foreach { case (values, arguments) =>
          s"""format values "$values" as arguments ${arguments.mkString("\"", "\",\"", "\"")}""" in {
            parameter.format(config(version, values)) should contain theSameElementsInOrderAs arguments
          }
        }

      }

    }
  }

}
