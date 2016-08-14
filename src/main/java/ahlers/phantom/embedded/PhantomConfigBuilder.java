package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
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
    protected static final TypedProperty<IPhantomScript> SCRIPT = TypedProperty.with("script", IPhantomScript.class);

    public PhantomConfigBuilder defaults() {
        property(VERSION).setDefault(PhantomVersion.LATEST);

        return this;
    }

    protected IProperty<IVersion> version() {
        return property(VERSION);
    }

    public PhantomConfigBuilder version(final IVersion value) {
        version().set(value);
        return this;
    }

    protected IProperty<Boolean> debug() {
        return property(DEBUG);
    }

    public PhantomConfigBuilder debug(final Boolean value) {
        debug().set(value);
        return this;
    }

    protected IProperty<IPhantomScript> script() {
        return property(SCRIPT);
    }

    public PhantomConfigBuilder script(final IPhantomScript value) {
        script().set(value);
        return this;
    }

    @Override
    public IPhantomConfig build() {
        final IVersion version = get(VERSION);
        final Optional<Boolean> debug = Optional.fromNullable(get(DEBUG, null));
        final Optional<IPhantomScript> script = Optional.fromNullable(get(SCRIPT, null));

        return new ImmutablePhantomConfig(
                version,
                debug,
                script
        );
    }

    static class ImmutablePhantomConfig
            extends ExecutableProcessConfig
            implements IPhantomConfig {

        private final Optional<Boolean> debug;

        private final Optional<IPhantomScript> script;

        protected ImmutablePhantomConfig(
                final IVersion version,
                final Optional<Boolean> debug,
                final Optional<IPhantomScript> script
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
            this.script = script;
        }

        @Override
        public Optional<Boolean> debug() {
            return debug;
        }

        @Override
        public Optional<IPhantomScript> script() {
            return script;
        }

        /**
         * Produce a {@link PhantomConfigBuilder} that'd make this exact instance to support making variants (this object is immutable).
         */
        @Override
        public PhantomConfigBuilder builder() {
            final PhantomConfigBuilder builder = new PhantomConfigBuilder();

            builder.set(VERSION, version());
            builder.set(DEBUG, debug().or((Boolean) null));
            builder.set(SCRIPT, script().or((IPhantomScript) null));

            return builder;
        }

        @Override
        public IPhantomConfig withScript(final IPhantomScript value) {
            return builder().script(value).build();
        }

    }
}
