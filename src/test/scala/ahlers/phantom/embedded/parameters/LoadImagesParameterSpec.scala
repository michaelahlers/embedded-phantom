package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.Distribution
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class LoadImagesParameterSpec
  extends ParameterSpec[Option[Boolean]] {

  override def parameter: IParameter = LoadImagesParameter.getInstance

  override def formats: PartialFunction[Distribution, List[(Option[Boolean], List[String])]] = {
    case _ =>
      Some(true) -> List("--load-images=true") ::
        Some(false) -> List("--load-images=false") ::
        None -> List.empty ::
        Nil
  }

  override def config(distribution: Distribution, value: Option[Boolean]): IPhantomProcessConfig = {
    import distribution.{getVersion => version}
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.loadImages _).expects().returns(value.map(Boolean.box).asJava).anyNumberOfTimes()

    config
  }

}
