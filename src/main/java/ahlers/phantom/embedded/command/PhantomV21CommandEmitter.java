package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import ahlers.phantom.embedded.PhantomVersion;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

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
    public boolean matches(final PhantomVersion version) {
        switch (version) {
            case V211:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<String> emit(PhantomConfig config, IExtractedFileSet exe) throws IOException {
        return ImmutableList
                .<String>builder()
                .addAll(PhantomAnyCommandEmitter.getInstance().emit(config, exe))
                .build();
    }
}
