package ahlers.phantom.embedded.proxies

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ProxyCredentialBuilderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "preserve all values" in {
    val user = "user"
    val secret = "secret"

    val actual =
      new ProxyCredentialBuilder()
        .username(user)
        .password(secret)
        .build()

    actual.username should be(user)
    actual.password should be(secret)
  }

}
