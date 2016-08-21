package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.AbstractProcess;

/**
 * Given {@linkplain IExtractedFileSet extracted files} and a {@linkplain IPhantomProcessConfig Phantom process configuration}, generate a complete command for invocation by {@link PhantomProcess} ({@link AbstractProcess} by extension).
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 */
public interface IPhantomCommandFormatter {

    /**
     * @param distribution  Applicable platform detail.
     * @param files         Files available from extraction.
     * @param processConfig Supplies arguments for Phantom parameters.
     * @return Distinct parts of the command, immutable, consistent (every call returns the same parts give consistent arguments), and stable (parts are always in the same order).
     */
    ImmutableList<String> format(Distribution distribution, IExtractedFileSet files, IPhantomProcessConfig processConfig);

}
