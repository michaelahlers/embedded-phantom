package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomCommandFormatter;
import ahlers.phantom.embedded.IPhantomProcessConfig;
import com.google.common.collect.ImmutableList;

/**
 * Subclasses convert arguments from {@link IPhantomProcessConfig} to one or many parts of a complete Phantom command.
 *
 * @see IPhantomCommandFormatter
 * @see IPhantomProcessConfig
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IParameter {

    /**
     * @param processConfig Supplies arguments for Phantom parameters.
     * @return Distinct parts of the command, immutable, consistent (every call returns the same parts give consistent arguments), and stable (parts are always in the same order).
     */
    ImmutableList<String> format(IPhantomProcessConfig processConfig);

}
