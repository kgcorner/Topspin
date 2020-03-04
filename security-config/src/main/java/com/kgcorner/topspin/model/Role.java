package com.kgcorner.topspin.model;


import org.springframework.security.core.GrantedAuthority;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class Role extends com.kgcorner.topspin.clients.model.Role implements GrantedAuthority {
    public Role(String role) {
        super(role);
    }
}