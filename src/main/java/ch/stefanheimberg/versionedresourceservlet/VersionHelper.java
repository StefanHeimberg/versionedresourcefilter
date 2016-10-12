package ch.stefanheimberg.versionedresourceservlet;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class VersionHelper {
    
    private static final Logger LOGGER = Logger.getLogger(VersionHelper.class.getSimpleName());
    
    // http://java-regex-tester.appspot.com/
    private static final String VERSION_PATTERN = "/\\d+.\\d+.\\d+/";
    
    public static final String stripVersionFromPath(final String versionedPath) {
        final Pattern p = Pattern.compile(VERSION_PATTERN);
        final Matcher m = p.matcher(versionedPath);
        if(m.find()) {
            LOGGER.info("-> request uri has version number. removing version");
            return m.replaceAll("/");
        }
        return versionedPath;
    }
    
}
