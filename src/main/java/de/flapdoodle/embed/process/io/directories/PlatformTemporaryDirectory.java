package de.flapdoodle.embed.process.io.directories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * u
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class PlatformTemporaryDirectory
        implements IDirectory {

    private static final Logger logger = LoggerFactory.getLogger(PlatformTemporaryDirectory.class);

    private final IDirectory delegate;

    public PlatformTemporaryDirectory() {
        this.delegate = new TempDirInPlatformTempDir();
    }


    @Override
    public File asFile() {
        final File directory = delegate.asFile();
        logger.info("Requested temporary directory created at \"{0}\".", directory);
        return directory;
    }

    @Override
    public boolean isGenerated() {
        return delegate.isGenerated();
    }
}
