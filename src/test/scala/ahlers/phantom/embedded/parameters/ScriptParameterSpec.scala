package ahlers.phantom.embedded.parameters

import java.io.File

import ahlers.phantom.embedded.{IPhantomConfig, IPhantomScript, PhantomScriptBuilder}
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.collection.convert.WrapAsJava._
import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ScriptParameterSpec
  extends ParameterSpec[Option[IPhantomScript]] {

  override def parameter: IParameter = ScriptParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[IPhantomScript], List[String])]] = {
    case _ =>
      val source = new File("source.js")
      val arguments = List("source-one", "source-two")

      val builder = new PhantomScriptBuilder().source(source)

      Some(builder.build()) -> List(source.getAbsolutePath) ::
        Some(builder.arguments(arguments).build()) -> (source.getAbsolutePath +: arguments) ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, script: Option[IPhantomScript]): IPhantomConfig = {
    val config = mock[IPhantomConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.script _).expects().returns(script.asJava).anyNumberOfTimes()

    config
  }

}
