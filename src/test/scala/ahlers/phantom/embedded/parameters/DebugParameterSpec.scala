package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomConfig
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class DebugParameterSpec
  extends ParameterSpec[Option[Boolean]] {

  override def parameter: IParameter = DebugParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[Boolean], List[String])]] = {
    case _ =>
      Some(true) -> List("--debug=true") ::
        Some(false) -> List("--debug=false") ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, debug: Option[Boolean]): IPhantomConfig = {
    val config = mock[IPhantomConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.debug _).expects().returns(debug.map(Boolean.box).asJava).anyNumberOfTimes()

    config
  }

}
