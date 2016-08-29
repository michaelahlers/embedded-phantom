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
     * Generate a digest of the given {@link File} using this instance's {@linkplain #algorithm() algorithm}.
     */
    byte[] digest(File file) throws IOException;

}
