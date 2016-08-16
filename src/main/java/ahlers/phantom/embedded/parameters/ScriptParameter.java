package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomConfig;
import ahlers.phantom.embedded.IPhantomScript;
import com.google.common.collect.ImmutableList;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum ScriptParameter
        implements IParameter {
    INSTANCE;

    public static ScriptParameter getInstance() {
        return INSTANCE;
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
