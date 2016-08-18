package ahlers.phantom.embedded;

import ahlers.phantom.embedded.proxies.IProxy;
import com.google.common.base.Optional;
import de.flapdoodle.embed.process.config.IExecutableProcessConfig;

import java.io.File;

/**
 * Command line options for PhantomJS.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 */
public interface IPhantomProcessConfig
        extends IExecutableProcessConfig {

    Optional<Boolean> debug();

    Optional<Integer> remoteDebuggerPort();

    Optional<Boolean> diskCache();

    Optional<File> diskCachePath();

    Optional<Long> maximumDiskCacheSize();

    Optional<Boolean> ignoreSSLErrors();

    Optional<Boolean> loadImages();

    Optional<File> localStoragePath();

    Optional<Long> localStorageQuota();

    Optional<Boolean> localURLAccess();

    Optional<Boolean> localToRemoteURLAccess();

    Optional<File> offlineStoragePath();

    Optional<Long> offlineStorageQuota();

    Optional<String> outputEncoding();

    Optional<IProxy> proxy();

    Optional<IPhantomScript> script();

}
