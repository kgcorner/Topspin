package com.kgcorner.topspin.clients.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class LoginModel {
    private String username;
    private String password;
    private String refreshToken;
    private String userId;
    private String loginProvider;
    private String oAuthAccessToken;
    private List<Role> roles;
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

    public List<Role> getRoles() {
        if(roles == null) {
            roles = new ArrayList<>();
            Role role = new Role(DEFAULT_ROLE);
            roles.add(role);
        }
        return roles;
    }

    public void addRole(String role) {
        Role roleObj = null;
        if(roles == null)
            roles = new ArrayList<>();
        if(role.startsWith("ROLE_")) {
            roleObj = new Role(role);
        }
        else {
            roleObj = new Role("ROLE_" + role);
        }
        this.roles.add(roleObj);

    }

    public Collection<Role> getAuthorities() {
        return getRoles();
    }
}