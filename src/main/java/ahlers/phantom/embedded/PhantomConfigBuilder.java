package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.TypedProperty;
import de.flapdoodle.embed.process.config.ExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomConfigBuilder
        extends AbstractBuilder<IPhantomConfig> {

    protected static final TypedProperty<IVersion> VERSION = TypedProperty.with("version", IVersion.class);
    protected static final TypedProperty<Boolean> DEBUG = TypedProperty.with("debug", Boolean.class);

    public PhantomConfigBuilder() {
        property(VERSION).setDefault(PhantomVersion.LATEST);
    }

    @Override
    public IPhantomConfig build() {
        final IVersion version = get(VERSION);
        final Boolean debug = get(DEBUG);

        return new ImmutablePhantomConfig(
                version,
                debug
        );
    }

    static class ImmutablePhantomConfig
            extends ExecutableProcessConfig
            implements IPhantomConfig {

        private final Boolean debug;

        protected ImmutablePhantomConfig(
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

        @Override
        public Boolean debug() {
            return debug;
        }

        /**
         * Produce a {@link PhantomConfigBuilder} that'd make this exact instance to support making variants (this object is immutable).
         */
        @Override
        public PhantomConfigBuilder builder() {
            final PhantomConfigBuilder builder = new PhantomConfigBuilder();

            builder.set(VERSION, version());
            builder.set(DEBUG, debug());

            return builder;
        }

    }
}
