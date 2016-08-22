package ahlers.phantom.embedded.processes;

import java.io.IOException;
import java.io.Writer;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public class WriterPhantomConsole
        implements IPhantomConsole {

    private final Writer writer;

    public WriterPhantomConsole(final Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(final String block) throws IOException {
        writer.write(block);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

}
