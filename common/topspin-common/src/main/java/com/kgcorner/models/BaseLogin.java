package com.kgcorner.models;


import org.springframework.util.Assert;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 10/03/20
 */

public abstract class BaseLogin {
    private String username;
    private String password;
    private String refreshToken;
    private String userId;
    private String loginProvider;
    private String oAuthAccessToken;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }


    public void setOAuthAccessToken(String oAuthAccessToken) {
        this.oAuthAccessToken = oAuthAccessToken;
    }


    public String getLoginProvider() {
        return loginProvider;
    }


    public String getOAuthAccessToken() {
        return oAuthAccessToken;
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        Assert.notNull(obj);
        Assert.isTrue(obj instanceof BaseLogin);
        return username.equals(((BaseLogin)obj).username);
    }
}