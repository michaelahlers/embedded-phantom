package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.Distribution;

import java.io.File;
import java.io.IOException;

/**
 * Defines fingerprints for upstream artifacts.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IPhantomSignature {

    /**
     * Convenience reference.
     */
    Distribution distribution();

    /**
     * Hash algorithm used to generate the {@link #digest()}.
     */
    String algorithm();

    /**
     * Digest of the file given an algorithm.
     */
    byte[] digest();

    /**
     * Indicates whether the given {@link File} has a digest matching this instance.
     */
    boolean verify(File file) throws IOException;

}
