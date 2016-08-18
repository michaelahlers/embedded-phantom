package ahlers.phantom.embedded

import de.flapdoodle.embed.process.config.io.ProcessOutput
import org.scalatest._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomSpec
  extends FlatSpec
          with Matchers {

  //PhantomVersion.values foreach { version =>
  //  it must s"start $version" in {
  //
  //    val runtimeConfig =
  //      new PhantomRuntimeConfigBuilder()
  //        .defaults()
  //        .processOutput(ProcessOutput.getDefaultInstance("FOO"))
  //        .build()
  //
  //    val starter = PhantomStarter.getInstance(runtimeConfig)
  //
  //    val config =
  //      new PhantomProcessConfigBuilder()
  //        .defaults()
  //        .version(version)
  //        .build()
  //
  //    val executablePath = starter.prepare(config)
  //
  //    val process = executablePath.start()
  //
  //
  //    process.stop()
  //
  //    Thread.sleep(2000)
  //
  //  }
  //}

}
