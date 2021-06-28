package com.kgcorner.topspin.models;

/*
Description : <Write class Description>
Author: kumar
Created on : 24/10/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.RoleModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DummyLogin implements Login {
    private String userName;
    private String password;
    private String refreshToken;
    private String userId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleModel r = new RoleModel("TEST");
        List<GrantedAuthority> objects = new ArrayList<>();
        objects.add(r);
        return objects;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DummyLogin) {
            return ((DummyLogin)obj).getUserId().equals(userId);
        }
        return false;
    }

    @Override
    public void setLoginProvider(String loginProvider) {

    }

    @Override
    public void setOAuthAccessToken(String oAuthAccessToken) {

    }

    @Override
    public String getLoginProvider() {
        return null;
    }

    @Override
    public String getOAuthAccessToken() {
        return null;
    }
}