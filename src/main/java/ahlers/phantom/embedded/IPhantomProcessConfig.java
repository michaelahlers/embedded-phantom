package ahlers.phantom.embedded;

import com.google.common.base.Optional;
import de.flapdoodle.embed.process.config.IExecutableProcessConfig;

/**
 * Command line options for PhantomJS.
 *
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IPhantomProcessConfig
        extends IExecutableProcessConfig {

    Optional<Boolean> debug();

    Optional<IPhantomScript> script();

}
