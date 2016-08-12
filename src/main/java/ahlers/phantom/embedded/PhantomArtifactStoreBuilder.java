package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.extract.UUIDTempNaming;
import de.flapdoodle.embed.process.io.directories.PropertyOrPlatformTempDir;
import de.flapdoodle.embed.process.store.ArtifactStoreBuilder;
import de.flapdoodle.embed.process.store.Downloader;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomArtifactStoreBuilder
        extends ArtifactStoreBuilder {

    public PhantomArtifactStoreBuilder() {
        tempDir().setDefault(new PropertyOrPlatformTempDir());
        executableNaming().setDefault(new UUIDTempNaming());
        download().setDefault(new PhantomDownloadConfigBuilder().build());
        downloader().setDefault(new Downloader());
    }

}
