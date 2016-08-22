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
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers, PrivateMethodTester}
import org.slf4j.LoggerFactory

import scala.collection.convert.WrapAsJava._
import scala.collection.mutable.StringBuilder
import scala.concurrent.Promise

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomProcessSpec
  extends FlatSpec
          with PrivateMethodTester
          with Matchers
          with ScalaFutures
          with MockFactory
          with GivenWhenThen {

  private val logger = LoggerFactory.getLogger(classOf[PhantomProcessSpec])

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(timeout = Span(10, Seconds), interval = Span(500, Millis))

  def withEchoCommand[T](standardOut: IStreamProcessor = Processors.silent, standardError: IStreamProcessor = Processors.silent)(block: (PhantomProcess) => T): Unit = {

    val distribution = Distribution.detectFor(mock[IVersion])

    val (executable, arguments: List[String]) =
      distribution.getPlatform match {
        case Windows =>
          new File("cmd") -> List("/c", "more")
        case _ => new File("cat") -> Nil
      }

    val commandFormatter = mock[IPhantomCommandFormatter]
    (commandFormatter.format _).expects(*, *, *).returns(ImmutableList.copyOf(Iterable(executable.getPath) ++ arguments)).atLeastOnce()

    val config = mock[IPhantomProcessConfig]
    (config.debug _).expects().never()
    (config.script _).expects().never()
    (config.supportConfig _).expects().returns(mock[ISupportConfig]).atLeastOnce()

    val processOutput = new ProcessOutput(standardOut, Processors.silent, Processors.silent)

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
          files,
          commandFormatter
        )
      )

    block(process)
  }

  it must "provide standard input" in {

    val expected = (0 until 10) map { i => s"message-number-$i." }
    val actual: Promise[List[String]] = Promise()

    val consumer = new IStreamProcessor {
      val blocks: StringBuilder = new StringBuilder

      /** Inefficient, but it's only a test. */
      override def process(block: String): Unit = {
        blocks.append(block.replaceAll("\r\n|\n", "").trim)
        val tokens = blocks.toString.split(";").map(_.trim).filter(_.nonEmpty).toList
        if (!actual.isCompleted && expected.lastOption == tokens.lastOption) actual.success(tokens)
      }

      override def onProcessed(): Unit = ()
    }

    withEchoCommand(standardOut = consumer) { process =>

      val console = process.getConsole

      expected.mkString(";").grouped(25) foreach { message =>
        console.write(message + "\n")
        console.flush()
      }

      actual.future.futureValue should contain theSameElementsInOrderAs expected

      process.stop()
    }

  }

  it must "send exit command on stop" in {

    val wasShutdown: Promise[Boolean] = Promise()
    val wasClosed: Promise[Boolean] = Promise()
    val isZombie: Promise[Boolean] = Promise()

    val consumer = new IStreamProcessor {
      override def process(block: String): Unit =
        block.replaceAll("\r\n|\n", "").trim   match {
          case "//;phantom.exit();" => wasShutdown.success(true)
          case "zombie" => isZombie.success(true)
          case _ =>
            logger.error(s"""Unexpected block "$block".""")
        }

      override def onProcessed(): Unit =
        wasClosed.success(true)
    }

    withEchoCommand(standardOut = consumer) { process =>
      process.stop()

      /* An exit command should have been sent. */
      wasShutdown.future.futureValue should be(true)
      wasClosed.future.futureValue should be(true)
      process.isProcessRunning should be(false)

      val console = process.getConsole
      an[IllegalStateException] should be thrownBy console.write("lost\n")

      /* Internal API. */
      val writer = process.standardInputWriter()
      writer.write("zombie\n")
      writer.flush()

      /* Of course, "cat" and "cmd /c more" won't have anything to do with the exit command. In that event, the process should be forcibly killed. If that works successfully, this future will never complete. */
      an[Exception] should be thrownBy isZombie.future.futureValue
    }
  }

}
