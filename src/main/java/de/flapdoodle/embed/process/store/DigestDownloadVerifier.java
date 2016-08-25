package de.flapdoodle.embed.process.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class DigestDownloadVerifier
        implements IDownloadVerifier {

    private final String algorithm;

    private final byte[] signature;

    public DigestDownloadVerifier(final String algorithm, final byte[] signature) {
        this.algorithm = algorithm;
        this.signature = signature;
    }

    @Override
    public boolean isValid(final File file) throws Exception {
        final MessageDigest hash = MessageDigest.getInstance(algorithm);

        final InputStream source = new FileInputStream(file);
        final byte[] buffer = new byte[1024];
        int length;

        while (source.available() != 0) {
            length = source.read(buffer);
            hash.update(buffer, 0, length);
        }

        return MessageDigest.isEqual(signature, hash.digest());

    }

}
