package com.kgcorner.topspin.clients.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class Token {
    private String accessToken;
    private String refreshToken;
    private int expiresInSeconds;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(int expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }
}