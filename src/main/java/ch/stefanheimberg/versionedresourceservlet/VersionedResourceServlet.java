package ch.stefanheimberg.versionedresourceservlet;

import static ch.stefanheimberg.versionedresourceservlet.ChannelHelper.fastChannelCopy;
import static ch.stefanheimberg.versionedresourceservlet.VersionHelper.hasVersionInPath;
import static ch.stefanheimberg.versionedresourceservlet.VersionHelper.stripVersionFromPath;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        final ServletContext servletContext = request.getServletContext();

        final String path = getPath(request);

        // check if requested resource exists.
        final InputStream pathIS = servletContext.getResourceAsStream(path);
        if (null != pathIS) {
            LOGGER.log(Level.INFO, "-> path: {0} [found]", path);
            resourceFound(servletContext, response, path, pathIS);
            return;
        } else {
            LOGGER.log(Level.INFO, "-> path: {0} [not found]", path);
        }

        if (hasVersionInPath(path)) {
            // path not found. try to strip version from path
            final String stripedPath = stripVersionFromPath(path);

            // check if resource for striped path exists
            final InputStream stripedPathIS = servletContext.getResourceAsStream(stripedPath);
            if (null != stripedPathIS) {
                LOGGER.log(Level.INFO, "--> stripedPath: {0} [found]", stripedPath);
                resourceFound(servletContext, response, stripedPath, stripedPathIS);
                return;
            } else {
                LOGGER.log(Level.INFO, "--> stripedPath: {0} [not found]", stripedPath);
            }
        }

        resourceNotFound(response);
    }

    private String getPath(final HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    private void resourceFound(final ServletContext servletContext, final HttpServletResponse response, final String path, final InputStream pathIS) throws IOException {
        response.setStatus(200);

        final String contentType = servletContext.getMimeType(path);
        if (null != contentType) {
            response.setContentType(contentType);
        }
        
        fastChannelCopy(pathIS, response.getOutputStream());
    }

    private void resourceNotFound(final HttpServletResponse response) throws IOException {
        response.sendError(404);
    }

}
