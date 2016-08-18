package ahlers.phantom.embedded

import java.io.File

import ahlers.phantom.embedded.proxies.IProxy
import com.google.common.base.Optional
import org.scalamock.scalatest.MockFactory
import org.scalatest._

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomProcessConfigBuilderSpec
  extends FlatSpec
          with Matchers
          with MockFactory {

  it must "assign defaults" in {
    val actual =
      new PhantomProcessConfigBuilder()
        .defaults()
        .build()

    actual.version() should be(PhantomVersion.LATEST)

    actual.debug should be(Optional.absent)
    actual.remoteDebuggerPort should be(Optional.absent)
    actual.diskCache should be(Optional.absent)
    actual.diskCachePath should be(Optional.absent)
    actual.maximumDiskCacheSize should be(Optional.absent)
    actual.ignoreSSLErrors should be(Optional.absent)
    actual.loadImages should be(Optional.absent)
    actual.localStoragePath should be(Optional.absent)
    actual.localStorageQuota should be(Optional.absent)
    actual.localURLAccess should be(Optional.absent)
    actual.localToRemoteURLAccess should be(Optional.absent)
    actual.offlineStoragePath should be(Optional.absent)
    actual.offlineStorageQuota should be(Optional.absent)
    actual.outputEncoding should be(Optional.absent)
    actual.proxy should be(Optional.absent)
    actual.script should be(Optional.absent)
  }

  it must "preserve all values" in {
    val version = PhantomVersion.V211

    val debug = true
    val remoteDebuggerPort = 0
    val diskCache = true
    val diskCachePath = new File("")
    val maximumDiskCacheSize = 0L
    val ignoreSSLErrors = true
    val loadImages = true
    val localStoragePath = new File("")
    val localStorageQuota = 0L
    val localURLAccess = true
    val localToRemoteURLAccess = true
    val offlineStoragePath = new File("")
    val offlineStorageQuota = 0L
    val outputEncoding = "encoding"
    val proxy = mock[IProxy]
    val script = mock[IPhantomScript]

    val actual =
      new PhantomProcessConfigBuilder()
        .version(version)
        .debug(debug)
        .remoteDebuggerPort(remoteDebuggerPort)
        .diskCache(diskCache)
        .diskCachePath(diskCachePath)
        .maximumDiskCacheSize(maximumDiskCacheSize)
        .ignoreSSLErrors(ignoreSSLErrors)
        .loadImages(loadImages)
        .localStoragePath(localStoragePath)
        .localStorageQuota(localStorageQuota)
        .localURLAccess(localURLAccess)
        .localToRemoteURLAccess(localToRemoteURLAccess)
        .offlineStoragePath(offlineStoragePath)
        .offlineStorageQuota(offlineStorageQuota)
        .outputEncoding(outputEncoding)
        .proxy(proxy)
        .script(script)
        .build()

    actual.version() should be(version)

    actual.debug should be(Optional.of(debug))
    actual.remoteDebuggerPort should be(Optional.of(remoteDebuggerPort))
    actual.diskCache should be(Optional.of(diskCache))
    actual.diskCachePath should be(Optional.of(diskCachePath))
    actual.maximumDiskCacheSize should be(Optional.of(maximumDiskCacheSize))
    actual.ignoreSSLErrors should be(Optional.of(ignoreSSLErrors))
    actual.loadImages should be(Optional.of(loadImages))
    actual.localStoragePath should be(Optional.of(localStoragePath))
    actual.localStorageQuota should be(Optional.of(localStorageQuota))
    actual.localURLAccess should be(Optional.of(localURLAccess))
    actual.localToRemoteURLAccess should be(Optional.of(localToRemoteURLAccess))
    actual.offlineStoragePath should be(Optional.of(offlineStoragePath))
    actual.offlineStorageQuota should be(Optional.of(offlineStorageQuota))
    actual.outputEncoding should be(Optional.of(outputEncoding))
    actual.proxy should be(Optional.of(proxy))
    actual.script should be(Optional.of(script))
  }

}
