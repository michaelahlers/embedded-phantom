package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
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
        public List<String> emit(final PhantomConfig config, final IExtractedFileSet files) throws IOException {
            return ImmutableList
                    .<String>builder()
                    .add(format("--debug=%s", config.getDebug()))
                    .build();
        }
    },

    V21 {
        @Override
        public boolean matches(final IVersion version) {
            return PhantomVersion.V211 == version;
        }

        @Override
        public List<String> emit(PhantomConfig config, IExtractedFileSet exe) throws IOException {
            return ImmutableList
                    .<String>builder()
                    .addAll(Any.emit(config, exe))
                    .build();
        }
    };

    /**
     * {@link PhantomCommand#Any} is guaranteed to match.
     */
    private static IPhantomCommand valueFor(final IVersion version) {
        for (final IPhantomCommand emitter : values()) {
            if (emitter.matches(version)) {
                return emitter;
            }
        }

        return Any;
    }

}
