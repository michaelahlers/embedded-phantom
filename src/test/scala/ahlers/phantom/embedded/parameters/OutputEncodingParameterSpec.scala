package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.Distribution
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class OutputEncodingParameterSpec
  extends ParameterSpec[Option[String]] {

  override def parameter: IParameter = OutputEncodingParameter.getInstance

  override def formats: PartialFunction[Distribution, List[(Option[String], List[String])]] = {
    case _ =>
      val encoding = "encoding"

      Some(encoding) -> List(s"--output-encoding=$encoding") ::
        None -> List.empty ::
        Nil
  }

  override def config(distribution: Distribution, value: Option[String]): IPhantomProcessConfig = {
    import distribution.{getVersion => version}
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.outputEncoding _).expects().returns(value.asJava).anyNumberOfTimes()

    config
  }

}
