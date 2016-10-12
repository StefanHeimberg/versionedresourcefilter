package ch.stefanheimberg.versionedresourceservlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class ChannelHelper {

    public static void fastChannelCopy(final InputStream is, final OutputStream os) throws IOException {
        try (final ReadableByteChannel inputChannel = Channels.newChannel(is);
                final WritableByteChannel outputChannel = Channels.newChannel(os)) {
            fastChannelCopy(inputChannel, outputChannel);
        }
    }

    public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        // https://thomaswabner.wordpress.com/2007/10/09/fast-stream-copy-using-javanio-channels/
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();
            // write to the channel, may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    private ChannelHelper() {
    }

}
