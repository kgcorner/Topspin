package com.kgcorner.topspin.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/11/19
 */

public class Role implements GrantedAuthority {
    private String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}