package group14;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used to handle multiple output streams
 */
public final class MultiOutputStream extends OutputStream {

    private List<OutputStream> streams;

    public MultiOutputStream(OutputStream out) {
        streams = new ArrayList<>();
        streams.add(out);
    }

    @Override
    public void write(int b) throws IOException {
        for (OutputStream s : streams) {
            s.write(b);
        }
    }

    public void add(OutputStream stream) {
        streams.add(stream);
    }

    @Override
    public void close() throws IOException {
        for (OutputStream s : streams) {
            s.close();
        }
    }
}
