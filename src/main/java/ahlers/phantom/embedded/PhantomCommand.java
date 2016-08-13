package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static java.lang.String.format;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomCommand
        implements IPhantomCommand {

    Any {
        @Override
        public boolean matches(final IVersion version) {
            return true;
        }

        @Override
        public List<String> emit(final IPhantomConfig config, final IExtractedFileSet files) throws IOException {
            return ImmutableList
                    .<String>builder()
                    .add(files.executable().getAbsolutePath())
                    .add(format("--debug=%s", config.debug()))
                    .build();
        }
    },

    V21 {
        @Override
        public boolean matches(final IVersion version) {
            return PhantomVersion.V211 == version;
        }

        @Override
        public List<String> emit(final IPhantomConfig config, final IExtractedFileSet files) throws IOException {
            return ImmutableList
                    .<String>builder()
                    .addAll(Any.emit(config, files))
                    .build();
        }
    };

    /**
     * {@link PhantomCommand#Any} is guaranteed to match.
     */
    public static IPhantomCommand valueFor(final IVersion version) {
        for (final IPhantomCommand emitter : EnumSet.complementOf(EnumSet.of(Any))) {
            if (emitter.matches(version)) {
                return emitter;
            }
        }

        return Any;
    }

    public static IPhantomCommand valueFor(final Distribution distribution) {
        return valueFor(distribution.getVersion());
    }

}
