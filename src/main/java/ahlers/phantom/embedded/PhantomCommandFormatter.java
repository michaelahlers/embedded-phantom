package ahlers.phantom.embedded;

import ahlers.phantom.embedded.parameters.DebugParameter;
import ahlers.phantom.embedded.parameters.IParameter;
import ahlers.phantom.embedded.parameters.ScriptParameter;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

import java.util.List;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public enum PhantomCommandFormatter
        implements IPhantomCommandFormatter {

    INSTANCE;

    public static PhantomCommandFormatter getInstance() {
        return INSTANCE;
    }

    private final ImmutableList<IParameter> arguments = ImmutableList.<IParameter>of(
            DebugParameter.getInstance(),
            ScriptParameter.getInstance()
    );

    @Override
    public ImmutableList<String> format(final IExtractedFileSet files, final IPhantomConfig config) {
        return format(arguments, files, config);
    }

    static ImmutableList<String> format(
            final List<IParameter> arguments,
            final IExtractedFileSet files,
            final IPhantomConfig config
    ) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        builder.add(files.executable().getAbsolutePath());

        for (final IParameter argument : arguments) {
            builder.addAll(argument.format(config));
        }

        return builder.build();
    }

}
