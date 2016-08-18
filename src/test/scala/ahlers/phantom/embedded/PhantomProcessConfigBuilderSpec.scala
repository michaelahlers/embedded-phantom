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
      new PhantomConfigBuilder()
        .defaults()
        .build()

    actual.version() should be(PhantomVersion.LATEST)
    actual.formatter() should be(PhantomCommandFormatter.getInstance)
    actual.debug() should be(Optional.absent[Boolean])
    actual.script() should be(Optional.absent[IPhantomScript])
  }

  it must "preserve all values" in {
    val version = PhantomVersion.V211

    val formatter = mock[IPhantomCommandFormatter]

    val debug = true
    val script = mock[IPhantomScript]

    val actual =
      new PhantomConfigBuilder()
        .version(version)
        .formatter(formatter)
        .debug(debug)
        .script(script)
        .build()

    actual.version() should be(version)
    actual.debug().get should equal(debug)
    actual.script().get should be(script)
  }

}
