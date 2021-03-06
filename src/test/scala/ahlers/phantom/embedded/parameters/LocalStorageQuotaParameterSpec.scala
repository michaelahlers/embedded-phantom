package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import de.flapdoodle.embed.process.distribution.Distribution
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class LocalStorageQuotaParameterSpec
  extends ParameterSpec[Option[Long]] {

  override def parameter: IParameter = LocalStorageQuotaParameter.getInstance

  override def formats: PartialFunction[Distribution, List[(Option[Long], List[String])]] = {
    case _ =>
      Some(0L) -> List("--local-storage-quota=0") ::
        None -> List.empty ::
        Nil
  }

  override def config(distribution: Distribution, value: Option[Long]): IPhantomProcessConfig = {
    import distribution.{getVersion => version}
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.localStorageQuota _).expects().returns(value.map(Long.box).asJava).anyNumberOfTimes()

    config
  }

}
