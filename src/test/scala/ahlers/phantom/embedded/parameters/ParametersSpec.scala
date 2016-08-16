package ahlers.phantom.embedded.parameters

import ahlers.phantom.embedded.parameters.Parameters.usingTemplate
import com.google.common.base.Optional
import org.scalatest.{Matchers, WordSpec}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class ParametersSpec
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
