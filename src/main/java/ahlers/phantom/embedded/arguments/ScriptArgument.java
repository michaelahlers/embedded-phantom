package ahlers.phantom.embedded.arguments;

import ahlers.phantom.embedded.IPhantomConfig;
import ahlers.phantom.embedded.IPhantomScript;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.sun.istack.internal.Nullable;
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

    @Override
    public ImmutableList<String> format(final IPhantomConfig config) {
        return config.script().transform(new Function<IPhantomScript, ImmutableList<String>>() {
            @Nullable
            @Override
            public ImmutableList<String> apply(final IPhantomScript input) {
                return ImmutableList
                        .<String>builder()
                        .add(input.source().getAbsolutePath())
                        .addAll(input.arguments())
                        .build();
            }
        }).or(ImmutableList.<String>of());
    }

}
