package ahlers.phantom.embedded

import java.io.File

import com.google.common.base.Optional
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.convert.WrapAsJava._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomScriptBuilderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "assign defaults" in {
    val source = new File("")
    val arguments = Nil

    val actual =
      new PhantomScriptBuilder()
        .defaults()
        .source(source)
        .build()

    actual.encoding should be(Optional.absent)
    actual.language should be(Optional.absent)
    actual.source should be(source)
    actual.arguments should be(arguments)
  }

  it must "preserve all values" in {
    val encoding = "encoding"
    val language = "langage"
    val source = new File("source")
    val arguments = List("argument", "argument")

    val actual =
      new PhantomScriptBuilder()
        .encoding(encoding)
        .language(language)
        .source(source)
        .arguments(arguments)
        .build()

    actual.encoding should be(encoding)
    actual.language should be(language)
    actual.source should be(source)
    actual.arguments should be(arguments)
  }

}
