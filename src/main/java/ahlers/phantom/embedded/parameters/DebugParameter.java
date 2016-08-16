package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomConfig;
import com.google.common.collect.ImmutableList;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum DebugParameter
        implements IParameter {

    INSTANCE;

    public static DebugParameter getInstance() {
        return INSTANCE;
    }

    @Override
    public ImmutableList<String> format(final IPhantomConfig config) {
        return usingTemplate("--debug=%s", config.debug());
    }

}
