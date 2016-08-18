package ahlers.phantom.embedded.proxies;

import com.google.common.base.Optional;
import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
import de.flapdoodle.embed.process.builder.TypedProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class ProxyBuilder
        extends AbstractBuilder<IProxy> {

    protected static final TypedProperty<String> HOST = TypedProperty.with("host", String.class);
    protected static final TypedProperty<Integer> PORT = TypedProperty.with("port", Integer.class);

    protected static final TypedProperty<IProxyType> TYPE = TypedProperty.with("type", IProxyType.class);
    protected static final TypedProperty<IProxyCredential> CREDENTIAL = TypedProperty.with("credential", IProxyCredential.class);

    public ProxyBuilder defaults() {
        return this;
    }

    protected IProperty<String> host() {
        return property(HOST);
    }

    public ProxyBuilder host(final String value) {
        host().set(value);
        return this;
    }

    protected IProperty<Integer> port() {
        return property(PORT);
    }

    public ProxyBuilder port(final Integer value) {
        port().set(value);
        return this;
    }

    protected IProperty<IProxyType> type() {
        return property(TYPE);
    }

    public ProxyBuilder type(final IProxyType value) {
        type().set(value);
        return this;
    }

    protected IProperty<IProxyCredential> credential() {
        return property(CREDENTIAL);
    }

    public ProxyBuilder credential(final IProxyCredential value) {
        credential().set(value);
        return this;
    }

    @Override
    public IProxy build() {
        final String host = get(HOST);
        final Optional<Integer> port = Optional.fromNullable(get(PORT, null));
        final Optional<IProxyType> type = Optional.fromNullable(get(TYPE, null));
        final Optional<IProxyCredential> credential = Optional.fromNullable(get(CREDENTIAL, null));

        return new ImmutableProxy(
                host,
                port,
                type,
                credential
        );
    }

    static class ImmutableProxy
            implements IProxy {

        private final String host;

        private final Optional<Integer> port;

        private final Optional<IProxyType> type;

        private final Optional<IProxyCredential> credential;

        public ImmutableProxy(
                final String host,
                final Optional<Integer> port,
                final Optional<IProxyType> type,
                final Optional<IProxyCredential> credential
        ) {
            this.host = host;
            this.port = port;
            this.type = type;
            this.credential = credential;
        }

        @Override
        public String host() {
            return host;
        }

        @Override
        public Optional<Integer> port() {
            return port;
        }

        @Override
        public Optional<IProxyType> type() {
            return type;
        }

        @Override
        public Optional<IProxyCredential> credential() {
            return credential;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

}
