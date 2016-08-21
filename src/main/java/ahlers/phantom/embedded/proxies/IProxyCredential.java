package ahlers.phantom.embedded.proxies;

/**
 * Authentication details.
 *
 * @see IProxy#credential()
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IProxyCredential {

    String username();

    /**
     * Authentication secret.
     */
    String password();

}
