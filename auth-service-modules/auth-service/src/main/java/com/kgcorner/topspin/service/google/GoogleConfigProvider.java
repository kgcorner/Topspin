package com.kgcorner.topspin.service.google;

/*
Description : OAuth COnfig provider for Google
Author: kumar
Created on : 28/10/19
*/

import com.kgcorner.topspin.service.OAuthConfigProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        return System.getenv(GOOGLE_APP_KEY);
    }

    @Override
    public String getSecretKey() {
        return System.getenv(GOOGLE_APP_SECRET);
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