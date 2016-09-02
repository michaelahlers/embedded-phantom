package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.store.Downloader;
import de.flapdoodle.embed.process.store.IDownloader;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Performs library-specific download processing, namely verifying downloaded file integrity.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PhantomDownloader
        implements IDownloader {

    private final IDownloader delegate;

    PhantomDownloader(final IDownloader delegate) {
        this.delegate = delegate;
    }

    public PhantomDownloader() {
        this(new Downloader());
    }

    @Override
    public String getDownloadUrl(final IDownloadConfig runtime, final Distribution distribution) {
        return delegate.getDownloadUrl(runtime, distribution);
    }

    protected IPhantomSignature signatureFor(final Distribution distribution) {
        return PhantomSignature.byDistribution(distribution);
    }

    @Override
    public final File download(final IDownloadConfig runtime, final Distribution distribution) throws IOException {
        final File file = delegate.download(runtime, distribution);
        final IPhantomSignature signature = signatureFor(distribution);

        final byte[] digest;

        try {
            digest = signature.digest(file);
        } catch (final Exception e) {
            throw new IllegalStateException(String.format("Can't digest downloaded file \"%s\".", file.getAbsolutePath()), e);
        }

        final boolean isValid = MessageDigest.isEqual(signature.digest(), digest);
        if (!isValid) throw new InvalidDownloadException(signature, file, digest);
        return file;
    }

}
