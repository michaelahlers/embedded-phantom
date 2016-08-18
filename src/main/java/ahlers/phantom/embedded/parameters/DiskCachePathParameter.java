package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum DiskCachePathParameter
        implements IParameter {

    INSTANCE;

    public static DiskCachePathParameter getInstance() {
        return INSTANCE;
    }

    @Override
    public ImmutableList<String> format(final IPhantomProcessConfig processConfig) {
        return ImmutableList.of();
    }

}
