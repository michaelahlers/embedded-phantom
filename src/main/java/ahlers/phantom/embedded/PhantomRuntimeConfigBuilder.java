package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.runtime.ICommandLinePostProcessor;
/**
 * Standard {@link IRuntimeConfig} factory.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomRuntimeConfigBuilder
        extends RuntimeConfigBuilder {

    /**
     * Processes output with the {@linkplain PhantomProcessOutputConfig#getDefaultInstance() default instance}, performs no command line processing, and uses the {@linkplain PhantomExtractedArtifactStoreBuilder#defaults() default artifact store}.
     *
     * @return A typical builder, requiring no additional details to produce a configuration instance.
     */
    public RuntimeConfigBuilder defaults() {
        processOutput().setDefault(PhantomProcessOutputConfig.getDefaultInstance());
        commandLinePostProcessor().setDefault(new ICommandLinePostProcessor.Noop());
        artifactStore().setDefault(new PhantomExtractedArtifactStoreBuilder().defaults().build());
        return this;
    }

}
