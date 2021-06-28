package com.kgcorner.topspin.model;


import org.springframework.security.core.GrantedAuthority;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class RoleResponse extends com.kgcorner.topspin.clients.model.RoleResponse implements GrantedAuthority {
    public RoleResponse(String role) {
        super(role);
    }
}