package ahlers.phantom.embedded

import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.io.{IStreamProcessor, Processors}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.tagobjects.{Disk, Network}
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.Promise

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

      val output: Promise[String] = Promise()

      val processOutput =
        new ProcessOutput(
          new IStreamProcessor {

            override def process(block: String): Unit = {
              println(s"""block: "$block"""")
              if (block.contains(message)) output.success(message)
            }

            override def onProcessed(): Unit = ()

          },
          Processors.silent,
          Processors.silent
        )

      val runtimeConfig =
        new PhantomRuntimeConfigBuilder()
          .defaults()
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

      val executablePath = starter.prepare(config)

      val process = executablePath.start()

      val console = process.getStandardInput

      console.println(s"console.log('$message');")
      console.flush()
      console.close()

      output.future.futureValue should include(message)
      process.stop()

    }
  }

}
