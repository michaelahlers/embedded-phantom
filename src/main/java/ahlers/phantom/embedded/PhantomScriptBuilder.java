package ahlers.phantom.embedded;

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

    protected static final TypedProperty<File> SOURCE = TypedProperty.with("source", File.class);

    private ImmutableList.Builder<String> arguments = ImmutableList.builder();

    @Override
    public IPhantomScript build() {
        return new ImmutablePhantomScript(
                source().get(),
                arguments().build()
        );
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

    static class ImmutablePhantomScript
            implements IPhantomScript {

        private final File source;

        private final ImmutableList<String> arguments;

        protected ImmutablePhantomScript(
                final File source,
                final ImmutableList<String> arguments
        ) {
            this.source = source;
            this.arguments = arguments;
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
