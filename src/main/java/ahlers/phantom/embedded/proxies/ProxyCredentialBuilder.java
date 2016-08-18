package ahlers.phantom.embedded.proxies;

import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
import de.flapdoodle.embed.process.builder.TypedProperty;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class ProxyCredentialBuilder
        extends AbstractBuilder<IProxyCredential> {

    protected static final TypedProperty<String> USERNAME = TypedProperty.with("username", String.class);
    protected static final TypedProperty<String> PASSWORD = TypedProperty.with("password", String.class);

    protected IProperty<String> username() {
        return property(USERNAME);
    }

    public ProxyCredentialBuilder username(final String value) {
        username().set(value);
        return this;
    }

    protected IProperty<String> password() {
        return property(PASSWORD);
    }

    public ProxyCredentialBuilder password(final String value) {
        password().set(value);
        return this;
    }

    @Override
    public IProxyCredential build() {
        final String username = get(USERNAME);
        final String password = get(PASSWORD);

        return new ImmutableProxyCredential(
                username,
                password
        );
    }

    static class ImmutableProxyCredential
            implements IProxyCredential {

        private final String username;

        private final String password;

        public ImmutableProxyCredential(
                final String username,
                final String password
        ) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String username() {
            return username;
        }

        @Override
        public String password() {
            return password;
        }

    }

}
