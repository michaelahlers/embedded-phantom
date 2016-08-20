package de.flapdoodle.embed.process.io.directories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Transient directories are important for testing, but there are cases where they break assumptions for artifact location. This {@link IDirectory} implementation will a generate random directory, but only do it once (returning the same {@link File} for every call to {@link StableTransientDirectory#asFile()}}).
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="https://github.com/michaelahlers/embedded-phantom/issues/22">Embedded Phantom: Issue 22</a>
 */
public class StableTransientDirectory
        implements IDirectory {

    private static final Logger logger = LoggerFactory.getLogger(StableTransientDirectory.class);

    private static final DateFormat timestampFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    static {
        timestampFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private final Path location;

    private StableTransientDirectory(final Path location) {
        this.location = location;

        logger.info("Initialized with location \"{}\".", location);
    }

    public StableTransientDirectory() {
        this(new UserHome(".embedded-phantom")
                .asFile()
                .toPath()
                .resolve("transients")
                .resolve(String.format("%s_%s", timestampFormat.format(new Date()), UUID.randomUUID()))
        );
    }

    public StableTransientDirectory withTail(final Path tail) {
        return new StableTransientDirectory(location.resolve(tail));
    }

    public StableTransientDirectory withTail(final String tail) {
        return withTail(Paths.get(tail));
    }

    @Override
    public File asFile() {
        if (Files.notExists(location)) {
            try {
                Files.createDirectories(location);
                logger.info("Created transient directory \"{}\".", location);
            } catch (final Throwable t) {
                throw new IllegalStateException(String.format("Error creating \"%s\".", location), t);
            }
        }

        final File file = location.toFile();
        logger.debug("Transient directory \"{}\" requested.", file);
        return file;
    }

    @Override
    public boolean isGenerated() {
        return true;
    }
}
