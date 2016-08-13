package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import ahlers.phantom.embedded.PhantomVersion;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomV21CommandEmitter
        implements ICommandEmitter {

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
                .addAll(new PhantomAnyCommandEmitter().emit(config, exe))
                .build();
    }
}
