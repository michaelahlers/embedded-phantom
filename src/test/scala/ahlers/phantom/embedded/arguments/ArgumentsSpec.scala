package ahlers.phantom.embedded.arguments

import ahlers.phantom.embedded.arguments.Arguments.usingTemplate
import com.google.common.base.Optional
import org.scalatest.{Matchers, WordSpec}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ArgumentsSpec
  extends WordSpec
          with Matchers {

  "Using template" must {
    "format present values" in {
      usingTemplate("%s", Optional.of("value")) should contain("value")
    }
    "format absent values" in {
      usingTemplate("%s", Optional.absent[String]) should be(empty)
    }
  }

}
