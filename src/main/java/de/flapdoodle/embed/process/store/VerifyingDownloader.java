package de.flapdoodle.embed.process.store;

import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.distribution.Distribution;

import java.io.File;
import java.io.IOException;

/**
 * Verifies downloaded files (typically for integrity) before returning their references.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class VerifyingDownloader
        implements IDownloader {

    private final IDownloadVerifier verifier;

    private final IDownloader delegate;

    /**
     * @param verifier Verification strategy.
     * @param delegate Specific download strategy.
     */
    public VerifyingDownloader(final IDownloadVerifier verifier, final IDownloader delegate) {
        this.verifier = verifier;
        this.delegate = delegate;
    }

    @Override
    public String getDownloadUrl(final IDownloadConfig runtime, final Distribution distribution) {
        return delegate.getDownloadUrl(runtime, distribution);
    }

    @Override
    public File download(final IDownloadConfig runtime, final Distribution distribution) throws IOException {
        final File file = delegate.download(runtime, distribution);

        final boolean isValid;

        try {
            isValid = verifier.isValid(file);
        } catch (final Exception e) {
            throw new RuntimeException(String.format("Can't verify downloaded file \"%s\".", file.getAbsolutePath()), e);
        }

        if (!isValid) throw new InvalidDownloadException(file);
        return file;
    }

}
