package ahlers.phantom.embedded

import java.io.File

import com.google.common.collect.ImmutableList
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.config.{IRuntimeConfig, ISupportConfig}
import de.flapdoodle.embed.process.distribution.Platform._
import de.flapdoodle.embed.process.distribution.{Distribution, IVersion}
import de.flapdoodle.embed.process.extract.IExtractedFileSet
import de.flapdoodle.embed.process.io.{IStreamProcessor, Processors}
import de.flapdoodle.embed.process.runtime.ICommandLinePostProcessor
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

import scala.collection.convert.WrapAsJava._
import scala.collection.mutable
import scala.concurrent.Promise

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomProcessSpec
  extends FlatSpec
          with PrivateMethodTester
          with Matchers
          with ScalaFutures
          with MockFactory {

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  it must "provide standard input" in {

    val distribution = Distribution.detectFor(mock[IVersion])

    val (executable, arguments: List[String]) =
      distribution.getPlatform match {
        case Windows =>
          new File("cmd") -> List("/c", "more")
        case _ => new File("cat") -> Nil
      }


    val commandFormatter = mock[IPhantomCommandFormatter]
    (commandFormatter.format _).expects(*, *).returns(ImmutableList.copyOf(Iterable(executable.getPath) ++ arguments)).atLeastOnce()

    val config = mock[IPhantomConfig]
    (config.formatter _).expects().returns(commandFormatter).atLeastOnce()
    (config.debug _).expects().never()
    (config.script _).expects().never()
    (config.supportConfig _).expects().returns(mock[ISupportConfig]).atLeastOnce()

    val expected = (0 until 10) map { i => s"message $i" }

    val actual: Promise[List[String]] = Promise()

    val processOutput = new ProcessOutput(
      new IStreamProcessor {

        val blocks: mutable.Buffer[String] = mutable.Buffer.empty

        /** Inefficient, but it's only a test. */
        override def process(block: String): Unit = {
          /* Cross-platform line terminator removal.*/
          blocks += block.replaceAll("\r\n?|\n", "")
          val tokens = blocks.mkString.split(";").map(_.trim).filter(_.nonEmpty).toList
          if (expected.lastOption == tokens.lastOption) actual.success(tokens)
        }

        override def onProcessed(): Unit = ()

      },
      Processors.silent,
      Processors.silent
    )

    val runtimeConfig = mock[IRuntimeConfig]
    (runtimeConfig.isDaemonProcess _).expects().returns(false).atLeastOnce()
    (runtimeConfig.getProcessOutput _).expects().returns(processOutput).atLeastOnce()
    (runtimeConfig.getCommandLinePostProcessor _).expects().returns(new ICommandLinePostProcessor.Noop).atLeastOnce()

    val files = mock[IExtractedFileSet]
    (files.executable _).expects().returns(executable).atLeastOnce()

    val process =
      new PhantomProcess(
        distribution,
        config,
        runtimeConfig,
        new PhantomExecutable(
          distribution,
          config,
          runtimeConfig,
          files
        )
      )

    val input = process.getStandardInput

    expected.mkString(";").grouped(15) foreach { message =>
      input.println(message)
      input.flush()
    }

    actual.future.futureValue should contain theSameElementsInOrderAs expected

    process.stop()
  }

}
