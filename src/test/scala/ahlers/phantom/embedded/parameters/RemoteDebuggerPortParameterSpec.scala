package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class RemoteDebuggerPortParameterSpec
  extends ParameterSpec[Option[Int]] {

  override def parameter: IParameter = RemoteDebuggerPortParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[Int], List[String])]] = {
    case _ =>
      Some(0) -> List("--remote-debugger-port=0") ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, value: Option[Int]): IPhantomProcessConfig = {
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.remoteDebuggerPort _).expects().returns(value.map(Int.box).asJava).anyNumberOfTimes()

    config
  }

}
