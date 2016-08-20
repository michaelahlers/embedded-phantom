package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.store.IArtifactStore;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface MockArtifactStores {

    IArtifactStore artifactStore =
            new PhantomExtractedArtifactStoreBuilder()
                    .defaults()
//.extractDir(new PlatformTemporaryDirectory())
//.download(new PhantomDownloadConfigBuilder()
//        .defaults()
//        .artifactStorePath(new PlatformTemporaryDirectory())
//        .build())
                    .build();


}
