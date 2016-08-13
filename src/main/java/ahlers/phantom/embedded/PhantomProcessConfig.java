package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.ExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * Command line options for PhantomJS.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 */
public class PhantomProcessConfig
        extends ExecutableProcessConfig {

    private final Boolean debug;

    protected PhantomProcessConfig(
            final IVersion version,
            final Boolean debug
    ) {
        super(version, new ISupportConfig() {
            @Override
            public String getName() {
                return "phantomjs";
            }

            @Override
            public String getSupportUrl() {
                return "https://github.com/michaelahlers/embedded-phantom/issues";
            }

            @Override
            public String messageOnException(final Class<?> context, final Exception exception) {
                return "";
            }
        });

        this.debug = debug;
    }

    public static class Builder {

        private final IVersion version;

        private Boolean debug;

        public Builder(final IVersion version) {
            this.version = version;
        }

        public void withDebug(final Boolean debug) {
            this.debug = debug;
        }

        public PhantomProcessConfig build() {
            return new PhantomProcessConfig(version, debug);
        }

    }

}
