package ch.stefanheimberg.versionedresourcefilter;

import static ch.stefanheimberg.versionedresourcefilter.VersionHelper.stripVersionFromPath;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@WebServlet(urlPatterns = {"/css/*", "/js/*", "/images/*"})
public class VersionedResourceServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VersionedResourceServlet.class.getSimpleName());

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String path = request.getRequestURI().substring(request.getContextPath().length());
        final String stripedPath = stripVersionFromPath(path);
        LOGGER.log(Level.INFO, "-> path: {0}", path);
        LOGGER.log(Level.INFO, "-> stripedPath: {0}", stripedPath);

        final ServletContext servletContext = request.getServletContext();
        final InputStream is = servletContext.getResourceAsStream(stripedPath);

        if (null == is) {
            response.sendError(404);
            LOGGER.log(Level.INFO, "-> resource not found!");
            return;
        }

        response.setStatus(200);
        response.setContentType(servletContext.getMimeType(stripedPath));

        try (final ReadableByteChannel inputChannel = Channels.newChannel(is);
                final WritableByteChannel outputChannel = Channels.newChannel(response.getOutputStream())) {
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

    @Override
    public void init(final ServletConfig servletConfig) throws ServletException {
        LOGGER.info("initialized");
    }

    @Override
    public void destroy() {
        LOGGER.info("destroyed");
    }

}
