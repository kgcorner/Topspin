package com.kgcorner.topspin.stepdefs;

/*
Description : <Write class Description>
Author: kumar
Created on : 12/10/19
*/

public final class URLs {
    private final String HOST;
    private static final String TOKEN_URL = "/token";
    private static final String DEFAULT_HOST = "http://localhost:9001";
    private static final String FACEBOOK_CODE_LOGIN_URL = "/token/oauth/code";
    private static URLs INSTANCE;
    private URLs(){
        HOST = null;
    }
    private URLs(String host) {
        HOST = host;
    }

    public static URLs getInstance(String host) {
        if(INSTANCE == null) {
            INSTANCE = new URLs(host);
        }
        return INSTANCE;
    }

    public static URLs getInstance() {
        return getInstance(DEFAULT_HOST);
    }

    public String getTokenUrl() {
        return HOST + TOKEN_URL;
    }
    public String getFacebookCodeLoginUrl() {
        return HOST + FACEBOOK_CODE_LOGIN_URL;
    }
}