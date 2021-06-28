package com.kgcorner.topspin.model;

/*
Description : LoginModel for Login
Author: kumar
Created on : 26/08/19
*/

import com.kgcorner.models.BaseLogin;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginModel extends BaseLogin implements Login {
    private List<GrantedAuthority> roles;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Id
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LoginModel) {
            return ((LoginModel)obj).getUserId().equals(getUserId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public List<GrantedAuthority> getRoles() {
        if(roles == null) {
            roles = new ArrayList<>();
            var role = new RoleModel(DEFAULT_ROLE);
            roles.add(role);
        }
        return roles;
    }

    public void addRole(String role) {
        RoleModel roleObj = null;
        if(roles == null)
            roles = new ArrayList<>();
        if(role.startsWith("ROLE_")) {
            roleObj = new RoleModel(role);
        }
        else {
            roleObj = new RoleModel("ROLE_" + role);
        }
        this.roles.add(roleObj);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}