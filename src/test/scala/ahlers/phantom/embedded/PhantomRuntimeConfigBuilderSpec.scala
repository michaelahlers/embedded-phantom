package ahlers.phantom.embedded

import de.flapdoodle.embed.process.builder.IProperty
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.runtime.ICommandLinePostProcessor
import de.flapdoodle.embed.process.store.IArtifactStore
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

import scala.language.{postfixOps, reflectiveCalls}

/**
 * @author [[mailto:michael@ahlers.co Michael Ahlers]]
 */
class PhantomRuntimeConfigBuilderSpec
  extends FlatSpec
          with PrivateMethodTester
          with Matchers
          with MockFactory {

  it must "assign defaults" in {

    /** Anonymous implementation works-around scalatest/scalatest#645. */
    val builder = new PhantomRuntimeConfigBuilder() {
      override def commandLinePostProcessor(): IProperty[ICommandLinePostProcessor] = super.commandLinePostProcessor()

      override def processOutput(): IProperty[ProcessOutput] = super.processOutput()

      override def artifactStore(): IProperty[IArtifactStore] = super.artifactStore()
    }


    val actual =
      builder
        .defaults()
        .build()

    val processOutput = PrivateMethod[IProperty[ProcessOutput]]('processOutput)
    val commandLinePostProcessor = PrivateMethod[IProperty[ICommandLinePostProcessor]]('commandLinePostProcessor)
    val artifactStore = PrivateMethod[IProperty[IArtifactStore]]('artifactStore)

    actual.getProcessOutput should be(builder invokePrivate processOutput() get)
    actual.getCommandLinePostProcessor should be(builder invokePrivate commandLinePostProcessor() get)
    actual.getArtifactStore should be(builder invokePrivate artifactStore() get)
  }

}
