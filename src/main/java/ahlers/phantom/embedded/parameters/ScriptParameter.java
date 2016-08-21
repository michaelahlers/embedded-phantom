package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import ahlers.phantom.embedded.IPhantomScript;
import com.google.common.collect.ImmutableList;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * {@inheritDoc}
 *
 * @see IPhantomScript
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

        builder.addAll(usingTemplate("--script-encoding=%s", script.encoding()));
        builder.addAll(usingTemplate("--script-language=%s", script.language()));
        builder.add(script.source().getAbsolutePath());
        builder.addAll(script.arguments());

        return builder.build();
    }

    @Override
    public ImmutableList<String> format(final IPhantomProcessConfig processConfig) {
        if (processConfig.script().isPresent()) {
            return format(processConfig.script().get());
        }

        return ImmutableList.of();
    }

}
