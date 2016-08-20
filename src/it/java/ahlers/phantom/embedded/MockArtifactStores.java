package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.io.directories.StableTransientDirectory;
import de.flapdoodle.embed.process.store.IArtifactStore;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class MockArtifactStores {

    private MockArtifactStores() {
    }

    static IArtifactStore newArtifactStore() {
        final StableTransientDirectory directory = new StableTransientDirectory();

        return new PhantomExtractedArtifactStoreBuilder()
                .defaults()
                .extractDir(directory.withTail("extractions"))
                .download(new PhantomDownloadConfigBuilder()
                        .defaults()
                        .artifactStorePath(directory.withTail("downloads"))
                        .build())
                .build();
    }

}
