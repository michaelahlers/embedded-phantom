package ahlers.phantom.embedded.parameters

import java.io.File

import ahlers.phantom.embedded.{IPhantomProcessConfig, IPhantomScript, PhantomScriptBuilder}
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
      def script(encoding: Option[String], language: Option[String], source: File, arguments: List[String]): IPhantomScript = {
        val builder = new PhantomScriptBuilder()

        encoding.foreach(builder.encoding)
        language.foreach(builder.language)
        builder.source(source)
        builder.arguments(arguments)

        builder.build()
      }

      val encoding = "encoding"
      val language = "language"
      val source = new File("source.js")
      val arguments = List("source-one", "source-two")

      val path: String = source.getAbsolutePath

      val scenarios =
        for {
          (e, ea) <- List(Some(encoding) -> List(s"--script-encoding=$encoding"), None -> Nil)
          (l, la) <- List(Some(language) -> List(s"--script-language=$language"), None -> Nil)
          (a, aa) <- List(arguments -> arguments, Nil -> Nil)
        } yield Some(script(e, l, source, a)) -> (ea ++ la ++ List(path) ++ aa)

      scenarios ++ List(None -> Nil)
  }

  override def config(version: IVersion, value: Option[IPhantomScript]): IPhantomProcessConfig = {
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.script _).expects().returns(value.asJava).anyNumberOfTimes()

    config
  }

}
