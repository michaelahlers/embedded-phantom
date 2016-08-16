package ahlers.phantom.embedded.parameters;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class Parameters {

    private Parameters() {
    }

    public static <T> ImmutableList<String> usingTemplate(final String template, final Optional<T> value) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        if (value.isPresent()) {
            builder.add(String.format(template, value.get()));
        }

        return builder.build();
    }

}
