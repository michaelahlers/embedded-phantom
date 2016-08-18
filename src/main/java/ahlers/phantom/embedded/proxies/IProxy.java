package ahlers.phantom.embedded.proxies;

import com.google.common.base.Optional;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IProxy {

    String host();

    Optional<Integer> port();

    IProxyType type();

    Optional<IProxyCredential> credential();

}
