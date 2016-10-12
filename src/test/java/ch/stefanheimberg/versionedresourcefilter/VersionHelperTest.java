/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.stefanheimberg.versionedresourcefilter;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ch.stefanheimberg.versionedresourcefilter.VersionHelper.stripVersionFromPath;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class VersionHelperTest {
    
    @Test
    public void test() {
        assertThat(stripVersionFromPath("/versionedresourcefilter/js/test.js"), is("/versionedresourcefilter/js/test.js"));
        assertThat(stripVersionFromPath("/versionedresourcefilter/js/122.1.343/test.js"), is("/versionedresourcefilter/js/test.js"));
        
        assertThat(stripVersionFromPath("/versionedresourcefilter/css/test.css"), is("/versionedresourcefilter/css/test.css"));
        assertThat(stripVersionFromPath("/versionedresourcefilter/css/188.4.415/test.css"), is("/versionedresourcefilter/css/test.css"));
        
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/test.gif"), is("/versionedresourcefilter/images/test.gif"));
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/180.1.607/test.gif"), is("/versionedresourcefilter/images/test.gif"));
        
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/test.jpg"), is("/versionedresourcefilter/images/test.jpg"));
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/109.9.505/test.jpg"), is("/versionedresourcefilter/images/test.jpg"));
        
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/test.png"), is("/versionedresourcefilter/images/test.png"));
        assertThat(stripVersionFromPath("/versionedresourcefilter/images/113.5.96/test.png"), is("/versionedresourcefilter/images/test.png"));
    }
    
}
