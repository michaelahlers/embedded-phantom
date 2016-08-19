package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.store.DownloadConfigBuilder;
import de.flapdoodle.embed.process.config.store.DownloadPath;
import de.flapdoodle.embed.process.extract.UUIDTempNaming;
import de.flapdoodle.embed.process.io.directories.UserHome;
import de.flapdoodle.embed.process.io.progress.StandardConsoleProgressListener;

import java.nio.file.Paths;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomDownloadConfigBuilder
        extends DownloadConfigBuilder {

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
