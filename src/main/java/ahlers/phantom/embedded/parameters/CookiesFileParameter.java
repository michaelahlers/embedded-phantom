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
public enum CookiesFileParameter
        implements IParameter {

    INSTANCE;

    public static CookiesFileParameter getInstance() {
        return INSTANCE;
    }

    @Override
    public ImmutableList<String> format(final IPhantomProcessConfig processConfig) {
        return usingTemplate("--cookies-file=%s", processConfig.cookiesFile().transform(new Function<File, String>() {
            @Nullable
            @Override
            public String apply(@Nullable final File input) {
                return input.getAbsolutePath();
            }
        }));
    }

}