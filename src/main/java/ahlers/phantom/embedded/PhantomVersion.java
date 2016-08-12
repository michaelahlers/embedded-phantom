package ahlers.phantom.embedded;

import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author [[mailto:michael@ahlers.co Michael Ahlers]]
 */
public enum PhantomVersion implements IVersion {

    V2_1_1("2.1.1");

    private final String name;

    PhantomVersion(final String name) {
        this.name = name;
    }

    @Override
    public String asInDownloadPath() {
        return name;
    }

}
