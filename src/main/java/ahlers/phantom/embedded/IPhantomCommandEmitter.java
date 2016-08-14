package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

/**
 * Inspired by {{com.wix.mysql.distribution.service.IPhantomCommandEmitter}}.
 *
 * @see <a href="https://github.com/wix/wix-embedded-mysql">Wix Embedded MySQL</a>
 */
public interface IPhantomCommandEmitter {

    boolean matches(IVersion version);

    String executable(IExtractedFileSet files);

    ImmutableList<String> arguments(IPhantomConfig config);

    ImmutableList<String> scripts(Optional<IPhantomScript> script);

}
