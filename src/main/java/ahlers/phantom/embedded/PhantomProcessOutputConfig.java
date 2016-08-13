package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.io.ProcessOutput;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcessOutputConfig {

    private PhantomProcessOutputConfig() {
    }

    private static String LABEL = "PhantomJS";

    public static ProcessOutput getDefaultInstance() {
        return ProcessOutput.getDefaultInstance(LABEL);
    }

    public static ProcessOutput getInstance(final java.util.logging.Logger logger) {
        return ProcessOutput.getInstance(LABEL, logger);
    }

    public static ProcessOutput getInstance(final org.slf4j.Logger logger) {
        return ProcessOutput.getInstance(LABEL, logger);
    }

}
