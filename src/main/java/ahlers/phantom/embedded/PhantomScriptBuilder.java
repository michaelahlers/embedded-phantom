package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
import de.flapdoodle.embed.process.builder.TypedProperty;

import java.io.File;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomScriptBuilder
        extends AbstractBuilder<IPhantomScript> {

    protected static final TypedProperty<String> ENCODING = TypedProperty.with("encoding", String.class);
    protected static final TypedProperty<String> LANGUAGE = TypedProperty.with("language", String.class);

    protected static final TypedProperty<File> SOURCE = TypedProperty.with("source", File.class);

    private ImmutableList.Builder<String> arguments = ImmutableList.builder();

    public PhantomScriptBuilder defaults() {
        return this;
    }

    protected IProperty<String> encoding() {
        return property(ENCODING);
    }

    public PhantomScriptBuilder encoding(final String value) {
        encoding().set(value);
        return this;
    }

    protected IProperty<String> language() {
        return property(LANGUAGE);
    }

    public PhantomScriptBuilder language(final String value) {
        language().set(value);
        return this;
    }

    protected IProperty<File> source() {
        return property(SOURCE);
    }

    public PhantomScriptBuilder source(final File value) {
        source().set(value);
        return this;
    }

    protected ImmutableList.Builder<String> arguments() {
        return arguments;
    }

    public PhantomScriptBuilder arguments(final Iterable<String> value) {
        this.arguments = ImmutableList.builder();
        this.arguments.addAll(value);
        return this;
    }

    public PhantomScriptBuilder argument(final String value) {
        this.arguments.add(value);
        return this;
    }

    @Override
    public IPhantomScript build() {
        final Optional<String> encoding = Optional.fromNullable(get(ENCODING, null));
        final Optional<String> language = Optional.fromNullable(get(LANGUAGE, null));
        final File source = source().get();
        final ImmutableList<String> arguments = arguments().build();

        return new ImmutablePhantomScript(
                encoding,
                language,
                source,
                arguments
        );
    }

    static class ImmutablePhantomScript
            implements IPhantomScript {

        private final Optional<String> encoding;

        private final Optional<String> language;

        private final File source;

        private final ImmutableList<String> arguments;

        protected ImmutablePhantomScript(
                final Optional<String> encoding,
                final Optional<String> language,
                final File source,
                final ImmutableList<String> arguments
        ) {
            this.encoding = encoding;
            this.language = language;
            this.source = source;
            this.arguments = arguments;
        }

        @Override
        public Optional<String> encoding() {
            return encoding;
        }

        @Override
        public Optional<String> language() {
            return language;
        }

        @Override
        public File source() {
            return source;
        }

        @Override
        public ImmutableList<String> arguments() {
            return arguments;
        }

        @Override
        public String toString() {
            return String.format("%s %s", source(), arguments());
        }

    }

}
