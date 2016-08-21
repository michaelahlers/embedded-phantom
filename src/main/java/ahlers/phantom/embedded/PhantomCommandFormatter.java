package ahlers.phantom.embedded;

import ahlers.phantom.embedded.parameters.*;
import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.distribution.Distribution;
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

    private final ImmutableList<IParameter> parameters = ImmutableList.<IParameter>of(
            DebugParameter.getInstance(),
            RemoteDebuggerPortParameter.getInstance(),
            CookiesFileParameter.getInstance(),
            DiskCacheParameter.getInstance(),
            DiskCachePathParameter.getInstance(),
            IgnoreSSLErrorsParameter.getInstance(),
            LoadImagesParameter.getInstance(),
            LocalStoragePathParameter.getInstance(),
            LocalStorageQuotaParameter.getInstance(),
            LocalToRemoteURLAccessParameter.getInstance(),
            LocalURLAccessParameter.getInstance(),
            MaximumDiskCacheSizeParameter.getInstance(),
            OfflineStoragePathParameter.getInstance(),
            OfflineStorageQuotaParameter.getInstance(),
            OutputEncodingParameter.getInstance(),
            ProxyParameter.getInstance(),
            WebSecurityParameter.getInstance(),
            ScriptParameter.getInstance()
    );

    @Override
    public ImmutableList<String> format(final Distribution distribution, final IExtractedFileSet files, final IPhantomProcessConfig processConfig) {
        return format(parameters, distribution, files, processConfig);
    }

    static ImmutableList<String> format(
            final List<IParameter> parameters,
            final Distribution distribution,
            final IExtractedFileSet files,
            final IPhantomProcessConfig processConfig
    ) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        builder.add(files.executable().getAbsolutePath());

        for (final IParameter parameter : parameters) {
            builder.addAll(parameter.format(distribution, processConfig));
        }

        return builder.build();
    }

}
