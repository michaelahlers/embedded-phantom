package ahlers.phantom.embedded.arguments;

import ahlers.phantom.embedded.IPhantomConfig;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IArgument {

    boolean applies(IVersion version);

    ImmutableList<String> format(IPhantomConfig config);

}
