package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcess
        extends AbstractProcess<IPhantomProcessConfig, PhantomExecutable, PhantomProcess> {

    private final static Logger logger = getLogger(PhantomProcess.class);

    private final ImmutableList<String> command;

    public PhantomProcess(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IRuntimeConfig runtimeConfig,
            final PhantomExecutable executable
    ) throws IOException {
        super(distribution, processConfig, runtimeConfig, executable);
        this.command = ImmutableList.copyOf(getCommandLine(distribution, processConfig, executable.getFile()));
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

    /**
     * This API is proof-of-concept only, and will be replaced soon.
     */
    public PrintWriter getStandardInput() throws Exception {
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

            final PrintWriter standardInput = getStandardInput();

            standardInput.flush();
            standardInput.println();
            standardInput.println(";phantom.exit();");
            standardInput.flush();
            standardInput.close();

        } catch (final Throwable t) {
            logger.warn("Error issuing exit command (will attempt to kill the process).", t);
        } finally {
            sendKillToProcess();
            tryKillToProcess();

            logger.info("Waiting for process to exit.");

            try {
                final Process process = nativeProcess();
                process.waitFor();
                logger.info(String.format("Received exit code %s (command was: %s).",
                        process.exitValue(),
                        StringUtils.join(command)
                ));
            } catch (final Throwable t) {
                logger.warn("Error waiting for process to exit.", t);
            }

            /* Almost always throws an exception, even if the process successfully terminated. */
            // stopProcess();
        }
    }

    @Override
    protected void cleanupInternal() {
    }

}
