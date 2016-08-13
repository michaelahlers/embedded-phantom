package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

import static ahlers.phantom.embedded.PhantomVersion.V211;

/**
 * Uses {{enum}} singleton pattern described by <em>Item 3</em> from <em>Effective Java</em>.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomV21CommandEmitter
        implements ICommandEmitter {

    INSTANCE;

    public static ICommandEmitter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean matches(final IVersion version) {
        return V211 == version;
    }

    @Override
    public List<String> emit(PhantomConfig config, IExtractedFileSet exe) throws IOException {
        return ImmutableList
                .<String>builder()
                .addAll(PhantomAnyCommandEmitter.getInstance().emit(config, exe))
                .build();
    }
}
