package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
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
            final PhantomExecutable executable
    ) throws IOException {
        super(distribution, config, runtimeConfig, executable);
    }

    /**
     * Internal API.
     */
    @Override
    protected List<String> getCommandLine(
            final Distribution distribution,
            final IPhantomConfig config,
            final IExtractedFileSet files
    ) throws IOException {
        return config.formatter().format(files, config);
    }

    PrintWriter getStandardInput() {
        try {
            final Field processControlField = AbstractProcess.class.getDeclaredField("process");
            processControlField.setAccessible(true);
            final ProcessControl processControl = (ProcessControl) processControlField.get(this);

            final Field processField = ProcessControl.class.getDeclaredField("process");
            processField.setAccessible(true);
            final Process process = (Process) processField.get(processControl);

            return new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
        } catch (final Throwable t) {
            throw new RuntimeException("Error creating standard input processor.", t);
        }
    }

    @Override
    protected void stopInternal() {
        try {
            logger.info("Sending exit command.");
            final Writer standardInput = getStandardInput();
            standardInput.write("phantom.exit();\n");
            standardInput.flush();
            standardInput.close();
        } catch (final Throwable t) {
            logger.warn("Error issuing exit command (will attempt to kill the process).", t);
            sendKillToProcess();
            tryKillToProcess();
        } finally {
            logger.info("Waiting for process to exit.");
            stopProcess();
        }
    }

    @Override
    protected void cleanupInternal() {
    }

}
