package ahlers.phantom.embedded;

import com.google.auto.value.AutoValue;
import de.flapdoodle.embed.process.distribution.Distribution;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.Files.readAllLines;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
@AutoValue
public abstract class PhantomSignature
        implements IPhantomSignature {

    @Override
    abstract public Distribution distribution();

    @Override
    abstract public String algorithm();

    @Override
    @SuppressWarnings("mutable")
    abstract public byte[] digest();

    @Override
    public byte[] digest(final File file) throws IOException {
        final InputStream stream = new FileInputStream(file);

        try {
            final MessageDigest digest = DigestUtils.getDigest(algorithm());
            DigestUtils.updateDigest(digest, stream);
            return digest.digest();
        } finally {
            stream.close();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("distribution", distribution())
                .append("algorithm", algorithm())
                .append("digest", new BigInteger(digest()).toString(16))
                .toString();
    }

    static Builder builder() {
        return new AutoValue_PhantomSignature.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder distribution(Distribution value);

        abstract Builder algorithm(String value);

        abstract Builder digest(byte[] value);

        abstract PhantomSignature build();
    }

    private static Map<String, byte[]> parseDigests(final Path source) throws IOException {
        final List<String> lines = readAllLines(source, defaultCharset());
        final Map<String, byte[]> byName = new HashMap<>();

        for (final String line : lines) {
            final String[] parts = line.split("\\s+");

            if (2 == parts.length) {
                final String name = parts[1].trim();
                final byte[] digest = new BigInteger(parts[0], 16).toByteArray();
                byName.put(name, digest);
            }
        }

        return byName;
    }

    private static Map<String, byte[]> parseDigests(final URI source) throws IOException {
        return parseDigests(Paths.get(source));
    }

    /* TODO: Make generic for other signature types as needed. */
    private static LazyInitializer<Map<String, byte[]>> digestByName =
            new LazyInitializer<Map<String, byte[]>>() {

                @Override
                protected Map<String, byte[]> initialize() throws ConcurrentException {
                    try {
                        return parseDigests(getClass().getResource("/ahlers/phantom/embedded/SHA256SUMS").toURI());
                    } catch (final Throwable t) {
                        throw new ConcurrentException(t);
                    }
                }

            };

    public static IPhantomSignature byDistribution(final Distribution distribution) {
        final String name = PhantomPackageResolver.archivePathFor(distribution);
        final byte[] digest;

        try {
            digest = digestByName.get().get(name);
        } catch (final Exception e) {
            throw new IllegalStateException("Couldn't load digests.", e.getCause());
        }

        if (null == digest) {
            throw new IllegalArgumentException(String.format("No signature available for \"%s\".", distribution));
        }

        return builder()
                .distribution(distribution)
                .algorithm("SHA-256")
                .digest(Arrays.copyOf(digest, digest.length))
                .build();
    }

}
