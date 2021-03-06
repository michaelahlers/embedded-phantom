package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.Starter;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomStarter
        extends Starter<IPhantomProcessConfig, PhantomExecutable, IPhantomProcess> {

    private PhantomStarter(final IRuntimeConfig config) {
        super(config);
    }

    public static PhantomStarter getInstance(final IRuntimeConfig runtimeConfig) {
        return new PhantomStarter(runtimeConfig);
    }

    public static PhantomStarter getDefaultInstance() {
        return getInstance(new PhantomRuntimeConfigBuilder().build());
    }

    @Override
    protected PhantomExecutable newExecutable(
            final IPhantomProcessConfig processConfig,
            final Distribution distribution,
            final IRuntimeConfig runtimeConfig,
            final IExtractedFileSet files
    ) {
        return new PhantomExecutable(
                distribution,
                processConfig,
                runtimeConfig,
                files,
                PhantomCommandFormatter.getInstance()
        );
    }

}
