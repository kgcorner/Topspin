package com.kgcorner.topspin.providers;


import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Description : Default bearer token auth implementation
 * Author: kumar
 * Created on : 26/11/19
 */

public class DefaultBearerTokenAuthenticationProvider extends DefaultTokenAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerAuthToken token = (BearerAuthToken) authentication;
        String bearerToken = token.getPrincipal().toString();
        return getAuthDetails(SCHEMES.BEARER + " " +bearerToken, BearerAuthToken.class);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BearerAuthToken.class.isAssignableFrom(aClass);
    }
}