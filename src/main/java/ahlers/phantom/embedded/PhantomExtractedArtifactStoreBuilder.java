package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.extract.NoopTempNaming;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.io.directories.TempDirInPlatformTempDir;
import de.flapdoodle.embed.process.io.directories.UserHome;
import de.flapdoodle.embed.process.store.Downloader;
import de.flapdoodle.embed.process.store.ExtractedArtifactStoreBuilder;

import java.nio.file.Paths;

/**
 * Defines where and how files used at runtime are extracted from downloaded archives.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomExtractedArtifactStoreBuilder
        extends ExtractedArtifactStoreBuilder {

    public PhantomExtractedArtifactStoreBuilder defaults() {
        /* Temporary directory used for extracting files (which, if extraction fails, these incomplete files should not be found in the artifact store). */
        tempDir().setDefault(new TempDirInPlatformTempDir());

        /* Defines where successfully-extracted files will land. */
        extractDir().setDefault(new UserHome(Paths.get(".embedded-phantom", "extractions").toString()));
        extractExecutableNaming().setDefault(new NoopTempNaming());

        /* Incorporates the user's name into the executable file name. */
        executableNaming().setDefault(new UserTempNaming());

        download().setDefault(new PhantomDownloadConfigBuilder().defaults().build());
        downloader().setDefault(new Downloader());

        return this;
    }

}
