package ahlers.phantom.embedded;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.IOException;

/**
 * Signals a downloaded file failed verification.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public final class InvalidDownloadException extends IOException {

    private final IPhantomSignature signature;

    private final File file;

    public InvalidDownloadException(final IPhantomSignature signature, final File file, final byte[] digest) {
        super(String.format("File \"%s\" failed validation (had digest %s, expected %s).",
                file.getAbsolutePath(),
                Hex.encodeHexString(digest),
                Hex.encodeHexString(signature.digest())
        ));

        this.signature = signature;
        this.file = file;
    }

    /**
     * Provides access to the {@link IPhantomSignature} used for validation.\
     */
    public IPhantomSignature getSignature() {
        return signature;
    }

    /**
     * Provides access to the offending file (for clean up, debugging, and so on).
     */
    public File getFile() {
        return file;
    }

}
