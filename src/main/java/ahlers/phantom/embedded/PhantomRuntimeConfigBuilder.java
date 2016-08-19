package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.runtime.ICommandLinePostProcessor;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomRuntimeConfigBuilder
        extends RuntimeConfigBuilder {

    public RuntimeConfigBuilder defaults() {
        processOutput().setDefault(PhantomProcessOutputConfig.getDefaultInstance());
        commandLinePostProcessor().setDefault(new ICommandLinePostProcessor.Noop());
        artifactStore().setDefault(new PhantomExtractedArtifactStoreBuilder().defaults().build());
        return this;
    }

}
