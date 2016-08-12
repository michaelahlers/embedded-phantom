package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.Distribution;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class UnsupportedPlatformException
        extends IllegalArgumentException {

    private final Distribution distribution;

    public UnsupportedPlatformException(final Distribution distribution) {
        super(String.format("Platform \"%s\" not available for distribution \"%s\".", distribution.getPlatform(), distribution));

        this.distribution = distribution;
    }

    public Distribution getDistribution() {
        return distribution;
    }
}
