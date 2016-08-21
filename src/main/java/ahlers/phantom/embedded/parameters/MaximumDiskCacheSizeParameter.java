package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum MaximumDiskCacheSizeParameter
        implements IParameter {

    INSTANCE;

    public static MaximumDiskCacheSizeParameter getInstance() {
        return INSTANCE;
    }

    /**
     * Emits a value for {@link IPhantomProcessConfig#maximumDiskCacheSize()}.
     */
    @Override
    public ImmutableList<String> format(final Distribution distribution, final IPhantomProcessConfig processConfig) {
        return usingTemplate("--max-disk-cache-size=%s", processConfig.maximumDiskCacheSize());
    }

}
