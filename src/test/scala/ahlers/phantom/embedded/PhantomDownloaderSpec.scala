package ahlers.phantom.embedded

import java.io.File

import de.flapdoodle.embed.process.config.store.IDownloadConfig
import de.flapdoodle.embed.process.distribution.Distribution
import de.flapdoodle.embed.process.store.IDownloader
import org.apache.commons.codec.binary.Hex
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomDownloaderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "accept valid downloads" in {
    val digest: Array[Byte] = Hex.decodeHex("00".toCharArray)
    val download = new File("")
    val downloadConfig = mock[IDownloadConfig]
    val distribution = new Distribution(null, null, null)

    val delegate = mock[IDownloader]
    (delegate.download(_: IDownloadConfig, _: Distribution)).expects(downloadConfig, distribution).returns(download)

    val signature = mock[IPhantomSignature]
    (signature.digest _).expects().returns(digest)
    (signature.digest(_: File)).expects(download).returns(digest)

    val downloader =
      new PhantomDownloader(delegate) {
        override protected def signatureFor(distribution: Distribution): IPhantomSignature = signature
      }

    downloader.download(downloadConfig, distribution) should be(download)
  }

  it must "reject corrupt downloads" in {
    val download = new File("")
    val downloadConfig = mock[IDownloadConfig]
    val distribution = new Distribution(null, null, null)

    val delegate = mock[IDownloader]
    (delegate.download(_: IDownloadConfig, _: Distribution)).expects(downloadConfig, distribution).returns(download)

    val signature = mock[IPhantomSignature]
    (signature.digest _).expects().returns(Hex.decodeHex("00".toCharArray)).atLeastOnce()
    (signature.digest(_: File)).expects(download).returns(Hex.decodeHex("01".toCharArray))

    val downloader =
      new PhantomDownloader(delegate) {
        override protected def signatureFor(distribution: Distribution): IPhantomSignature = signature
      }

    val error = the[InvalidDownloadException] thrownBy downloader.download(downloadConfig, distribution)
    error.getSignature should be(signature)
    error.getFile should be(download)
  }

}
