package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.Distribution;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class UnsupportedBitsizeException
        extends IllegalArgumentException {

    private final Distribution distribution;

    public UnsupportedBitsizeException(final Distribution distribution) {
        super(String.format("Bit size \"%s\" not available for distribution \"%s\".", distribution.getBitsize(), distribution));

        this.distribution = distribution;
    }

    public Distribution getDistribution() {
        return distribution;
    }

}
