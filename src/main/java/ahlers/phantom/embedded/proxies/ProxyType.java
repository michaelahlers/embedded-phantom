package ahlers.phantom.embedded.proxies;

/**
 * @see IProxy#type()
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum ProxyType
        implements IProxyType {

    HTTP,
    SOCKS5,
    NONE

}
