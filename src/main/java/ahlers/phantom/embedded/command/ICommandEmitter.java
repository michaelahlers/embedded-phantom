package ahlers.phantom.embedded.command;

import ahlers.phantom.embedded.PhantomConfig;
import ahlers.phantom.embedded.PhantomVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.io.IOException;
import java.util.List;

/**
 * Inspired by {{com.wix.mysql.distribution.service.ICommandEmitter}}.
 *
 * @see <a href="https://github.com/wix/wix-embedded-mysql">Wix Embedded MySQL</a>
 */
public interface ICommandEmitter {

    boolean matches(final PhantomVersion version);

    List<String> emit(final PhantomConfig config, final IExtractedFileSet files) throws IOException;

}
