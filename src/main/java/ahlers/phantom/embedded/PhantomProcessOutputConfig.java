package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.io.ProcessOutput;

/**
 * Pre-configured {@link ProcessOutput} instances.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcessOutputConfig {

    private PhantomProcessOutputConfig() {
    }

    public static final String LABEL = "PhantomJS";

    /**
     * Delegates to {@link ProcessOutput#getDefaultInstance(String)} with {@link #LABEL}.
     */
    public static ProcessOutput getDefaultInstance() {
        return ProcessOutput.getDefaultInstance(LABEL);
    }

    /**
     * Delegates to {@link ProcessOutput#getInstance(String, java.util.logging.Logger)} with {@link #LABEL}.
     */
    public static ProcessOutput getInstance(final java.util.logging.Logger logger) {
        return ProcessOutput.getInstance(LABEL, logger);
    }

    /**
     * Delegates to {@link ProcessOutput#getInstance(String, org.slf4j.Logger)} with {@link #LABEL}.
     */
    public static ProcessOutput getInstance(final org.slf4j.Logger logger) {
        return ProcessOutput.getInstance(LABEL, logger);
    }

}
