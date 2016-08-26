package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.BitSize;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.distribution.Platform;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

import static de.flapdoodle.embed.process.distribution.Platform.*;
import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.Files.readAllLines;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomSignatures {

    INSTANCE;

    private final LazyInitializer<Map<Distribution, IPhantomSignature>> byDistribution =
            new LazyInitializer<Map<Distribution, IPhantomSignature>>() {

                @Override
                protected Map<Distribution, IPhantomSignature> initialize() throws ConcurrentException {
                    final List<Distribution> distributions = new LinkedList<>();

                    for (final IVersion version : PhantomVersion.values()) {
                        for (final Platform platform : EnumSet.of(Linux, OS_X, Windows)) {
                            for (final BitSize bitsize : BitSize.values()) {
                                distributions.add(new Distribution(version, platform, bitsize));
                            }
                        }
                    }

                    try {
                        final Map<String, byte[]> digestByName = parseDigests(getClass().getResource("/ahlers/phantom/embedded/SHA256SUMS").toURI());
                        final Map<Distribution, IPhantomSignature> signatureByDistribution = new HashMap<>();

                        for (final Distribution distribution : distributions) {
                            final String name = PhantomPackageResolver.archivePathFor(distribution);
                            final byte[] digest = digestByName.get(name);

                            if (null == digest) continue;

                            final IPhantomSignature signature = new ImmutablePhantomSignature(distribution, "SHA-256", digest);
                            signatureByDistribution.put(distribution, signature);
                        }

                        return signatureByDistribution;
                    } catch (final Throwable t) {
                        throw new ConcurrentException("Couldn't initialize signatures.", t);
                    }
                }

            };

    public static PhantomSignatures getInstance() {
        return INSTANCE;
    }

    private static Map<String, byte[]> parseDigests(final URI source) throws IOException {
        final List<String> lines = readAllLines(Paths.get(source), defaultCharset());
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

    private static class ImmutablePhantomSignature
            implements IPhantomSignature {

        private final Distribution distribution;

        private final String algorithm;

        private final byte[] digest;

        private ImmutablePhantomSignature(final Distribution distribution, final String algorithm, final byte[] digest) {
            this.distribution = distribution;
            this.algorithm = algorithm;
            this.digest = digest;
        }

        @Override
        public Distribution distribution() {
            return distribution;
        }

        @Override
        public String algorithm() {
            return algorithm;
        }

        @Override
        public byte[] digest() {
            return digest;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(distribution)
                    .append(algorithm)
                    .append(digest)
                    .toHashCode();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;

            if (o == null) return false;
            if (!(o instanceof IPhantomSignature)) return false;

            final ImmutablePhantomSignature that = (ImmutablePhantomSignature) o;

            return new EqualsBuilder()
                    .append(distribution, that.distribution)
                    .append(algorithm, that.algorithm)
                    .append(digest, that.digest)
                    .isEquals();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("distribution", distribution)
                    .append("algorithm", algorithm)
                    .append("digest", new BigInteger(digest).toString(16))
                    .toString();
        }
    }

    public static IPhantomSignature byDistribution(final Distribution distribution) throws Exception {
        final IPhantomSignature signature = getInstance().byDistribution.get().get(distribution);

        if (null == signature) {
            throw new IllegalArgumentException(String.format("No signature available for \"%s\".", distribution));
        }

        return signature;
    }

}
