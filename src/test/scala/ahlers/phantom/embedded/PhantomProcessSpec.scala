package ahlers.phantom.embedded

import java.io.File

import com.google.common.base.Optional
import com.google.common.collect.ImmutableList
import de.flapdoodle.embed.process.extract.IExtractedFileSet
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

import scala.collection.convert.WrapAsJava._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomProcessSpec
  extends FlatSpec
          with PrivateMethodTester
          with Matchers
          with MockFactory {

  it must "generate command lines" in {
    val executable = new File("executable")
    val executableArguments = List("executable-one", "executable-two")

    val files = mock[IExtractedFileSet]
    (files.executable _).expects().returns(executable).anyNumberOfTimes()

    val source = new File("source")
    val sourceArguments = List("source-one", "source-two")

    val script = mock[IPhantomScript]
    (script.source _).expects().returns(source)
    (script.arguments _).expects().returns(ImmutableList.copyOf(sourceArguments.toIterable))

    val config = mock[IPhantomConfig]
    (config.script _).expects().returns(Optional.of(script))

    val formatter = mock[IPhantomCommandFormatter]
    (formatter.executable _).expects(files).returns(files.executable.getAbsolutePath)
    (formatter.arguments _).expects(config).returns(ImmutableList.copyOf(executableArguments.toIterable))
    (formatter.scripts _).expects(Optional.of(script)).returns(ImmutableList.of(script.source.getAbsolutePath))

    PhantomProcess.getCommandLine(formatter, config, files) should contain theSameElementsInOrderAs {
      executable.getAbsolutePath +:
        executableArguments :+
        source.getAbsolutePath +:
          sourceArguments
    }
  }

}
