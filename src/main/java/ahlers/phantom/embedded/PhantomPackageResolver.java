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
import static java.lang.String.format;

/**
 * Uses {{enum}} singleton pattern described by <em>Item 3</em> from <em>Effective Java</em>.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomPackageResolver
        implements IPackageResolver {

    INSTANCE;

    public static PhantomPackageResolver getInstance() {
        return INSTANCE;
    }

    @Override
    public ArchiveType getArchiveType(final Distribution distribution) {
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

    String platformClassifierFor(final Distribution distribution) {
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

    Optional<String> bitsizeClassifierFor(final Distribution distribution) {
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

    String archiveExtensionFor(final Distribution distribution, final ArchiveType archiveType) {
        switch (archiveType) {
            case TBZ2:
                return "tar.bz2";
            case ZIP:
                return "zip";
            default:
                throw new UnsupportedArchiveException(distribution, archiveType);
        }
    }

    @Override
    public String getPath(final Distribution distribution) {
        final String version = distribution.getVersion().asInDownloadPath();

        final String platformClassifier = platformClassifierFor(distribution);
        final Optional<String> bitsizeClassifier = bitsizeClassifierFor(distribution);
        final ArchiveType archiveType = getArchiveType(distribution);
        final String extension = archiveExtensionFor(distribution, archiveType);

        return format("phantomjs-%s-%s%s.%s", version, platformClassifier, bitsizeClassifier.isPresent() ? "-" + bitsizeClassifier.get() : "", extension);
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
