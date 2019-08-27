package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

public class FacebookOAuthService implements OAuthService {
    public static final String FACEBOOK = "facebook";

    @Override
    public String getAccessToken(String code, String redirectUri) {
        return null;
    }

    @Override
    public boolean validateAccessToken(String token) {
        return false;
    }

    @Override
    public String getOAuthServiceName() {
        return FACEBOOK;
    }
}