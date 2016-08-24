package ahlers.phantom.embedded;

import ahlers.phantom.embedded.processes.IPhantomConsole;
import ahlers.phantom.embedded.processes.WriterPhantomConsole;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.config.IExecutableProcessConfig;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Represents a running instance of PhantomJS, created by {@link PhantomExecutable}.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcess
        implements IPhantomProcess {

    private final static Logger logger = getLogger(PhantomProcess.class);

    /**
     * Provided for debugging.
     */
    private final ImmutableList<String> command;

    /**
     * Because {@link AbstractProcess}'s constructor starts the process immediately&mdash;invoking {@link AbstractProcess#getCommandLine(Distribution, IExecutableProcessConfig, IExtractedFileSet)}&mdash;there's no way to provide additional details needed. A prior implementation of this class used reflection to get extra values from the parent object's {@link PhantomExecutable} instance when generating the command line. Now using delegation the constructor is able to initialize class members that can be read when the delegateProcess starts, removing the reflective calls and setting up the instance entirely before starting the Phantom process.
     */
    private class DelegateProcess
            extends AbstractProcess<IPhantomProcessConfig, PhantomExecutable, IPhantomProcess> {

        public DelegateProcess(Distribution distribution, IPhantomProcessConfig config, IRuntimeConfig runtimeConfig, PhantomExecutable executable) throws IOException {
            super(distribution, config, runtimeConfig, executable);
        }

        /**
         * A trivial reader method for {@link PhantomProcess#command}.
         */
        @Override
        protected List<String> getCommandLine(
                final Distribution distribution,
                final IPhantomProcessConfig processConfig,
                final IExtractedFileSet files
        ) throws IOException {
            return command;
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

        @Override
        protected void stopInternal() {
            try {
                logger.info("Sending exit command.");

                final ILocalPhantomConsole console = consoleHolder.get();

                console.flush();
                console.write("//\n;phantom.exit();\n");
                console.flush();

                logger.info("Closing console input (future expressions will fail.");

                console.destroy();

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

    private final DelegateProcess delegateProcess;

    public PhantomProcess(
            final Distribution distribution,
            final IPhantomProcessConfig processConfig,
            final IRuntimeConfig runtimeConfig,
            final PhantomExecutable executable
    ) throws IOException {
        final IPhantomCommandFormatter commandFormatter = executable.commandFormatter();

        this.command = commandFormatter.format(distribution, executable.getFile(), processConfig);
        this.delegateProcess = new DelegateProcess(distribution, processConfig, runtimeConfig, executable);

        /* Throw no exceptions here unless finally terminating the process created by the super class. */
    }

    /**
     * @deprecated As of 1.0.0, replaced by {@link #getConsole()}.
     */
    public PrintWriter getStandardInput() throws Exception {
        return standardInputWriter();
    }

    PrintWriter standardInputWriter() throws Exception {
        try {
            return new PrintWriter(new OutputStreamWriter(delegateProcess.nativeProcess().getOutputStream()));
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
        void destroy() throws Exception;

    }

    private final AtomicSafeInitializer<ILocalPhantomConsole> consoleHolder =
            new AtomicSafeInitializer<ILocalPhantomConsole>() {
                @Override
                protected ILocalPhantomConsole initialize() throws ConcurrentException {
                    try {
                        final Writer writer = standardInputWriter();
                        final IPhantomConsole console = new WriterPhantomConsole(writer);

                        return new ILocalPhantomConsole() {

                            private final AtomicBoolean destroyed = new AtomicBoolean();

                            @Override
                            public void write(final String block) throws Exception {
                                if (destroyed.get()) {
                                    throw new IllegalStateException("Console has been closed (explicit stop of process made).");
                                }

                                console.write(block);
                            }

                            @Override
                            public void flush() throws Exception {
                                console.flush();
                            }

                            @Override
                            public void destroy() throws Exception {
                                destroyed.set(true);
                                writer.flush();
                                writer.close();
                            }
                        };
                    } catch (final Throwable t) {
                        throw new ConcurrentException("Error initializing console.", t);
                    }
                }
            };

    @Override
    public boolean isProcessRunning() {
        return delegateProcess.isProcessRunning();
    }

    /**
     * Provides console access to {@code this} process instance. While {@link IPhantomConsole} describes intent, it's important to understand behaviors given this implementation. There are no guarantees regarding evaluation order when multiple threads use this interface. Ordering (by means of a {@linkplain BlockingQueue queue}, for example) are a higher-level concern. It's possible for blocks sent to {@link IPhantomConsole#write(String)} to interleave, creating invalid expressions or unintended results. There are likewise no guarantees (and cannot be) regarding output order simply by virtue of concurrent features of JavaScript.
     */
    public IPhantomConsole getConsole() throws Exception {
        return consoleHolder.get();
    }

    @Override
    public void stop() {
        delegateProcess.stop();
    }

}
