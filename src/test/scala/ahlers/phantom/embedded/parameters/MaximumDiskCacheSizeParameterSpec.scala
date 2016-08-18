package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.IVersion
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class MaximumDiskCacheSizeParameterSpec
  extends ParameterSpec[Option[Long]] {

  override def parameter: IParameter = MaximumDiskCacheSizeParameter.getInstance

  override def formats: PartialFunction[IVersion, List[(Option[Long], List[String])]] = {
    case _ =>
      Some(0L) -> List("--max-disk-cache-size=0") ::
        None -> List.empty ::
        Nil
  }

  override def config(version: IVersion, value: Option[Long]): IPhantomProcessConfig = {
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.maximumDiskCacheSize _).expects().returns(value.map(Long.box).asJava).anyNumberOfTimes()

    config
  }

}
