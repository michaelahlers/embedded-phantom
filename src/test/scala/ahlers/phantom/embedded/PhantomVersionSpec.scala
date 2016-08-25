package ahlers.phantom.embedded

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomVersionSpec
  extends FlatSpec
          with Matchers {

  PhantomVersion.values foreach { version =>
    import version.{getLabel => label}

    it must s"""resolve label "$label" to version "$version"""" in {
      PhantomVersion.byLabel(label) should be(version)
    }
  }

  it must "fail on invalid labels" in {
    an[IllegalArgumentException] should be thrownBy PhantomVersion.byLabel("invalid")
  }

}
