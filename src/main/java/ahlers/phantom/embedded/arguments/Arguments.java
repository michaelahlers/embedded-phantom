package ahlers.phantom.embedded.arguments;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.sun.istack.internal.Nullable;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class Arguments {

    private Arguments() {
    }

    public static <T> ImmutableList<String> usingTemplate(final String template, final Optional<T> value) {
        return value.transform(new Function<T, ImmutableList<String>>() {
            @Nullable
            @Override
            public ImmutableList<String> apply(final T input) {
                return ImmutableList.of(String.format(template, input));
            }
        }).or(ImmutableList.<String>of());
    }

}
