package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.ExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;

import static ahlers.phantom.embedded.PhantomConfig.Builder;

/**
 * Command line options for PhantomJS.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 */
public class PhantomConfig
        extends ExecutableProcessConfig
        implements IPhantomConfig<Builder> {

    private final Boolean debug;

    protected PhantomConfig(
            final IVersion version,
            final Boolean debug
    ) {
        super(version, new ISupportConfig() {
            @Override
            public String getName() {
                return "embedded-phantom";
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

    @Override
    public Boolean debug() {
        return debug;
    }

    @Override
    public Builder builder() {
        return new Builder(
                version(),
                debug()
        );
    }

    public static class Builder {

        private final IVersion version;

        private Boolean debug;

        public Builder(
                final IVersion version,
                final Boolean debug
        ) {
            this.version = version;
            this.debug = debug;
        }

        public void withDebug(final Boolean debug) {
            this.debug = debug;
        }

        public PhantomConfig build() {
            return new PhantomConfig(version, debug);
        }

    }

}
