package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.IPackageResolver;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;

import static de.flapdoodle.embed.process.distribution.ArchiveType.TBZ2;
import static de.flapdoodle.embed.process.distribution.ArchiveType.ZIP;
import static de.flapdoodle.embed.process.distribution.Platform.Linux;

/**
 * Translates components of {@link Distribution} into literal components of PhantomJS's distributed files.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/download.html">PhantomJS: Download</a>
 */
public enum PhantomPackageResolver
        implements IPackageResolver {

    INSTANCE;

    public static PhantomPackageResolver getInstance() {
        return INSTANCE;
    }

    public static ArchiveType archiveTypeFor(final Distribution distribution) {
        switch (distribution.getPlatform()) {
            case Linux:
                return TBZ2;
            case OS_X:
            case Windows:
                return ZIP;
            default:
                throw new UnsupportedPlatformException(distribution);
        }
    }

    @Override
    public ArchiveType getArchiveType(final Distribution distribution) {
        return archiveTypeFor(distribution);
    }

    static String platformClassifierFor(final Distribution distribution) {
        switch (distribution.getPlatform()) {
            case Linux:
                return "linux";
            case OS_X:
                return "macosx";
            case Windows:
                return "windows";
            default:
                throw new UnsupportedPlatformException(distribution);
        }
    }

    static Optional<String> bitsizeClassifierFor(final Distribution distribution) {
        if (Linux == distribution.getPlatform()) {
            switch (distribution.getBitsize()) {
                case B32:
                    return Optional.of("i686");
                case B64:
                    return Optional.of("x86_64");
                default:
                    throw new UnsupportedBitsizeException(distribution);
            }
        } else {
            return Optional.absent();
        }
    }

    /**
     * Provides the leading filename portion.
     */
    public static String archivePrefixFor(final Distribution distribution) {
        final String version = distribution.getVersion().asInDownloadPath();
        final String platformClassifier = platformClassifierFor(distribution);
        final Optional<String> bitsizeClassifier = bitsizeClassifierFor(distribution);
        return String.format("phantomjs-%s-%s%s", version, platformClassifier, bitsizeClassifier.isPresent() ? "-" + bitsizeClassifier.get() : "");
    }

    public static String archiveExtensionFor(final Distribution distribution, final ArchiveType archiveType) {
        switch (archiveType) {
            case TBZ2:
                return "tar.bz2";
            case ZIP:
                return "zip";
            default:
                throw new UnsupportedArchiveException(distribution, archiveType);
        }
    }

    public static String archiveExtensionFor(final Distribution distribution) {
        final ArchiveType archiveType = archiveTypeFor(distribution);
        return archiveExtensionFor(distribution, archiveType);
    }

    /**
     * Combines the filename {@linkplain #archivePrefixFor(Distribution) prefix} and {@linkplain #archiveExtensionFor(Distribution) extension}.
     */
    public static String archiveFilenameFor(final Distribution distribution) {
        final String filename = archivePrefixFor(distribution);
        final String extension = archiveExtensionFor(distribution);
        return String.format("%s.%s", filename, extension);
    }

    /**
     * Provides a qualified, <em>relative</em> path to a bundled archive (which may be present, given proper dependencies).
     */
    public static String archivePathFor(final Distribution distribution) {
        return PhantomPackageResolver.class.getPackage().getName().replace('.', '/') +
                "/" + archiveFilenameFor(distribution);
    }

    /**
     * Provides the archive {@linkplain #archiveFilenameFor(Distribution) filename}, not to be confused with any qualified path.
     */
    @Override
    public String getPath(final Distribution distribution) {
        return archiveFilenameFor(distribution);
    }

    @Override
    public FileSet getFileSet(final Distribution distribution) {
        final FileSet.Builder builder = FileSet.builder();

        switch (distribution.getPlatform()) {
            case Linux:
            case OS_X:
                builder.addEntry(FileType.Executable, "phantomjs");
                break;
            case Windows:
                builder.addEntry(FileType.Executable, "phantomjs.exe");
                break;
            default:
                throw new UnsupportedPlatformException(distribution);
        }

        return builder.build();
    }
}
