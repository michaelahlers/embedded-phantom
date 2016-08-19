package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.io.directories.TempDirInPlatformTempDir;
import de.flapdoodle.embed.process.store.IArtifactStore;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface MockArtifactStores {

    IArtifactStore artifactStore =
            new PhantomExtractedArtifactStoreBuilder()
                    .defaults()
                    .extractDir(new TempDirInPlatformTempDir())
                    .download(new PhantomDownloadConfigBuilder()
                            .defaults()
                            .artifactStorePath(new TempDirInPlatformTempDir())
                            .build())
                    .build();


}
