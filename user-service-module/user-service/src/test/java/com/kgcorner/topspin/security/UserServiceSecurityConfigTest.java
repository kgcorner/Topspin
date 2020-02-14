package com.kgcorner.topspin.security;

import com.kgcorner.utils.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/02/20
 */

public class UserServiceSecurityConfigTest {

    public UserServiceSecurityConfig config;

    @Before
    public void setUp() throws Exception {
        config = new UserServiceSecurityConfig();
    }

    @Test
    public void getPublicUser() {
        String publicUrl = "url1, url2";
        Whitebox.setInternalState(config, "publicUrls", publicUrl);
        testPublicUrl(publicUrl);
    }

    private void testPublicUrl(String url) {
        String[] response = config.getPublicUrl();
        testUrl(url, response);
    }

    private void testUrl(String url, String[] response) {
        String[] urls = null;
        if(Strings.isNullOrEmpty(url)) {
            urls = new String[]{};
        } else
            urls = url.split(",");
        Assert.assertEquals("url count is not matching", urls.length, response.length);
        for (int i = 0; i < urls.length; i++) {
            Assert.assertEquals("url is not matching", urls[i], response[i]);
        }
    }

    @Test
    public void getPublicUserFor1Url() {
        String publicUrl = "url1";
        Whitebox.setInternalState(config, "publicUrls", publicUrl);
        testPublicUrl(publicUrl);
    }

    @Test
    public void getPublicUserForNullUrl() {
        String publicUrl = null;
        Whitebox.setInternalState(config, "publicUrls", publicUrl);
        testPublicUrl(publicUrl);

    }

    @Test
    public void getAuthenticatedUrl() {
        String adminUrls = "url1, url2";
        String userUrls = "url3, url4";
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }

    @Test
    public void getAuthenticatedUrlFor1Urls() {
        String adminUrls = "url1";
        String userUrls = "url3";
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }

    @Test
    public void getAuthenticatedUrlForNullAdmin() {
        String adminUrls = null;
        String userUrls = "url3";
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }

    @Test
    public void getAuthenticatedUrlForNullUser() {
        String adminUrls = "user1";
        String userUrls = null;
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }

    @Test
    public void getAuthenticatedUrlForNullUrls() {
        String adminUrls = null;
        String userUrls = null;
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }

    @Test
    public void getAuthenticatedUrlForEmptyUrls() {
        String adminUrls = "";
        String userUrls = "";
        Whitebox.setInternalState(config, "adminUrls", adminUrls);
        Whitebox.setInternalState(config, "userUrls", userUrls);
        Map<String, String[]> response = config.getAuthenticatedUrl();
        Assert.assertNotNull(response);
        Assert.assertEquals("Authenticated url doesn't contain url for admin and user",
            2, response.size());
        Assert.assertNotNull("response doesn't contain urls for admin", response.get("ROLE_ADMIN"));

        Assert.assertNotNull("response doesn't contain urls for user", response.get("ROLE_USER"));
        testUrl(adminUrls, response.get("ROLE_ADMIN"));
        testUrl(userUrls, response.get("ROLE_USER"));
    }
}