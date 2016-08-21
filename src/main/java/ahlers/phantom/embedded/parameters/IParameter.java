package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomCommandFormatter;
import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;

/**
 * Converts arguments from {@link IPhantomProcessConfig} to one part (or many parts) of a complete Phantom command.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see IPhantomCommandFormatter
 * @see IPhantomProcessConfig
 */
public interface IParameter {

    /**
     * @param distribution Applicable platform detail.
     * @param processConfig Supplies arguments for Phantom parameters.
     * @return Distinct parts of the command, immutable, consistent (every call returns the same parts give consistent arguments), and stable (parts are always in the same order).
     */
    ImmutableList<String> format(Distribution distribution, IPhantomProcessConfig processConfig);

}
