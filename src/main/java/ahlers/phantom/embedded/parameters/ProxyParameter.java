package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import ahlers.phantom.embedded.proxies.IProxy;
import ahlers.phantom.embedded.proxies.IProxyCredential;
import ahlers.phantom.embedded.proxies.IProxyType;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;
import static ahlers.phantom.embedded.proxies.ProxyType.*;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum ProxyParameter
        implements IParameter {

    INSTANCE;

    public static ProxyParameter getInstance() {
        return INSTANCE;
    }

    private ImmutableList<String> format(final IProxy proxy) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        final String host = proxy.host();
        final String port = proxy.port().isPresent() ? ":" + proxy.port().get() : "";

        builder.add(String.format("--proxy=%s", host + port));

        builder.addAll(usingTemplate("--proxy-type=%s", proxy.type().transform(new Function<IProxyType, String>() {
            @Nullable
            @Override
            public String apply(final IProxyType input) {
                if (HTTP == input) return "http";
                if (SOCKS5 == input) return "socks5";
                if (NONE == input) return "none";
                throw new IllegalArgumentException(String.format("Don't know how to format proxy type \"%s\".", input));
            }
        })));

        builder.addAll(usingTemplate("--proxy-auth=%s", proxy.credential().transform(new Function<IProxyCredential, String>() {
            @Nullable
            @Override
            public String apply(final IProxyCredential input) {
                return input.username() + ":" + input.password();
            }
        })));

        return builder.build();
    }

    @Override
    public ImmutableList<String> format(final IPhantomProcessConfig processConfig) {
        if (processConfig.proxy().isPresent()) {
            return format(processConfig.proxy().get());
        }

        return ImmutableList.of();
    }
}
