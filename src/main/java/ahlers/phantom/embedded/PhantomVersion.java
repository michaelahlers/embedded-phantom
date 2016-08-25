package ahlers.phantom.embedded;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import de.flapdoodle.embed.process.distribution.IVersion;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Defines supported PhantomJS versions.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomVersion
        implements IVersion {

    V211("2.1.1");

    public static final PhantomVersion LATEST = V211;

    private final String label;

    PhantomVersion(final String label) {
        this.label = label;
    }

    /**
     * @return A human-readable version number.
     */
    @Nonnull
    public String getLabel() {
        return label;
    }

    /**
     * @return Value of {@link #getLabel()}.
     */
    @Override
    public String asInDownloadPath() {
        return getLabel();
    }

    @Override
    public String toString() {
        return getLabel();
    }

    /**
     * @param label Lookup value for a version.
     * @return A matching {@link PhantomVersion}.
     * @throws IllegalArgumentException If the {@code label} is not known.
     */
    public static PhantomVersion byLabel(final String label) {
        for (final PhantomVersion version : values()) {
            if (label.equals(version.getLabel())) {
                return version;
            }
        }

        final Iterable<String> labels =
                Iterables.transform(EnumSet.allOf(PhantomVersion.class), new Function<PhantomVersion, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable final PhantomVersion input) {
                        return input.getLabel();
                    }
                });

        throw new IllegalArgumentException(String.format("No version matching \"%s\" (must be among: %s).", label, StringUtils.join(labels)));
    }

}
