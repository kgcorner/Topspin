package com.kgcorner.topspin.service.facebook;

/*
Description : Implementation of Facebooh OAuth settings provider
Author: kumar
Created on : 15/09/19
*/

import com.kgcorner.topspin.service.OAuthConfigProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FacebookConfigProvider implements OAuthConfigProvider {
    private static final String FACEBOOK_APP_KEY = "FACEBOOK_APP_KEY";
    private static final String FACEBOOK_APP_SECRET = "FACEBOOK_APP_KEY";

    @Value("${facebook.token.exchange.url}")
    private String fbAccessTokenExchangeUrl;

    @Value("${facebook.get.user.access.token.url}")
    private String fbUserAccessUrl;

    @Value("${facebook.user_info.url}")
    private String fbUserInfoExchangeUrl;

    @Override
    public String getAppKey() {
        return System.getenv(FACEBOOK_APP_KEY);
    }

    @Override
    public String getSecretKey() {
        return System.getenv(FACEBOOK_APP_SECRET);
    }

    @Override
    public String getAccessTokenExchangeUrl(String redirectUri, String authCode) {
        return String.format(fbAccessTokenExchangeUrl, getAppKey(), redirectUri, getSecretKey(), authCode);
    }

    @Override
    public String getAccessTokenValidationUrl(String accessToken) {
        return String.format(fbUserAccessUrl, accessToken, accessToken);
    }

    @Override
    public String getUserInfoUrl(String permissions, String accessToken) {
        return String.format(fbUserInfoExchangeUrl, permissions, accessToken);
    }
}