package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcess
        extends AbstractProcess<IPhantomProcessConfig, PhantomExecutable, PhantomProcess> {

    private final static Logger logger = getLogger(PhantomProcess.class);

    public PhantomProcess(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IRuntimeConfig runtimeConfig,
            final PhantomExecutable executable
    ) throws IOException {
        super(distribution, processConfig, runtimeConfig, executable);
    }

    /*
     * About reflective access to private members.
     *
     * AbstractProcess makes a glaring mistake in calling overridden (virtual) methods from its constructor. This defeats the ability of subclasses to provide additional details to those methods, which this implementation needs (<em>e.g.</em>, IPhantomCommandFormatter
     *
     * For more information see these answers on Stack Overflow:
     *
     * - http://stackoverflow.com/a/2529618/700420
     * - http://stackoverflow.com/a/2529661/700420
     *
     * These explain the issue, and refer to work by Joshua Bloch and Neal Grafter.
     */

    private ProcessControl processControl() throws Exception {
        return (ProcessControl) FieldUtils
                .getDeclaredField(AbstractProcess.class, "process", true)
                .get(this);
    }

    private Process nativeProcess() throws Exception {
        return (Process) FieldUtils
                .getDeclaredField(ProcessControl.class, "process", true)
                .get(processControl());
    }

    private PhantomExecutable executable() throws Exception {
        return (PhantomExecutable) FieldUtils
                .getDeclaredField(AbstractProcess.class, "executable", true)
                .get(this);
    }

    private IPhantomCommandFormatter commandFormatter() throws Exception {
        return executable().commandFormatter();
    }

    /**
     * Internal API.
     */
    @Override
    protected List<String> getCommandLine(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IExtractedFileSet files
    ) throws IOException {
        try {
            return commandFormatter().format(files, processConfig);
        } catch (final Throwable t) {
            throw new IOException("Error retrieving command formatter from executable.", t);
        }
    }

    PrintWriter getStandardInput() throws Exception {
        try {
            return new PrintWriter(new OutputStreamWriter(nativeProcess().getOutputStream()));
        } catch (final Throwable t) {
            throw new Exception("Error creating standard input processor.", t);
        }
    }

    @Override
    protected void stopInternal() {
        try {
            logger.info("Sending exit command.");
            final Writer standardInput = getStandardInput();
            standardInput.flush();
            standardInput.write("\r\n;phantom.exit();\r\n");
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
