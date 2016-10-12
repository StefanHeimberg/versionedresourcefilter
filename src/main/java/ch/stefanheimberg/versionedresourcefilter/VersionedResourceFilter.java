package ch.stefanheimberg.versionedresourcefilter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@WebFilter(urlPatterns = {"/images/*", "/js/*", "/css/*"})
public class VersionedResourceFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(VersionedResourceFilter.class.getSimpleName());
    // http://java-regex-tester.appspot.com/
    private static final String VERSION_PATTERN = "/\\d+.\\d+.\\d+/";
    
    public static final String stripVersionFromUri(final String requestUri) {
        final Pattern p = Pattern.compile(VERSION_PATTERN);
        final Matcher m = p.matcher(requestUri);
        if(m.find()) {
            LOGGER.info("-> request uri has version number. removing version");
            return m.replaceAll("/");
        }
        return requestUri;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        LOGGER.info("filtering started");
        
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String requestURI = httpRequest.getRequestURI();
        LOGGER.log(Level.INFO, "-> requestURI: {0}", requestURI);
        LOGGER.log(Level.INFO, "-> striped: {0}", stripVersionFromUri(requestURI));
        
        chain.doFilter(request, response);
        LOGGER.info("filtering finished");
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOGGER.info("initialized");
    }

    @Override
    public void destroy() {
        LOGGER.info("destroyed");
    }

}
