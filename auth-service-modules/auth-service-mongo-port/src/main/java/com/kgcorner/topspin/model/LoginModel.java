package com.kgcorner.topspin.model;

/*
Description : LoginModel for Login
Author: kumar
Created on : 26/08/19
*/

import org.springframework.data.annotation.Id;

public class LoginModel implements Login {
    private String userName;
    private String password;
    private String refreshToken;
    private String userId;
    private String loginProvider;
    private String oAuthAccessToken;

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    @Override
    public void setOAuthAccessToken(String oAuthAccessToken) {
        this.oAuthAccessToken = oAuthAccessToken;
    }

    @Override
    public String getLoginProvider() {
        return loginProvider;
    }

    @Override
    public String getOAuthAccessToken() {
        return oAuthAccessToken;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LoginModel) {
            return ((LoginModel)obj).getUserId().equals(userId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}