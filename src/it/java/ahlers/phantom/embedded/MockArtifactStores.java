package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.io.directories.StableTransientDirectory;
import de.flapdoodle.embed.process.store.IArtifactStore;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface MockArtifactStores {

    IArtifactStore artifactStore =
            new PhantomExtractedArtifactStoreBuilder()
                    .defaults()
                    .extractDir(new StableTransientDirectory().withTail("extractions"))
                    .download(new PhantomDownloadConfigBuilder()
                            .defaults()
                            .artifactStorePath(new StableTransientDirectory().withTail("downloads"))
                            .build())
                    .build();


}
