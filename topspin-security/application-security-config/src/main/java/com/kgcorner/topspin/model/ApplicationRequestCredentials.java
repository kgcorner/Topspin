package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Description : Application Request credential keeps information about the
 * request header passed by consumer application
 * Author: kumar
 * Created on : 12/08/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestCredentials implements Authentication {
    private String applicationName;
    private String applicationKey;
    private String requestSentAt;
    private String hash;

    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_APPLICATION");
        return Collections.singleton(grantedAuthority);
    }

    @Override
    public Object getCredentials() {
        return this;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return applicationName;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        authenticated = b;
    }

    @Override
    public String getName() {
        return applicationName;
    }
}