package ahlers.phantom.embedded.proxies

import com.google.common.base.Optional
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ProxyBuilderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "assign defaults" in {
    val host = "localhost"

    val actual =
      new ProxyBuilder()
        .defaults()
        .host(host)
        .build()

    actual.host should be(host)
    actual.port should be(Optional.absent)
    actual.`type` should be(Optional.absent)
    actual.credential should be(Optional.absent)
  }

  it must "preserve all values" in {
    val host = "localhost"
    val port = 0
    val `type` = mock[IProxyType]
    val credential = mock[IProxyCredential]

    val actual =
      new ProxyBuilder()
        .host(host)
        .port(port)
        .`type`(`type`)
        .credential(credential)
        .build()

    actual.host should be(host)
    actual.port should be(Optional.of(port))
    actual.`type` should be(Optional.of(`type`))
    actual.credential should be(Optional.of(credential))
  }

}
