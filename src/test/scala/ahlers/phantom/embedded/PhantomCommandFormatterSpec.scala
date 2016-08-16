package ahlers.phantom.embedded

import java.io.File

import ahlers.phantom.embedded.PhantomCommandFormatter._
import ahlers.phantom.embedded.PhantomVersion._
import com.google.common.base.Optional
import com.google.common.collect.ImmutableList
import de.flapdoodle.embed.process.distribution.{Distribution, IVersion}
import de.flapdoodle.embed.process.extract.IExtractedFileSet
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.collection.convert.WrapAsScala._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomCommandFormatterSpec
  extends WordSpec
          with Matchers
          with MockFactory {

  "Executable" must {
    "format" when {
      PhantomCommandFormatter.values foreach { emitter =>
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
      PhantomCommandFormatter.values foreach { emitter =>
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
      PhantomCommandFormatter.values foreach { emitter =>
        s"using $emitter" in {
          emitter.scripts(Optional.absent[IPhantomScript]) should be(empty)
        }
      }
    }
  }

  "Version-specific selector" must {
    PhantomVersion.values :+ mock[IVersion] foreach { version =>
      val formatter =
        version match {
          case V211 => Version21
          case _ => AnyVersion
        }

      s"""return "$formatter" for version "$version"""" in {
        val distribution = new Distribution(version, null, null)
        PhantomCommandFormatter.getInstance(distribution) should be(formatter)
      }

      s"""match "$version"""" in {
        formatter.matches(version) should be(true)
      }
    }
  }

}
