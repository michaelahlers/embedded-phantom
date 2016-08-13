package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;

import static java.lang.String.format;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class UnsupportedArchiveException
        extends IllegalArgumentException {

    private final Distribution distribution;

    private final ArchiveType archiveType;

    public UnsupportedArchiveException(final Distribution distribution, final ArchiveType archiveType) {
        super(format("Archive type \"%s\" not available for distribution \"%s\".", archiveType, distribution));

        this.distribution = distribution;
        this.archiveType = archiveType;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public ArchiveType getArchiveType() {
        return archiveType;
    }

}
