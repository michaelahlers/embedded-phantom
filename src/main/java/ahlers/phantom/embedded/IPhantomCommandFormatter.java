package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

/**
 * Inspired by {{com.wix.mysql.distribution.service.IPhantomCommandFormatter}}.
 *
 * @see <a href="https://github.com/wix/wix-embedded-mysql">Wix Embedded MySQL</a>
 */
public interface IPhantomCommandFormatter {

    ImmutableList<String> format(IExtractedFileSet files, IPhantomConfig config);

}
