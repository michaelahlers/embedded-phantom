package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.File;

/**
 * Complements {@link IPhantomProcessConfig} in providing arguments to the command line interface, but only the portion dealing with external scripts.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IPhantomScript {

    Optional<String> encoding();

    Optional<String> language();

    File source();

    ImmutableList<String> arguments();

}
