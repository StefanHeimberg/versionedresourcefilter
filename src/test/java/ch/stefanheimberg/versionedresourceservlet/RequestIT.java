package ch.stefanheimberg.versionedresourceservlet;

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
    public void test_notFound() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_not_found("css/test_not_found.css");
        assert_resource_not_found("css/%s/test_not_found.css");
        assert_resource_not_found("js/test_not_found.js");
        assert_resource_not_found("js/%s/test_not_found.js");
        assert_resource_not_found("images/test_not_found.gif");
        assert_resource_not_found("images/%s/test_not_found.gif");
    }

    @Test
    public void test_html() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("index.html", "text/html");
        assert_resource_not_found("%s/index.html");
    }

    @Test
    public void test_css() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("css/test.css", "text/css");
        assert_resource_found_and_mimetypecorrect("css/%s/test.css", "text/css");
    }

    @Test
    public void test_css2() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_not_found("css/test2.css");
        assert_resource_found_and_mimetypecorrect("css/1.2.3/test2.css", "text/css");
    }

    @Test
    public void test_js() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("js/test.js", "application/javascript");
        assert_resource_found_and_mimetypecorrect("js/%s/test.js", "application/javascript");
    }

    @Test
    public void test_gif() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/test.gif", "image/gif");
        assert_resource_found_and_mimetypecorrect("images/%s/test.gif", "image/gif");
    }

    @Test
    public void test_jpg() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/test.jpg", "image/jpeg");
        assert_resource_found_and_mimetypecorrect("images/%s/test.jpg", "image/jpeg");
    }

    @Test
    public void test_png() throws MalformedURLException, ProtocolException, IOException {
        assert_resource_found_and_mimetypecorrect("images/test.png", "image/png");
        assert_resource_found_and_mimetypecorrect("images/%s/test.png", "image/png");
    }

}
