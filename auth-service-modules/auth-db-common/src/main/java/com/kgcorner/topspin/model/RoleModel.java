package com.kgcorner.topspin.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/11/19
 */

public class RoleModel implements GrantedAuthority {
    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public RoleModel(String role) {
        this.authority = role;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}