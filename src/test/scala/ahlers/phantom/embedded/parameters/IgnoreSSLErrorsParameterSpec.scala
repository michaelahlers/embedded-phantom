package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.Distribution
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class IgnoreSSLErrorsParameterSpec
  extends ParameterSpec[Option[Boolean]] {

  override def parameter: IParameter = IgnoreSSLErrorsParameter.getInstance

  override def formats: PartialFunction[Distribution, List[(Option[Boolean], List[String])]] = {
    case _ =>
      Some(true) -> List("--ignore-ssl-errors=true") ::
        Some(false) -> List("--ignore-ssl-errors=false") ::
        None -> List.empty ::
        Nil
  }

  override def config(distribution: Distribution, value: Option[Boolean]): IPhantomProcessConfig = {
    import distribution.{getVersion => version}
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.ignoreSSLErrors _).expects().returns(value.map(Boolean.box).asJava).anyNumberOfTimes()

    config
  }

}
