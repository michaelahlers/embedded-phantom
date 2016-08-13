package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * Uses {{enum}} singleton pattern described by <em>Item 3</em> from <em>Effective Java</em>.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomAnyCommandEmitter
        implements ICommandEmitter {

    INSTANCE;

    public static ICommandEmitter getInstance() {
        return INSTANCE;
    }

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
}
