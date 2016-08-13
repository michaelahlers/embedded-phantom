package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

/**
 * Inspired by {{com.wix.mysql.distribution.service.IPhantomCommandEmitter}}.
 *
 * @see <a href="https://github.com/wix/wix-embedded-mysql">Wix Embedded MySQL</a>
 */
public interface IPhantomCommandEmitter {

    boolean matches(IVersion version);

    List<String> emit(IPhantomConfig config, IExtractedFileSet files) throws IOException;

}
