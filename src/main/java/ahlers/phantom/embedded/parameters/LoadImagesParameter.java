package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum LoadImagesParameter
        implements IParameter {

    INSTANCE;

    public static LoadImagesParameter getInstance() {
        return INSTANCE;
    }

    @Override
    public ImmutableList<String> format(final IPhantomProcessConfig processConfig) {
        return usingTemplate("--load-images=%s", processConfig.loadImages());
    }
}
