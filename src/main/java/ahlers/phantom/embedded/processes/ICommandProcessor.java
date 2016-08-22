package ahlers.phantom.embedded.processes;

import ahlers.phantom.embedded.PhantomProcess;
import ahlers.phantom.embedded.PhantomRuntimeConfigBuilder;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.IStreamProcessor;

/**
 * Implementations provide access for writing to standard input on a running Phantom process. Compliment with an {@link IStreamProcessor} reference (typically provided to {@link PhantomProcess} by the {@link ProcessOutput} assigned to {@link IRuntimeConfig}, and recoverable from {@link ProcessOutput#getOutput()}).
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see PhantomRuntimeConfigBuilder#processOutput(ProcessOutput)
 * @see PhantomProcess
 */
public class ICommandProcessor {


}
