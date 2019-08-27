package com.kgcorner.topspin.model;

/*
Description : Token Resource Object
Author: kumar
Created on : 25/08/19
*/

import java.io.Serializable;
import java.util.List;

public class Token implements Serializable {
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