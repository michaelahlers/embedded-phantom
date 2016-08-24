package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.Executable;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Represents a ready-for-execution setup, created by {@link PhantomStarter} and produces {@link PhantomProcess}.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomExecutable
        extends Executable<IPhantomProcessConfig, IPhantomProcess> {

    private final static Logger logger = getLogger(PhantomExecutable.class);

    private final IPhantomCommandFormatter commandFormatter;

    PhantomExecutable(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IRuntimeConfig runtimeConfig,
            final IExtractedFileSet files,
            final IPhantomCommandFormatter commandFormatter
    ) {
        super(distribution, processConfig, runtimeConfig, files);
        this.commandFormatter = commandFormatter;
    }

    @Override
    protected IPhantomProcess start(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IRuntimeConfig runtimeConfig
    ) throws IOException {
        return new PhantomProcess(distribution, processConfig, runtimeConfig, this);
    }

    public IPhantomCommandFormatter commandFormatter() {
        return commandFormatter;
    }

}
