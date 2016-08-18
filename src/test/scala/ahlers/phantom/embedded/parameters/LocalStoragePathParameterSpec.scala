package ahlers.phantom.embedded.parameters

import java.io.File

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class LocalStoragePathParameterSpec
  extends ParameterSpec[Option[File]] {

  override def parameter: IParameter = LocalStoragePathParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[File], List[String])]] = {
    case _ =>
      val file = new File("")

      Some(file) -> List(s"--local-storage-path=${file.getAbsolutePath}") ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, value: Option[File]): IPhantomProcessConfig = {
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.localStoragePath _).expects().returns(value.asJava).anyNumberOfTimes()

    config
  }

}
