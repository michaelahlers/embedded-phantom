package ahlers.phantom.embedded;

import ahlers.phantom.embedded.processes.IPhantomConsole;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.IStopable;

/**
 * Describes features presented by the native Phantom process.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IPhantomProcess
        extends IStopable {

    /**
     * Indicates whether Phantom is running or not.
     *
     * @see AbstractProcess#isProcessRunning()
     */
    boolean isProcessRunning();

    /**
     * Provides access to the Phantom REPL.
     */
    IPhantomConsole getConsole() throws Exception;

}
