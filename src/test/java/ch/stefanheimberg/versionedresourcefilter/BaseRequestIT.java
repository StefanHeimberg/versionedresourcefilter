package ch.stefanheimberg.versionedresourcefilter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
abstract class BaseRequestIT {

    private static final String BASE_REQUEST_URL = "http://localhost:8080/versionedresourcefilter/";

    protected void assert_resource_found_and_mimetypecorrect(final String resourcePath, final String expectedMimeType) throws MalformedURLException, ProtocolException, IOException {
        final URL url = new URL(BASE_REQUEST_URL + resourcePath);
        
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        
        final int responseCode = con.getResponseCode();
        assertThat(responseCode, is(200));
        
        final String contentType = con.getContentType();
        assertThat(contentType, is(expectedMimeType));
    }

}
