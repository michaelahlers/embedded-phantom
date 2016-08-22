package ahlers.phantom.embedded.processes;

import ahlers.phantom.embedded.PhantomProcess;
import ahlers.phantom.embedded.PhantomRuntimeConfigBuilder;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.IStreamProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Implementations provide access for writing to standard in on a running Phantom process. Compliment with an {@link IStreamProcessor} references (typically provided to {@link PhantomProcess} by the {@link ProcessOutput} assigned to {@link IRuntimeConfig}) to receive standard out and error. This interface's behavior is intentionally modeled after similar stream classes, like {@link OutputStream} or {@link Writer}. Be sure to see {@link PhantomProcess#getConsole()} for more specific behavior.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see PhantomRuntimeConfigBuilder#processOutput(ProcessOutput)
 * @see ProcessOutput#getOutput()
 * @see ProcessOutput#getError()
 * @see PhantomProcess#getConsole()
 */
public interface IPhantomConsole {

    /**
     * Sends {@code block} directly to Phantom's console. Newline characters trigger evaluation.
     *
     * @param block Any portion of a JavaScript expression.
     * @throws IOException Any error from the underlying stream.
     * @see <a href="http://phantomjs.org/repl.html">REPL</a>
     */
    void write(String block) throws IOException;

    /**
     * Causes any underlying buffers to be sent to the console. Does not <em>necessarily</em> cause evaluation, unless a newline is buffered.
     *
     * @throws IOException Any error from the underlying stream.
     */
    void flush() throws IOException;

}
