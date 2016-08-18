package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class IgnoreSSLErrorsParameterSpec
  extends ParameterSpec[Option[Boolean]] {

  override def parameter: IParameter = IgnoreSSLErrorsParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[Boolean], List[String])]] = {
    case _ =>
      Some(true) -> List("--ignore-ssl-errors=true") ::
        Some(false) -> List("--ignore-ssl-errors=false") ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, value: Option[Boolean]): IPhantomProcessConfig = {
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.diskCache _).expects().returns(value.map(Boolean.box).asJava).anyNumberOfTimes()

    config
  }

}
