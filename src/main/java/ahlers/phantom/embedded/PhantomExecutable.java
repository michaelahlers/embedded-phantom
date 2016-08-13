package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.Executable;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomExecutable
        extends Executable<IPhantomConfig, PhantomProcess> {

    private final static Logger logger = getLogger(PhantomExecutable.class);

    private final IExtractedFileSet files;

    PhantomExecutable(
            final Distribution distribution,
            final IPhantomConfig config,
            final IRuntimeConfig runtimeConfig,
            final IExtractedFileSet files
    ) {
        super(distribution, config, runtimeConfig, files);
        this.files = files;
    }

    @Override
    protected PhantomProcess start(
            final Distribution distribution,
            final IPhantomConfig config,
            final IRuntimeConfig runtimeConfig
    ) throws IOException {
        return new PhantomProcess(distribution, config, runtimeConfig, this);
    }

}
