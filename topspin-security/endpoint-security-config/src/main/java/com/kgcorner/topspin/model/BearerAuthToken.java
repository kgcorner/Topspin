package com.kgcorner.topspin.model;

/*
Description : Represents Bearer AuthenticationToken
Author: kumar
Created on : 3/2/19
*/

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BearerAuthToken extends AbstractAuthenticationToken {


    private Object principle;

    public BearerAuthToken(Collection<? extends GrantedAuthority> authorities, Object token) {
        super(authorities);
        this.principle = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }
}