package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import ahlers.phantom.embedded.PhantomVersion;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomAnyCommandEmitter
        implements ICommandEmitter {

    @Override
    public boolean matches(final PhantomVersion version) {
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
