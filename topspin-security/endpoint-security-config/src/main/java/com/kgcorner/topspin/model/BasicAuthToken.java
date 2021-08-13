package com.kgcorner.topspin.model;

/*
Description : Represents Basic token
Author: kumar
Created on : 3/2/19
*/

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthToken extends UsernamePasswordAuthenticationToken {

    public BasicAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BasicAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}