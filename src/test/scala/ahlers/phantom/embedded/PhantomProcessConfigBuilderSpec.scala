package ahlers.phantom.embedded

import com.google.common.base.Optional
import org.scalamock.scalatest.MockFactory
import org.scalatest._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomProcessConfigBuilderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "assign defaults" in {
    val actual =
      new PhantomProcessConfigBuilder()
        .defaults()
        .build()

    actual.version() should be(PhantomVersion.LATEST)

    actual.debug() should be(Optional.absent[Boolean])
    actual.script() should be(Optional.absent[IPhantomScript])
  }

  it must "preserve all values" in {
    val version = PhantomVersion.V211

    val debug = true
    val script = mock[IPhantomScript]

    val actual =
      new PhantomProcessConfigBuilder()
        .version(version)
        .debug(debug)
        .script(script)
        .build()

    actual.version() should be(version)

    actual.debug().get should equal(debug)
    actual.script().get should be(script)
  }

}
