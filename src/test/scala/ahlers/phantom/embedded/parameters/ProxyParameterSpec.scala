package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.IPhantomProcessConfig
import ahlers.phantom.embedded.proxies.ProxyType._
import ahlers.phantom.embedded.proxies._
import de.flapdoodle.embed.process.distribution.Distribution
import org.feijoas.mango.common.base.Optional._

import scala.language.implicitConversions

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ProxyParameterSpec
  extends ParameterSpec[Option[IProxy]] {

  override def parameter: IParameter = ProxyParameter.getInstance

  override def formats: PartialFunction[Distribution, List[(Option[IProxy], List[String])]] = {
    case _ =>
      def proxy(host: String, port: Option[Int], proxyType: Option[IProxyType], credential: Option[IProxyCredential]): IProxy = {
        val builder = new ProxyBuilder()

        builder.host(host)
        port.map(Int.box).foreach(builder.port)
        proxyType.foreach(builder.`type`)
        credential.foreach(builder.credential)

        builder.build()
      }

      val host = "host"
      val port = 0

      val user = "user"
      val secret = "secret"
      val credential =
        new ProxyCredentialBuilder()
          .username(user)
          .password(secret)
          .build()

      val scenarios =
        for {
          (p, pa) <- List(Some(port) -> List(s"--proxy=$host:$port"), None -> List(s"--proxy=$host"))
          (t, ta) <- List(Some(HTTP) -> List(s"--proxy-type=http"), Some(SOCKS5) -> List(s"--proxy-type=socks5"), Some(NONE) -> List(s"--proxy-type=none"), None -> Nil)
          (c, ca) <- List(Some(credential) -> List(s"--proxy-auth=$user:$secret"), None -> Nil)
        } yield Some(proxy(host, p, t, c)) -> (pa ++ ta ++ ca)

      scenarios ++ List(None -> Nil)
  }

  override def config(distribution: Distribution, value: Option[IProxy]): IPhantomProcessConfig = {
    import distribution.{getVersion => version}
    val config = mock[IPhantomProcessConfig]

    (config.version _).expects().returns(version).anyNumberOfTimes()
    (config.proxy _).expects().returns(value.asJava).anyNumberOfTimes()

    config
  }

}
