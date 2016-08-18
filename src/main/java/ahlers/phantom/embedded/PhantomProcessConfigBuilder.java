package ahlers.phantom.embedded;

import ahlers.phantom.embedded.proxies.IProxy;
import com.google.common.base.Optional;
import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
import de.flapdoodle.embed.process.builder.TypedProperty;
import de.flapdoodle.embed.process.config.ExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;

import java.io.File;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomProcessConfigBuilder
        extends AbstractBuilder<IPhantomProcessConfig> {

    protected static final TypedProperty<IVersion> VERSION = TypedProperty.with("version", IVersion.class);

    protected static final TypedProperty<Boolean> DEBUG = TypedProperty.with("debug", Boolean.class);
    protected static final TypedProperty<Integer> REMOTE_DEBUGGER_PORT = TypedProperty.with("remote-debugging-port", Integer.class);

    protected static final TypedProperty<File> COOKIES_FILE = TypedProperty.with("cookies-file", File.class);

    protected static final TypedProperty<Boolean> DISK_CACHE = TypedProperty.with("disk-cache", Boolean.class);
    protected static final TypedProperty<File> DISK_CACHE_PATH = TypedProperty.with("disk-cache-path", File.class);

    protected static final TypedProperty<Long> MAXIMUM_DISK_CACHE_SIZE = TypedProperty.with("maximum-disk-cache-size", Long.class);

    protected static final TypedProperty<Boolean> IGNORE_SSL_ERRORS = TypedProperty.with("ignore-SSL-errors", Boolean.class);

    protected static final TypedProperty<Boolean> LOAD_IMAGES = TypedProperty.with("load-images", Boolean.class);
    protected static final TypedProperty<File> LOCAL_STORAGE_PATH = TypedProperty.with("local-storage-path", File.class);

    protected static final TypedProperty<Long> LOCAL_STORAGE_QUOTA = TypedProperty.with("local-storage-quota", Long.class);
    protected static final TypedProperty<Boolean> LOCAL_URL_ACCESS = TypedProperty.with("local-URL-access", Boolean.class);

    protected static final TypedProperty<Boolean> LOCAL_TO_REMOTE_URL_ACCESS = TypedProperty.with("local-to-remote-URL-access", Boolean.class);
    protected static final TypedProperty<File> OFFLINE_STORAGE_PATH = TypedProperty.with("offline-storage-path", File.class);

    protected static final TypedProperty<Long> OFFLINE_STORAGE_QUOTA = TypedProperty.with("offline-storage-quota", Long.class);
    protected static final TypedProperty<String> OUTPUT_ENCODING = TypedProperty.with("output-encoding", String.class);

    protected static final TypedProperty<IProxy> PROXY = TypedProperty.with("proxy", IProxy.class);

    protected static final TypedProperty<Boolean> WEB_SECURITY = TypedProperty.with("web-security", Boolean.class);

    protected static final TypedProperty<IPhantomScript> SCRIPT = TypedProperty.with("script", IPhantomScript.class);

    public PhantomProcessConfigBuilder defaults() {
        property(VERSION).setDefault(PhantomVersion.LATEST);

        return this;
    }

    protected IProperty<IVersion> version() {
        return property(VERSION);
    }

    public PhantomProcessConfigBuilder version(final IVersion value) {
        version().set(value);
        return this;
    }

    protected IProperty<Boolean> debug() {
        return property(DEBUG);
    }

    public PhantomProcessConfigBuilder debug(final Boolean value) {
        debug().set(value);
        return this;
    }

    protected IProperty<Integer> remoteDebuggerPort() {
        return property(REMOTE_DEBUGGER_PORT);
    }

    public PhantomProcessConfigBuilder remoteDebuggerPort(final Integer value) {
        remoteDebuggerPort().set(value);
        return this;
    }

    protected IProperty<File> cookiesFile() {
        return property(COOKIES_FILE);
    }

    public PhantomProcessConfigBuilder cookiesFile(final File value) {
        cookiesFile().set(value);
        return this;
    }

    protected IProperty<Boolean> diskCache() {
        return property(DISK_CACHE);
    }

    public PhantomProcessConfigBuilder diskCache(final Boolean value) {
        diskCache().set(value);
        return this;
    }

    protected IProperty<File> diskCachePath() {
        return property(DISK_CACHE_PATH);
    }

    public PhantomProcessConfigBuilder diskCachePath(final File value) {
        diskCachePath().set(value);
        return this;
    }

    protected IProperty<Long> maximumDiskCacheSize() {
        return property(MAXIMUM_DISK_CACHE_SIZE);
    }

    public PhantomProcessConfigBuilder maximumDiskCacheSize(final Long value) {
        maximumDiskCacheSize().set(value);
        return this;
    }

    protected IProperty<Boolean> ignoreSSLErrors() {
        return property(IGNORE_SSL_ERRORS);
    }

    public PhantomProcessConfigBuilder ignoreSSLErrors(final Boolean value) {
        ignoreSSLErrors().set(value);
        return this;
    }

    protected IProperty<Boolean> loadImages() {
        return property(LOAD_IMAGES);
    }

    public PhantomProcessConfigBuilder loadImages(final Boolean value) {
        loadImages().set(value);
        return this;
    }

    protected IProperty<File> localStoragePath() {
        return property(LOCAL_STORAGE_PATH);
    }

    public PhantomProcessConfigBuilder localStoragePath(final File value) {
        localStoragePath().set(value);
        return this;
    }

    protected IProperty<Long> localStorageQuota() {
        return property(LOCAL_STORAGE_QUOTA);
    }

    public PhantomProcessConfigBuilder localStorageQuota(final Long value) {
        localStorageQuota().set(value);
        return this;
    }

    protected IProperty<Boolean> localURLAccess() {
        return property(LOCAL_URL_ACCESS);
    }

    public PhantomProcessConfigBuilder localURLAccess(final Boolean value) {
        localURLAccess().set(value);
        return this;
    }

    protected IProperty<Boolean> localToRemoteURLAccess() {
        return property(LOCAL_TO_REMOTE_URL_ACCESS);
    }

    public PhantomProcessConfigBuilder localToRemoteURLAccess(final Boolean value) {
        localToRemoteURLAccess().set(value);
        return this;
    }

    protected IProperty<File> offlineStoragePath() {
        return property(OFFLINE_STORAGE_PATH);
    }

    public PhantomProcessConfigBuilder offlineStoragePath(final File value) {
        offlineStoragePath().set(value);
        return this;
    }

    protected IProperty<Long> offlineStorageQuota() {
        return property(OFFLINE_STORAGE_QUOTA);
    }

    public PhantomProcessConfigBuilder offlineStorageQuota(final Long value) {
        offlineStorageQuota().set(value);
        return this;
    }

    protected IProperty<String> outputEncoding() {
        return property(OUTPUT_ENCODING);
    }

    public PhantomProcessConfigBuilder outputEncoding(final String value) {
        outputEncoding().set(value);
        return this;
    }

    protected IProperty<IProxy> proxy() {
        return property(PROXY);
    }

    public PhantomProcessConfigBuilder proxy(final IProxy value) {
        proxy().set(value);
        return this;
    }

    protected IProperty<Boolean> webSecurity() {
        return property(WEB_SECURITY);
    }

    public PhantomProcessConfigBuilder webSecurity(final Boolean value) {
        webSecurity().set(value);
        return this;
    }

    protected IProperty<IPhantomScript> script() {
        return property(SCRIPT);
    }

    public PhantomProcessConfigBuilder script(final IPhantomScript value) {
        script().set(value);
        return this;
    }

    @Override
    public IPhantomProcessConfig build() {
        final IVersion version = get(VERSION);

        final Optional<Boolean> debug = Optional.fromNullable(get(DEBUG, null));
        final Optional<Integer> remoteDebuggerPort = Optional.fromNullable(get(REMOTE_DEBUGGER_PORT, null));
        final Optional<File> cookiesFile = Optional.fromNullable(get(COOKIES_FILE, null));
        final Optional<Boolean> diskCache = Optional.fromNullable(get(DISK_CACHE, null));
        final Optional<File> diskCachePath = Optional.fromNullable(get(DISK_CACHE_PATH, null));
        final Optional<Long> maximumDiskCacheSize = Optional.fromNullable(get(MAXIMUM_DISK_CACHE_SIZE, null));
        final Optional<Boolean> ignoreSSLErrors = Optional.fromNullable(get(IGNORE_SSL_ERRORS, null));
        final Optional<Boolean> loadImages = Optional.fromNullable(get(LOAD_IMAGES, null));
        final Optional<File> localStoragePath = Optional.fromNullable(get(LOCAL_STORAGE_PATH, null));
        final Optional<Long> localStorageQuota = Optional.fromNullable(get(LOCAL_STORAGE_QUOTA, null));
        final Optional<Boolean> localURLAccess = Optional.fromNullable(get(LOCAL_URL_ACCESS, null));
        final Optional<Boolean> localToRemoteURLAccess = Optional.fromNullable(get(LOCAL_TO_REMOTE_URL_ACCESS, null));
        final Optional<File> offlineStoragePath = Optional.fromNullable(get(OFFLINE_STORAGE_PATH, null));
        final Optional<Long> offlineStorageQuota = Optional.fromNullable(get(OFFLINE_STORAGE_QUOTA, null));
        final Optional<String> outputEncoding = Optional.fromNullable(get(OUTPUT_ENCODING, null));
        final Optional<IProxy> proxy = Optional.fromNullable(get(PROXY, null));
        final Optional<Boolean> webSecurity = Optional.fromNullable(get(WEB_SECURITY, null));
        final Optional<IPhantomScript> script = Optional.fromNullable(get(SCRIPT, null));

        return new ImmutablePhantomProcessConfig(
                version,
                debug,
                remoteDebuggerPort,
                cookiesFile,
                diskCache,
                diskCachePath,
                maximumDiskCacheSize,
                ignoreSSLErrors,
                loadImages,
                localStoragePath,
                localStorageQuota,
                localURLAccess,
                localToRemoteURLAccess,
                offlineStoragePath,
                offlineStorageQuota,
                outputEncoding,
                proxy,
                webSecurity,
                script
        );
    }

    static class ImmutablePhantomProcessConfig
            extends ExecutableProcessConfig
            implements IPhantomProcessConfig {

        private final Optional<Boolean> debug;

        private final Optional<Integer> remoteDebuggerPort;

        private final Optional<File> cookiesFile;

        private final Optional<Boolean> diskCache;

        private final Optional<File> diskCachePath;

        private final Optional<Long> maximumDiskCacheSize;

        private final Optional<Boolean> ignoreSSLErrors;

        private final Optional<Boolean> loadImages;

        private final Optional<File> localStoragePath;

        private final Optional<Long> localStorageQuota;

        private final Optional<Boolean> localURLAccess;

        private final Optional<Boolean> localToRemoteURLAccess;

        private final Optional<File> offlineStoragePath;

        private final Optional<Long> offlineStorageQuota;

        private final Optional<String> outputEncoding;

        private final Optional<IProxy> proxy;

        private final Optional<Boolean> webSecurity;

        private final Optional<IPhantomScript> script;

        protected ImmutablePhantomProcessConfig(
                final IVersion version,
                final Optional<Boolean> debug,
                final Optional<Integer> remoteDebuggerPort,
                final Optional<File> cookiesFile,
                final Optional<Boolean> diskCache,
                final Optional<File> diskCachePath,
                final Optional<Long> maximumDiskCacheSize,
                final Optional<Boolean> ignoreSSLErrors,
                final Optional<Boolean> loadImages,
                final Optional<File> localStoragePath,
                final Optional<Long> localStorageQuota,
                final Optional<Boolean> localURLAccess,
                final Optional<Boolean> localToRemoteURLAccess,
                final Optional<File> offlineStoragePath,
                final Optional<Long> offlineStorageQuota,
                final Optional<String> outputEncoding,
                final Optional<IProxy> proxy,
                final Optional<Boolean> webSecurity,
                final Optional<IPhantomScript> script
        ) {
            super(version, new ISupportConfig() {
                @Override
                public String getName() {
                    return "phantomjs";
                }

                @Override
                public String getSupportUrl() {
                    return "https://github.com/michaelahlers/embedded-phantom/issues";
                }

                @Override
                public String messageOnException(final Class<?> context, final Exception exception) {
                    return "";
                }
            });

            this.debug = debug;

            this.remoteDebuggerPort = remoteDebuggerPort;

            this.cookiesFile = cookiesFile;

            this.diskCache = diskCache;

            this.diskCachePath = diskCachePath;

            this.maximumDiskCacheSize = maximumDiskCacheSize;

            this.ignoreSSLErrors = ignoreSSLErrors;

            this.loadImages = loadImages;

            this.localStoragePath = localStoragePath;

            this.localStorageQuota = localStorageQuota;

            this.localURLAccess = localURLAccess;

            this.localToRemoteURLAccess = localToRemoteURLAccess;

            this.offlineStoragePath = offlineStoragePath;

            this.offlineStorageQuota = offlineStorageQuota;

            this.outputEncoding = outputEncoding;

            this.proxy = proxy;

            this.webSecurity = webSecurity;

            this.script = script;
        }

        @Override
        public Optional<Boolean> debug() {
            return debug;
        }

        @Override
        public Optional<Integer> remoteDebuggerPort() {
            return remoteDebuggerPort;
        }

        @Override
        public Optional<File> cookiesFile() {
            return cookiesFile;
        }

        @Override
        public Optional<Boolean> diskCache() {
            return diskCache;
        }

        @Override
        public Optional<File> diskCachePath() {
            return diskCachePath;
        }

        @Override
        public Optional<Long> maximumDiskCacheSize() {
            return maximumDiskCacheSize;
        }

        @Override
        public Optional<Boolean> ignoreSSLErrors() {
            return ignoreSSLErrors;
        }

        @Override
        public Optional<Boolean> loadImages() {
            return loadImages;
        }

        @Override
        public Optional<File> localStoragePath() {
            return localStoragePath;
        }

        @Override
        public Optional<Long> localStorageQuota() {
            return localStorageQuota;
        }

        @Override
        public Optional<Boolean> localURLAccess() {
            return localURLAccess;
        }

        @Override
        public Optional<Boolean> localToRemoteURLAccess() {
            return localToRemoteURLAccess;
        }

        @Override
        public Optional<File> offlineStoragePath() {
            return offlineStoragePath;
        }

        @Override
        public Optional<Long> offlineStorageQuota() {
            return offlineStorageQuota;
        }

        @Override
        public Optional<String> outputEncoding() {
            return outputEncoding;
        }

        @Override
        public Optional<IProxy> proxy() {
            return proxy;
        }

        @Override
        public Optional<Boolean> webSecurity() {
            return webSecurity;
        }

        @Override
        public Optional<IPhantomScript> script() {
            return script;
        }

    }

}
