package de.flapdoodle.embed.process.store;

import java.io.File;
import java.io.IOException;

/**
 * Signals a downloaded file failed verification.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public final class InvalidDownloadException extends IOException {

    private final File file;

    public InvalidDownloadException(final File file) {
        super(String.format("File \"%s\" failed validation.", file.getAbsolutePath()));
        this.file = file;
    }

    /**
     * Provides access to the offending file (for clean up, debugging, and so on).
     */
    public File getFile() {
        return file;
    }

}
