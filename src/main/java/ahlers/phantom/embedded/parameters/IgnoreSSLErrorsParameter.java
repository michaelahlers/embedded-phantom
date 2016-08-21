package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum IgnoreSSLErrorsParameter
        implements IParameter {

    INSTANCE;

    public static IgnoreSSLErrorsParameter getInstance() {
        return INSTANCE;
    }

    /**
     * Emits a value for {@link IPhantomProcessConfig#ignoreSSLErrors()}.
     */
    @Override
    public ImmutableList<String> format(final Distribution distribution, final IPhantomProcessConfig processConfig) {
        return usingTemplate("--ignore-ssl-errors=%s", processConfig.ignoreSSLErrors());
    }
}
