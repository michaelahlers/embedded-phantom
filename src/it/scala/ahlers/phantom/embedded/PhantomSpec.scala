package ahlers.phantom.embedded

import ahlers.phantom.embedded.MockArtifactStores.newArtifactStore
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.io.{IStreamProcessor, Processors}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.tagobjects.{Disk, Network}
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.{Future, Promise}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomSpec
  extends FlatSpec
          with Matchers
          with ScalaFutures {

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  PhantomVersion.values foreach { version =>
    it must s"start $version, and receive commands" taggedAs(Network, Disk) in {

      val message = "Hello, World!"

      val outputProcessor = new FutureStreamProcessor(message)

      val processOutput =
        new ProcessOutput(
          outputProcessor,
          Processors.silent,
          Processors.silent
        )

      val runtimeConfig =
        new PhantomRuntimeConfigBuilder()
          .defaults()
          .artifactStore(newArtifactStore)
          .processOutput(processOutput)
          .build()

      val starter =
        PhantomStarter
          .getInstance(runtimeConfig)

      val config =
        new PhantomProcessConfigBuilder()
          .defaults()
          .version(version)
          .build()

      val executable = starter.prepare(config)

      val process = executable.start()

      val console = process.getStandardInput

      console.println(s"console.log('$message');")
      console.flush()
      console.close()

      outputProcessor.getOutput().futureValue should include(message)

      process.stop()

    }
  }

  class FutureStreamProcessor(message: String)
    extends IStreamProcessor {

    private val output: Promise[String] = Promise()

    override def process(block: String): Unit =
      if (block.contains(message)) output.success(block)

    override def onProcessed(): Unit = ()

    def getOutput(): Future[String] =
      output.future

  }

}
