package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomConfig;
import com.google.common.collect.ImmutableList;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IParameter {

    ImmutableList<String> format(IPhantomConfig config);

}
