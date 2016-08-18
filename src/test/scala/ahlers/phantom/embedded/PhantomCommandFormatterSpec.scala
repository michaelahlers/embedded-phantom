package ahlers.phantom.embedded

import java.io.File

import ahlers.phantom.embedded.parameters.IParameter
import com.google.common.collect.ImmutableList
import de.flapdoodle.embed.process.extract.IExtractedFileSet
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomCommandFormatterSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "format files and config given argument definitions" in {
    val executable = new File("executable")

    val files = mock[IExtractedFileSet]
    (files.executable _).expects().returns(executable)

    val config = mock[IPhantomProcessConfig]

    val applicable = mock[IParameter]
    (applicable.format _).expects(config).returns(ImmutableList.of("applies"))

    val inapplicable = mock[IParameter]
    (inapplicable.format _).expects(*).returns(ImmutableList.of())

    val arguments = ImmutableList.of(applicable, inapplicable)

    PhantomCommandFormatter.format(arguments, files, config) should contain theSameElementsInOrderAs {
      executable.getAbsolutePath ::
        "applies" ::
        Nil
    }
  }

}
