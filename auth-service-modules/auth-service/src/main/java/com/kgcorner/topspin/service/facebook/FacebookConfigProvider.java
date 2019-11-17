package com.kgcorner.topspin.service.facebook;

/*
Description : Implementation of Facebooh OAuth settings provider
Author: kumar
Created on : 15/09/19
*/

import com.kgcorner.topspin.service.OAuthConfigProvider;
import com.kgcorner.utils.EnvironmentVariableSanityChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FacebookConfigProvider implements OAuthConfigProvider {
    private static final String FACEBOOK_APP_KEY = "FACEBOOK_APP_KEY";
    private static final String FACEBOOK_APP_SECRET = "FACEBOOK_APP_SECRET";

    @Value("${facebook.token.exchange.url}")
    private String fbAccessTokenExchangeUrl;

    @Value("${facebook.validate.access_token.url}")
    private String fbValidateAccessTokenUrl;

    @Value("${facebook.user_info.url}")
    private String fbUserInfoExchangeUrl;

    @Override
    public String getAppKey() {
        String key = System.getenv(FACEBOOK_APP_KEY);
        Assert.isTrue(EnvironmentVariableSanityChecker.checkForFacebookAppKey(key),
            "Facebook app key failed in sanity check, Check for environment variable " + FACEBOOK_APP_KEY);
        return key;
    }

    @Override
    public String getSecretKey() {
        String key = System.getenv(FACEBOOK_APP_SECRET);
        Assert.isTrue(EnvironmentVariableSanityChecker.checkForFacebookSecretKey(key),
            "Facebook app secret failed in sanity check, Check for environment variable " + FACEBOOK_APP_SECRET);
        return key;
    }

    @Override
    public String getAccessTokenExchangeUrl(String redirectUri, String authCode) {
        return String.format(fbAccessTokenExchangeUrl, getAppKey(), redirectUri, getSecretKey(), authCode);
    }

    @Override
    public String getAccessTokenValidationUrl(String accessToken) {
        return String.format(fbValidateAccessTokenUrl, accessToken, accessToken);
    }

    @Override
    public String getUserInfoUrl(String permissions, String accessToken) {
        return String.format(fbUserInfoExchangeUrl, permissions, accessToken);
    }
}