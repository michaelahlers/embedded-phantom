package de.flapdoodle.embed.process.store;

import java.io.File;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IDownloadVerifier {

    boolean isValid(File file);

}
