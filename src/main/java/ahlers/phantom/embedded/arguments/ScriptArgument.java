package ahlers.phantom.embedded.arguments;

import ahlers.phantom.embedded.IPhantomConfig;
import ahlers.phantom.embedded.IPhantomScript;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum ScriptArgument
        implements IArgument {
    INSTANCE;

    public static ScriptArgument getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean applies(final IVersion version) {
        return true;
    }

    private ImmutableList<String> format(final IPhantomScript script) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        builder.add(script.source().getAbsolutePath());
        builder.addAll(script.arguments());

        return builder.build();
    }

    @Override
    public ImmutableList<String> format(final IPhantomConfig config) {
        if (config.script().isPresent()) {
            return format(config.script().get());
        }

        return ImmutableList.of();
    }

}
