package ahlers.phantom.embedded;

import ahlers.phantom.embedded.processes.IPhantomConsole;
import ahlers.phantom.embedded.processes.WriterPhantomConsole;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.AtomicSafeInitializer;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Represents a running instance of PhantomJS, created by {@link PhantomExecutable}.
 *
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
        /* Throw no exceptions here unless finally terminating the process created by the super class. */
    }

    /*
     * About reflective access to private members:
     *
     * AbstractProcess makes a mistake in calling overridden (virtual) methods from its constructor. This defeats the ability of subclasses to provide additional details to those methods, which this implementation needs to do (<em>e.g.</em>, IPhantomCommandFormatter).
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

    /**
     * Even though the <em>constructor</em> has access to the provided {@link PhantomExecutable} instance, any field initializer would not be called until <em>after</em> {@link #getCommandLine(Distribution, IPhantomProcessConfig, IExtractedFileSet)}. Therefore, it's necessary to reflect into the superclass for a reference that has been initialized so its properties can be provided to the command line generator.
     */
    private PhantomExecutable executable() throws Exception {
        return (PhantomExecutable) FieldUtils
                .getDeclaredField(AbstractProcess.class, "executable", true)
                .get(this);
    }

    private IPhantomCommandFormatter commandFormatter() throws Exception {
        return executable().commandFormatter();
    }

    /**
     * Provided for debugging. <del>Guaranteed to be set only once.</del> Will be {@code null} until {@link #getCommandLine(Distribution, IPhantomProcessConfig, IExtractedFileSet)} is called by the super constructor.
     */
    private ImmutableList<String> command;

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
            final ImmutableList<String> command = commandFormatter().format(distribution, files, processConfig);
            this.command = command;
            return command;
        } catch (final Throwable t) {
            throw new IOException("Error retrieving command formatter from executable.", t);
        }
    }

    /**
     * @deprecated As of 1.0.0, replaced by {@link #getConsole()}.
     */
    public PrintWriter getStandardInput() throws Exception {
        try {
            return new PrintWriter(new OutputStreamWriter(nativeProcess().getOutputStream()));
        } catch (final Throwable t) {
            throw new Exception("Error creating standard input processor.", t);
        }
    }

    /**
     * Internal API, typically delegates to another instance.
     */
    private interface ILocalPhantomConsole extends IPhantomConsole {

        /**
         * Provides access to underlying writing to allow clean up on stop.
         */
        Writer getWriter();

    }

    private final AtomicSafeInitializer<ILocalPhantomConsole> consoleHolder =
            new AtomicSafeInitializer<ILocalPhantomConsole>() {
                @Override
                protected ILocalPhantomConsole initialize() throws ConcurrentException {
                    try {
                        final Writer writer = new OutputStreamWriter(nativeProcess().getOutputStream());
                        final IPhantomConsole console = new WriterPhantomConsole(writer);

                        return new ILocalPhantomConsole() {

                            @Override
                            public void write(final String block) throws IOException {
                                console.write(block);
                            }

                            @Override
                            public void flush() throws IOException {
                                console.flush();
                            }

                            @Override
                            public Writer getWriter() {
                                return writer;
                            }

                        };
                    } catch (final Throwable t) {
                        throw new ConcurrentException("Error initializing console.", t);
                    }
                }
            };

    /**
     * Provides console access to {@code this} process instance. While {@link IPhantomConsole} describes intent, it's important to understand behaviors given this implementation. There are no guarantees regarding evaluation order when multiple threads use this interface. Ordering (by means of a {@linkplain BlockingQueue queue}, for example) are a higher-level concern. It's possible for blocks sent to {@link IPhantomConsole#write(String)} to interleave, creating invalid expressions or unintended results. There are likewise no guarantees (and cannot be) regarding output order simply by virtue of concurrent features of JavaScript.
     */
    public IPhantomConsole getConsole() throws Exception {
        return consoleHolder.get();
    }

    @Override
    protected void stopInternal() {
        try {
            logger.info("Sending exit command.");

            final ILocalPhantomConsole console = consoleHolder.get();

            console.flush();
            console.write("//\n;phantom.exit();\n");
            console.flush();

            console.getWriter().close();

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
