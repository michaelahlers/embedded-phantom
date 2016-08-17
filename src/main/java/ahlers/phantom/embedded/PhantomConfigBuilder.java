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

    protected static final TypedProperty<IPhantomCommandFormatter> COMMAND_FORMATTER = TypedProperty.with("command-formatter", IPhantomCommandFormatter.class);

    protected static final TypedProperty<Boolean> DEBUG = TypedProperty.with("debug", Boolean.class);
    protected static final TypedProperty<IPhantomScript> SCRIPT = TypedProperty.with("script", IPhantomScript.class);

    public PhantomConfigBuilder defaults() {
        property(VERSION).setDefault(PhantomVersion.LATEST);

        property(COMMAND_FORMATTER).setDefault(PhantomCommandFormatter.getInstance());

        return this;
    }

    protected IProperty<IVersion> version() {
        return property(VERSION);
    }

    public PhantomConfigBuilder version(final IVersion value) {
        version().set(value);
        return this;
    }

    protected IProperty<IPhantomCommandFormatter> formatter() {
        return property(COMMAND_FORMATTER);
    }

    public PhantomConfigBuilder formatter(final IPhantomCommandFormatter value) {
        formatter().set(value);
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

        final IPhantomCommandFormatter formatter = get(COMMAND_FORMATTER);

        final Optional<Boolean> debug = Optional.fromNullable(get(DEBUG, null));
        final Optional<IPhantomScript> script = Optional.fromNullable(get(SCRIPT, null));

        return new ImmutablePhantomConfig(
                version,
                formatter,
                debug,
                script
        );
    }

    static class ImmutablePhantomConfig
            extends ExecutableProcessConfig
            implements IPhantomConfig {

        private final IPhantomCommandFormatter formatter;

        private final Optional<Boolean> debug;

        private final Optional<IPhantomScript> script;

        protected ImmutablePhantomConfig(
                final IVersion version,
                final IPhantomCommandFormatter formatter,
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

            this.formatter = formatter;

            this.debug = debug;
            this.script = script;
        }

        @Override
        public IPhantomCommandFormatter formatter() {
            return formatter;
        }

        @Override
        public Optional<Boolean> debug() {
            return debug;
        }

        @Override
        public Optional<IPhantomScript> script() {
            return script;
        }

    }
}
