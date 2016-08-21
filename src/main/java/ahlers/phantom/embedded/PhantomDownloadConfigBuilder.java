package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.store.DownloadConfigBuilder;
import de.flapdoodle.embed.process.config.store.DownloadPath;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.extract.UUIDTempNaming;
import de.flapdoodle.embed.process.io.directories.UserHome;
import de.flapdoodle.embed.process.io.progress.StandardConsoleProgressListener;

import java.nio.file.Paths;

/**
 * Standard {@link IDownloadConfig} factory.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomDownloadConfigBuilder
        extends DownloadConfigBuilder {

    /**
     * Temporary files are given random and unique names, sourced from Ahlers Consulting's artifact repository (at {@code http://artifacts.ahlers.consulting/phantomjs/}), and ultimately stored in {@code downloads} under {@code .embedded-platform}.
     *
     * @return A typical builder, requiring no additional details to produce a configuration instance.
     */
    public PhantomDownloadConfigBuilder defaults() {
        /* Names temporary, in-progress downloads. */

        fileNaming().setDefault(new UUIDTempNaming());
        // fileNaming().setDefault(new UserTempNaming());

        downloadPrefix().setDefault(new DownloadPrefix("downloads"));

        /* Official download site appears problematic for programmatic user agents. See michaelahlers/specter#1 (and ariya/phantomjs#13951). */

        // downloadPath().setDefault(new DownloadPath("https://bitbucket.org/ariya/phantomjs/downloads/"));
        // downloadPath().setDefault(new DownloadPath("https://cnpmjs.org/downloads/"));
        // downloadPath().setDefault(new DownloadPath("https://github.com/paladox/phantomjs/releases/download/2.1.7/"));
        downloadPath().setDefault(new DownloadPath("http://artifacts.ahlers.consulting/phantomjs/"));

        packageResolver().setDefault(PhantomPackageResolver.getInstance());

        /* Defines where completed download files will land. */
        artifactStorePath().setDefault(new UserHome(Paths.get(".embedded-phantom", "downloads").toString()));

        progressListener().setDefault(new StandardConsoleProgressListener());
        userAgent().setDefault(new UserAgent("Mozilla/5.0 (compatible Embedded Phantom +https://github.com/michaelahlers/embedded-phantom)"));

        return this;
    }

}
