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

public class Login  extends BaseLogin {
    private List<Role> roles;
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Login) {
            return ((Login)obj).getUserId().equals(getUserId());
        }
        return false;
    }

    public List<Role> getRoles() {
        if(roles == null) {
            return Collections.emptyList();
        }
        return roles;
    }

    public void addRole(String role) {
        Role roleObj = null;
        if(roles == null)
            roles = new ArrayList<>();
        if(Strings.isNullOrEmpty(role))
            return;
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