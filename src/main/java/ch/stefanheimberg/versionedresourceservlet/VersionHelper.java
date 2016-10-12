package ch.stefanheimberg.versionedresourceservlet;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class VersionHelper {
    
    private static String PATH_SEPARATOR = "/";
    
    // http://java-regex-tester.appspot.com/
    private static final String DEFAULT_VERSION_PATTERN = "\\d+.\\d+.\\d+";
    
    public static final boolean hasVersionInPath(final String path) {
        return hasVersionInPath(path, DEFAULT_VERSION_PATTERN);
    }
    
    public static final boolean hasVersionInPath(final String path, final String versionPattern) {
        return Pattern.compile(PATH_SEPARATOR + versionPattern + PATH_SEPARATOR)
                .matcher(path)
                .find();
    }
    
    public static final String stripVersionFromPath(final String path) {
        return stripVersionFromPath(path, DEFAULT_VERSION_PATTERN);
    }
    
    public static final String stripVersionFromPath(final String path, final String versionPattern) {
        if(hasVersionInPath(path)) {
            return path.replaceAll(PATH_SEPARATOR + versionPattern + PATH_SEPARATOR, PATH_SEPARATOR);
        }
        return path;
    }

    private VersionHelper() {
    }
    
}
