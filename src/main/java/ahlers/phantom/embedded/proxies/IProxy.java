package ahlers.phantom.embedded.proxies;

import com.google.common.base.Optional;

/**
 * Represents a proxy configuration.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IProxy {

    /**
     * Proxy host connection information.
     */
    String host();

    /**
     * Host's port.
     *
     * @see #host()
     */
    Optional<Integer> port();

    /**
     * Proxy type (<em>e.g.</em>, HTTP, SOCKS5).
     */
    Optional<IProxyType> type();

    /**
     * Authentication details.
     */
    Optional<IProxyCredential> credential();

}
