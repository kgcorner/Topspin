package com.kgcorner.topspin.clients.model;


import com.kgcorner.models.BaseLogin;
import com.kgcorner.utils.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/03/20
 */

public class LoginResponse extends BaseLogin {
    private List<RoleResponse> roles;

    public List<RoleResponse> getRoles() {
        if(roles == null) {
            return Collections.emptyList();
        }
        return roles;
    }

    public void addRole(String role) {
        RoleResponse roleObj = null;
        if(roles == null)
            roles = new ArrayList<>();
        if(Strings.isNullOrEmpty(role))
            return;
        if(role.startsWith("ROLE_")) {
            roleObj = new RoleResponse(role);
        }
        else {
            roleObj = new RoleResponse("ROLE_" + role);
        }
        this.roles.add(roleObj);

    }

    public Collection<RoleResponse> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LoginResponse) {
            return ((LoginResponse)obj).getUserId().equals(getUserId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}