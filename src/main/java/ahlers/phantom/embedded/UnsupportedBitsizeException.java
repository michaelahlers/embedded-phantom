package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.Distribution;

import static java.lang.String.format;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class UnsupportedBitsizeException
        extends IllegalArgumentException {

    private final Distribution distribution;

    public UnsupportedBitsizeException(final Distribution distribution) {
        super(format("Bit size \"%s\" not available for distribution \"%s\".", distribution.getBitsize(), distribution));

        this.distribution = distribution;
    }

    public Distribution getDistribution() {
        return distribution;
    }

}
