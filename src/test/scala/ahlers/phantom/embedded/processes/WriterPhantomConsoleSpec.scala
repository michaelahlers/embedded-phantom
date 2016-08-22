package ahlers.phantom.embedded.processes

import java.io.Writer

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class WriterPhantomConsoleSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "delegate write calls" in {
    val block = "message"
    val writer = mock[Writer]
    (writer.write(_: String)).expects(block).once()
    new WriterPhantomConsole(writer).write(block)
  }

  it must "delegate flush calls" in {
    val writer = mock[Writer]
    (writer.flush _).expects().once()
    new WriterPhantomConsole(writer).flush()
  }

}
