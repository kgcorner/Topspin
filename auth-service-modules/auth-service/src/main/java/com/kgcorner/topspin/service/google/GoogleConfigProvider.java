package com.kgcorner.topspin.service.google;

/*
Description : OAuth COnfig provider for Google
Author: kumar
Created on : 28/10/19
*/

import com.kgcorner.topspin.service.OAuthConfigProvider;
import com.kgcorner.utils.EnvironmentVariableSanityChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GoogleConfigProvider implements OAuthConfigProvider {

    private static final String GOOGLE_APP_KEY = "GOOGLE_APP_KEY";
    private static final String GOOGLE_APP_SECRET = "GOOGLE_APP_SECRET";

    @Value("${google.token.exchange.url}")
    private String googleAccessTokenExchangeUrl;

    @Value("${google.validate.access_token.url}")
    private String googleValidateAccessTokenUrl;

    @Value("${google.user_info.url}")
    private String googleUserInfoExchangeUrl;

    @Override
    public String getAppKey() {
        String key = System.getenv(GOOGLE_APP_KEY);
        Assert.isTrue(EnvironmentVariableSanityChecker.checkForGoogleAppKey(key),
            "Google app key failed in sanity check, Check for environment variable " + GOOGLE_APP_KEY);
        return key;
    }

    @Override
    public String getSecretKey() {
        String key = System.getenv(GOOGLE_APP_SECRET);
        Assert.isTrue(EnvironmentVariableSanityChecker.checkForGoogleSecretKey(key),
            "Google app secret failed in sanity check, Check for environment variable " + GOOGLE_APP_SECRET);
        return key;
    }

    @Override
    public String getAccessTokenExchangeUrl(String redirectUri, String authCode) {
        return googleAccessTokenExchangeUrl;
    }

    @Override
    public String getAccessTokenValidationUrl(String accessToken) {
        return String.format(googleValidateAccessTokenUrl, accessToken);
    }

    @Override
    public String getUserInfoUrl(String permissions, String accessToken) {
        return googleUserInfoExchangeUrl;
    }
}