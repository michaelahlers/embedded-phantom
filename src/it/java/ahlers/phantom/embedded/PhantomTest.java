package ahlers.phantom.embedded;

import com.google.common.collect.ImmutableList;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.Processors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
@RunWith(Parameterized.class)
public class PhantomTest {

    private final IVersion version;

    public PhantomTest(final IVersion version) {
        this.version = version;
    }

    @Test
    public void startAndReceiveCommands() throws Exception {

        final String message = "Hello, World!";

        final BlockingStreamProcessor outputProcessor = new BlockingStreamProcessor(message);

        final ProcessOutput processOutput =
                new ProcessOutput(
                        outputProcessor,
                        Processors.silent(),
                        Processors.silent()
                );

        final IRuntimeConfig runtimeConfig =
                new PhantomRuntimeConfigBuilder()
                        .defaults()
                        .processOutput(processOutput)
                        .build();

        final PhantomStarter starter = PhantomStarter.getInstance(runtimeConfig);

        final IPhantomProcessConfig processConfig =
                new PhantomProcessConfigBuilder()
                        .defaults()
                        .version(version)
                        .build();

        final PhantomExecutable executable = starter.prepare(processConfig);

        final PhantomProcess process = executable.start();

        final PrintWriter console = process.getStandardInput();

        console.println(String.format("console.log('%s');", message));
        console.flush();
        console.close();

        Assert.assertThat(outputProcessor.getOutput(), containsString(message));

        process.stop();

    }

    @Parameters(name = "{index}: version = {0}")
    public static List<Object[]> arguments() {
        final ImmutableList.Builder<Object[]> builder = ImmutableList.builder();

        for (final IVersion version : PhantomVersion.values()) {
            builder.add(new Object[]{version});
        }

        return builder.build();
    }

    private static class BlockingStreamProcessor
            implements IStreamProcessor {

        private final String message;

        private final AtomicReference<String> output = new AtomicReference<>();

        private final CountDownLatch latch = new CountDownLatch(1);

        BlockingStreamProcessor(final String message) {
            this.message = message;
        }

        @Override
        public void process(final String block) {
            if (block.contains(message)) {
                output.set(block);
                latch.countDown();
            }
        }

        @Override
        public void onProcessed() {
        }

        private String getOutput() throws Exception {
            latch.await();
            return output.get();
        }

    }

}
