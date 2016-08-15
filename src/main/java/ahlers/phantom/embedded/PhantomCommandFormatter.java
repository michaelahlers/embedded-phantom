package ahlers.phantom.embedded;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.util.EnumSet;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomCommandFormatter
        implements IPhantomCommandFormatter {

    AnyVersion {
        @Override
        public boolean matches(final IVersion version) {
            return true;
        }

        @Override
        public String executable(final IExtractedFileSet files) {
            return files.executable().getAbsolutePath();
        }

        @Override
        public ImmutableList<String> arguments(final IPhantomConfig config) {
            return ImmutableList
                    .<String>builder()
                    .add(format("--debug=%s", config.debug()))
                    .build();
        }

        @Override
        public ImmutableList<String> scripts(final Optional<IPhantomScript> script) {
            return script
                    .transform(new Function<IPhantomScript, ImmutableList<String>>() {
                        @Override
                        public ImmutableList<String> apply(final IPhantomScript input) {
                            return ImmutableList
                                    .<String>builder()
                                    .add(input.source().getAbsolutePath())
                                    .addAll(input.arguments())
                                    .build();
                        }
                    })
                    .or(ImmutableList.<String>of());
        }
    },

    Version21 {
        @Override
        public boolean matches(final IVersion version) {
            return PhantomVersion.V211 == version;
        }

        @Override
        public String executable(final IExtractedFileSet files) {
            return AnyVersion.executable(files);
        }

        @Override
        public ImmutableList<String> arguments(final IPhantomConfig config) {
            return ImmutableList
                    .<String>builder()
                    .addAll(AnyVersion.arguments(config))
                    .build();
        }

        @Override
        public ImmutableList<String> scripts(final Optional<IPhantomScript> script) {
            return AnyVersion.scripts(script);
        }
    };

    private static <T> String format(final String template, final Optional<T> value) {
        return value
                .transform(new Function<T, String>() {
                    @Override
                    public String apply(final T input) {
                        return String.format(template, input);
                    }
                })
                .or("");
    }

    /**
     * {@link PhantomCommandFormatter#AnyVersion} is guaranteed to match.
     */
    public static IPhantomCommandFormatter getInstance(final IVersion version) {
        for (final IPhantomCommandFormatter emitter : EnumSet.complementOf(EnumSet.of(AnyVersion))) {
            if (emitter.matches(version)) {
                return emitter;
            }
        }

        return AnyVersion;
    }

    public static IPhantomCommandFormatter getInstance(final Distribution distribution) {
        return getInstance(distribution.getVersion());
    }

}
