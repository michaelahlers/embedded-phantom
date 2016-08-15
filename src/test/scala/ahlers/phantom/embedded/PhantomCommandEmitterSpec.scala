package ahlers.phantom.embedded

import java.io.File

import ahlers.phantom.embedded.PhantomCommandEmitter._
import com.google.common.base.Optional
import com.google.common.collect.ImmutableList
import de.flapdoodle.embed.process.extract.IExtractedFileSet
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.collection.convert.WrapAsScala._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomCommandEmitterSpec
  extends WordSpec
          with Matchers
          with MockFactory {

  "Executable" must {
    "format" when {
      PhantomCommandEmitter.values foreach { emitter =>
        s"using $emitter" in {
          val expected = new File("/executable")

          val files = mock[IExtractedFileSet]
          (files.executable _).expects().returns(expected)

          emitter.executable(files) should be(expected.getAbsolutePath)
        }
      }
    }
  }

  "Present arguments" must {
    "format" when {

      s"using $AnyVersion" in {
        val config = mock[IPhantomConfig]
        (config.debug _).expects().returns(Optional.of(true))

        AnyVersion.arguments(config) should contain theSameElementsInOrderAs {
          "--debug=true" ::
            Nil
        }
      }

      s"using $Version21" in {
        val config = mock[IPhantomConfig]
        (config.debug _).expects().returns(Optional.of(true))

        Version21.arguments(config) should contain theSameElementsInOrderAs {
          "--debug=true" ::
            Nil
        }
      }

    }
  }

  "Present scripts" must {
    val source = new File("source.js")
    val arguments = ImmutableList.of("0", "1")

    "format" when {
      PhantomCommandEmitter.values foreach { emitter =>
        s"using $emitter" in {
          val script = mock[IPhantomScript]

          (script.source _).expects().returns(source)
          (script.arguments _).expects().returns(arguments)

          emitter.scripts(Optional.of(script)) should contain theSameElementsInOrderAs (source.getAbsolutePath +: arguments)
        }
      }
    }
  }

  "Absent scripts" must {
    "format" when {
      PhantomCommandEmitter.values foreach { emitter =>
        s"using $emitter" in {
          emitter.scripts(Optional.absent[IPhantomScript]) should be(empty)
        }
      }
    }
  }

}
