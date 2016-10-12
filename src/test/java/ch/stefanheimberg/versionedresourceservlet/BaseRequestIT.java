package ch.stefanheimberg.versionedresourceservlet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
abstract class BaseRequestIT {

    private static final String BASE_REQUEST_URL = "http://localhost:8080/versionedresourceservlet/";

    protected void assert_resource_found_and_mimetypecorrect(final String resourcePath, final String expectedMimeType) throws MalformedURLException, ProtocolException, IOException {
        final String versionedResourcePath = String.format(resourcePath, get_random_version());
        final URL url = new URL(BASE_REQUEST_URL + versionedResourcePath);
        
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        
        final int responseCode = con.getResponseCode();
        assertThat(versionedResourcePath, responseCode, is(200));
        
        final String contentType = con.getContentType();
        assertThat(versionedResourcePath, contentType, is(expectedMimeType));
    }
    
    protected void assert_resource_not_found(final String resourcePath) throws MalformedURLException, ProtocolException, IOException {
        final String versionedResourcePath = String.format(resourcePath, get_random_version());
        final URL url = new URL(BASE_REQUEST_URL + versionedResourcePath);
        
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        
        final int responseCode = con.getResponseCode();
        assertThat(versionedResourcePath, responseCode, is(404));
    }
    
    protected String get_random_version() {
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        
        return new StringBuilder()
                .append(tlr.nextInt(100, 200))
                .append(".")
                .append(tlr.nextInt(0, 10))
                .append(".")
                .append(tlr.nextInt(0, 999))
                .toString();
    }

}
