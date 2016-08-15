package ahlers.phantom.embedded

import org.scalamock.scalatest.MockFactory
import org.scalatest._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomConfigSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "preserve all values from builder" in {
    val version = PhantomVersion.LATEST
    val debug = true
    val script = mock[IPhantomScript]

    val actual =
      new PhantomConfigBuilder()
        .version(version)
        .debug(debug)
        .script(script)
        .build()

    actual.version() should be(version)
    actual.debug().get should equal(debug)
    actual.script().get should be(script)
  }

}
