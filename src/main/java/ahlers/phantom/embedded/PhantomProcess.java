package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcess
        extends AbstractProcess<IPhantomConfig, PhantomExecutable, PhantomProcess> {

    private final static Logger logger = getLogger(PhantomProcess.class);

    public PhantomProcess(
            final Distribution distribution,
            final IPhantomConfig config,
            final IRuntimeConfig runtimeConfig,
            final PhantomExecutable executable)
            throws IOException {
        super(distribution, config, runtimeConfig, executable);
    }

    @Override
    protected List<String> getCommandLine(
            final Distribution distribution,
            final IPhantomConfig config,
            final IExtractedFileSet executable
    ) throws IOException {
        return PhantomCommandEmitter
                .getInstance(distribution)
                .emit(config, executable);
    }

    @Override
    protected void stopInternal() {

    }

    @Override
    protected void cleanupInternal() {

    }

}
