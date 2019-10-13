package com.kgcorner.topspin.stepdefs.model;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/


public class Login {
    private String userName;
    private String password;
    private String refreshToken;

    public static final String USER_NAME_COLUMN = "userName";
    private String userId;

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
}