package com.kgcorner.topspin.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Description : Contract for creating Requester
 * Author: kumar
 * Created on : 26/11/19
 */

public interface Requester extends UserDetails {

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    default String getPassword() {
        return null;
    }

    @Override
    default String getUsername() {
        return null;
    }

    @Override
    default boolean isAccountNonExpired() {
        return false;
    }

    @Override
    default boolean isAccountNonLocked() {
        return false;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    default boolean isEnabled() {
        return false;
    }
}