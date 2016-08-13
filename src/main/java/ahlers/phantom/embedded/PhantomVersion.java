package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * Defines supported PhantomJS versions.
 *
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomVersion
        implements IVersion {

    V211("2.1.1");

    public static final PhantomVersion LATEST = V211;

    private final String name;

    PhantomVersion(final String name) {
        this.name = name;
    }

    @Override
    public String asInDownloadPath() {
        return name;
    }

}
