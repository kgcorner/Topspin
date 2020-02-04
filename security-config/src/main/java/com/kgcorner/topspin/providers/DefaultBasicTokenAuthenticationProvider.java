package com.kgcorner.topspin.providers;


import com.kgcorner.topspin.model.BasicAuthToken;
import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import org.springframework.security.core.Authentication;

/**
 * Description : Default basic token auth provider
 * Author: kumar
 * Created on : 02/12/19
 */

public class DefaultBasicTokenAuthenticationProvider extends DefaultTokenAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        BasicAuthToken basicAuthToken = (BasicAuthToken) authentication;
        String bearerToken = basicAuthToken.getPrincipal().toString();
        return getAuthDetails(SCHEMES.BASIC +" " +bearerToken, BearerAuthToken.class);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BasicAuthToken.class.isAssignableFrom(aClass);
    }
}