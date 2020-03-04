package com.kgcorner.topspin.clients.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class Role {
    private String authority;

    public Role() {
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Role(String role) {
        this.authority = role;
    }

    public String getAuthority() {
        return authority;
    }
}