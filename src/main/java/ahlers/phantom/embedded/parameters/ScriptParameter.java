package ahlers.phantom.embedded.parameters;

import ahlers.phantom.embedded.IPhantomProcessConfig;
import ahlers.phantom.embedded.IPhantomScript;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;

import static ahlers.phantom.embedded.parameters.Parameters.usingTemplate;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 * @see IPhantomScript
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

    /**
     * Emits several values for {@link IPhantomProcessConfig#script()}: applicable {@link IPhantomScript#encoding()} and {@link IPhantomScript#language()} arguments, followed by an absolute path from {@link IPhantomScript#source()} and verbatim {@link IPhantomScript#arguments()}.
     */
    @Override
    public ImmutableList<String> format(final Distribution distribution, final IPhantomProcessConfig processConfig) {
        if (processConfig.script().isPresent()) {
            return format(processConfig.script().get());
        }

        return ImmutableList.of();
    }

}
