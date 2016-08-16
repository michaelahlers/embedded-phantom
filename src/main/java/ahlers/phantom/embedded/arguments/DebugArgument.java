package ahlers.phantom.embedded.arguments;

import ahlers.phantom.embedded.IPhantomConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;

import static ahlers.phantom.embedded.arguments.Arguments.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum DebugArgument
        implements IArgument {

    INSTANCE;

    public static DebugArgument getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean applies(final IVersion version) {
        return true;
    }

    @Override
    public ImmutableList<String> format(final IPhantomConfig config) {
        return usingTemplate("--debug=%s", config.debug());
    }

}
