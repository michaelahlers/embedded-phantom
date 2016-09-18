package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.io.directories.PropertyOrPlatformTempDir;
import de.flapdoodle.embed.process.io.file.Files;
import de.flapdoodle.embed.process.store.Downloader;
import de.flapdoodle.embed.process.store.IDownloader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import static ahlers.phantom.embedded.PhantomPackageResolver.archivePathFor;

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

    public static Optional<InputStream> bundledArchiveFor(final Distribution distribution) {
        return Optional.fromNullable(PhantomDownloader.class.getResourceAsStream(archivePathFor(distribution)));
    }

    public static Optional<File> bundledArchiveFor(final IDownloadConfig downloadConfig, final Distribution distribution) throws IOException {
        final Optional<InputStream> source = bundledArchiveFor(distribution);

        if (source.isPresent()) {
            /* Copied from de.flapdoodle.embed.process.store.Downloader.download, but for good reason: this class aims for consistency with the core downloader (in this case, to which file a bundled archive from the classpath is extracted). */
            final File extracted = Files.createTempFile(PropertyOrPlatformTempDir.defaultInstance(), downloadConfig.getFileNaming()
                    .nameFor(downloadConfig.getDownloadPrefix(), "." + downloadConfig.getPackageResolver().getArchiveType(distribution)));

            FileUtils.copyInputStreamToFile(source.get(), extracted);

            return Optional.of(extracted);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public final File download(final IDownloadConfig downloadConfig, final Distribution distribution) throws IOException {

        final Optional<File> bundled = bundledArchiveFor(downloadConfig, distribution);

        final File file;

        /* Can't use com.google.common.base.Optional.or(com.google.common.base.Supplier<? extends T>) because of checked exceptions from IDownloader's interface. */
        if (bundled.isPresent()) {
            file = bundled.get();
        } else {
            file = delegate.download(downloadConfig, distribution);
        }

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
