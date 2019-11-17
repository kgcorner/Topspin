package com.kgcorner.topspin.service.facebook;

import com.kgcorner.utils.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.when;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 21/10/19
*/

public class FacebookConfigProviderTest {
    private static final String FACEBOOK_APP_KEY = "FACEBOOK_APP_KEY";
    private static final String FACEBOOK_APP_SECRET = "FACEBOOK_APP_SECRET";
    private FacebookConfigProvider facebookConfigProvider;
    private static final String TOKEN_EXCHANGE_URL = "https://graph.facebook.com/v2.11/oauth/access_token?" +
        "client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";
    private static final String TOKEN_VALIDATION_URL = "https://graph.facebook.com/debug_token?" +
        "input_token=%s&access_token=%s";
    private static final String USER_INFO_URL="https://graph.facebook.com/me?fields=%s&access_token=%s";
    private static final String MOCKED_FB_APP_KEY = "098";
    private static final String MOCKED_FB_APP_SEC = "098";

    @Before
    public void setup() {
        this.facebookConfigProvider = new FacebookConfigProvider();
        if(Strings.isNullOrEmpty(System.getenv(FACEBOOK_APP_KEY))) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Map<String, String> environment = processBuilder.environment();  // Sensitive
            environment.put(FACEBOOK_APP_KEY, MOCKED_FB_APP_KEY);
        }

        if(Strings.isNullOrEmpty(System.getenv(FACEBOOK_APP_SECRET))) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Map<String, String> environment = processBuilder.environment();  // Sensitive
            environment.put(FACEBOOK_APP_SECRET, MOCKED_FB_APP_SEC);
        }
    }

    @Test
    public void getAppKey() {
        Assert.assertEquals("Facebook app key is not matching",
            System.getenv(FACEBOOK_APP_KEY), facebookConfigProvider.getAppKey());
    }

    @Test
    public void getSecretKey() {
        Assert.assertEquals("Facebook secret key is not matching",
            System.getenv(FACEBOOK_APP_SECRET), facebookConfigProvider.getSecretKey());
    }

    @Test
    public void getAccessTokenExchangeUrl() {
        Whitebox.setInternalState(facebookConfigProvider, "fbAccessTokenExchangeUrl", TOKEN_EXCHANGE_URL);
        String url = facebookConfigProvider.getAccessTokenExchangeUrl("redirect_uri", "auth_code");
        String expected = "https://graph.facebook.com/v2.11/oauth/access_token?" +
            "client_id="+System.getenv(FACEBOOK_APP_KEY)+"&redirect_uri=redirect_uri&client_secret="+System.getenv(FACEBOOK_APP_SECRET)+"&code=auth_code";
        Assert.assertEquals("url is not matching", expected, url);
    }

    @Test
    public void getAccessTokenValidationUrl() {
        Whitebox.setInternalState(facebookConfigProvider, "fbValidateAccessTokenUrl", TOKEN_VALIDATION_URL);
        String url = facebookConfigProvider.getAccessTokenValidationUrl("access_token");
        String expected = "https://graph.facebook.com/debug_token?input_token=access_token&access_token=access_token";
        Assert.assertEquals("url is not matching", expected, url);
    }

    @Test
    public void getUserInfoUrl() {
        Whitebox.setInternalState(facebookConfigProvider, "fbUserInfoExchangeUrl", USER_INFO_URL);
        String url = facebookConfigProvider.getUserInfoUrl("email,name", "access_token");
        String expected = "https://graph.facebook.com/me?fields=email,name&access_token=access_token";
        Assert.assertEquals("url is not matching", expected, url);
    }
}