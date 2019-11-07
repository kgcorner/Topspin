package com.kgcorner.topspin.service.google;

import com.kgcorner.topspin.service.google.GoogleConfigProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;


/*
Description : Tests for GoogleConfigProvider
Author: kumar
Created on : 06/11/19
*/

public class GoogleConfigProviderTest {

    private static final String GOOGLE_APP_KEY = "GOOGLE_APP_KEY";
    private static final String GOOGLE_APP_SECRET = "GOOGLE_APP_SECRET";
    private GoogleConfigProvider googleConfigProvider;
    private static final String TOKEN_EXCHANGE_URL = "https://accounts.google.com/o/oauth2/token";
    private static final String TOKEN_VALIDATION_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=%s";
    private static final String USER_INFO_URL="https://people.googleapis.com/v1/people/me?personFields=emailAddresses";

    @Before
    public void setup() {
        this.googleConfigProvider = new GoogleConfigProvider();
    }

    @Test
    public void getAppKey() {
        Assert.assertEquals("Google app key is not matching",
            System.getenv(GOOGLE_APP_KEY), googleConfigProvider.getAppKey());
    }

    @Test
    public void getSecretKey() {
        Assert.assertEquals("Google secret key is not matching",
            System.getenv(GOOGLE_APP_SECRET), googleConfigProvider.getSecretKey());
    }

    @Test
    public void getAccessTokenExchangeUrl() {
        Whitebox.setInternalState(googleConfigProvider, "googleAccessTokenExchangeUrl", TOKEN_EXCHANGE_URL);
        String url = googleConfigProvider.getAccessTokenExchangeUrl("redirect_uri", "auth_code");
        String expected = "https://accounts.google.com/o/oauth2/token";
        Assert.assertEquals("url is not matching", expected, url);
    }

    @Test
    public void getAccessTokenValidationUrl() {
        Whitebox.setInternalState(googleConfigProvider, "googleValidateAccessTokenUrl", TOKEN_VALIDATION_URL);
        String url = googleConfigProvider.getAccessTokenValidationUrl("access_token");
        String expected = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=access_token";
        Assert.assertEquals("url is not matching", expected, url);
    }

    @Test
    public void getUserInfoUrl() {
        Whitebox.setInternalState(googleConfigProvider, "googleUserInfoExchangeUrl", USER_INFO_URL);
        String url = googleConfigProvider.getUserInfoUrl("email,name", "access_token");
        String expected = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses";
        Assert.assertEquals("url is not matching", expected, url);
    }
}