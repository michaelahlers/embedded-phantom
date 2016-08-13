package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.config.IExecutableProcessConfig;

/**
 * Command line options for PhantomJS.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see <a href="http://phantomjs.org/api/command-line.html"><em>Command Line Interface</em></a>
 */
public interface IPhantomConfig
        extends IExecutableProcessConfig {

    Boolean debug();

    PhantomConfigBuilder builder();

}
