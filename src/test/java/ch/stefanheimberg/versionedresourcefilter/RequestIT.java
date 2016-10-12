package ch.stefanheimberg.versionedresourcefilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.junit.Test;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class RequestIT extends BaseRequestIT {

    @Test
    public void test_css() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("css/%s/test.css", "text/css");
    }

    @Test
    public void test_js() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("js/%s/test.js", "application/javascript");
    }

    @Test
    public void test_gif() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/%s/test.gif", "image/gif");
    }

    @Test
    public void test_jpg() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/%s/test.jpg", "image/jpeg");
    }

    @Test
    public void test_png() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/%s/test.png", "image/png");
    }

}
