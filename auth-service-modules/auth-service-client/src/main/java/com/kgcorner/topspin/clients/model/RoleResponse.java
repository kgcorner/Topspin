package com.kgcorner.topspin.clients.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class RoleResponse {
    private String authority;
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    public RoleResponse(){}
    public RoleResponse(String role) {
        this.authority = role;
    }

    public String getAuthority() {
        return authority;
    }
}