package com.kgcorner.topspin.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/11/19
 */

public class Role implements GrantedAuthority {
    private String authority;

    public Role(String role) {
        this.authority = role;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}