package ahlers.phantom.embedded;

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

    @Override
    public String getPath(final Distribution distribution) {
        final String version = distribution.getVersion().asInDownloadPath();

        final String classifier;

        switch (distribution.getPlatform()) {
            case Linux:
                classifier = "linux";
                break;
            case OS_X:
                classifier = "macosx";
                break;
            case Windows:
                classifier = "windows";
                break;
            default:
                throw new UnsupportedPlatformException(distribution);
        }

        final String bitsize;

        if (Linux == distribution.getPlatform()) {
            switch (distribution.getBitsize()) {
                case B32:
                    bitsize = "i686";
                    break;
                case B64:
                    bitsize = "x86_64";
                    break;

                default:
                    throw new UnsupportedBitsizeException(distribution);

            }
        } else {
            bitsize = null;
        }

        final String extension;

        switch (getArchiveType(distribution)) {
            case TBZ2:
                extension = "tar.bz2";
                break;
            case ZIP:
                extension = "zip";
                break;
            default:
                throw new UnsupportedArchiveException(distribution, getArchiveType(distribution));
        }

        return format("phantomjs-%s-%s%s.%s", version, classifier, null == bitsize ? "" : "-" + bitsize, extension);
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
