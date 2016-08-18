package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.io.File;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

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
        return usingTemplate("--disk-cache-path=%s", processConfig.diskCachePath().transform(new Function<File, String>() {
            @Nullable
            @Override
            public String apply(@Nullable final File input) {
                return input.getAbsolutePath();
            }
        }));
    }

}
