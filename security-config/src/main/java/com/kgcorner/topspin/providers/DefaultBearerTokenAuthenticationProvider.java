package com.kgcorner.topspin.providers;


import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Description : Default bearer token auth implementation
 * Author: kumar
 * Created on : 26/11/19
 */
@Component
public class DefaultBearerTokenAuthenticationProvider extends DefaultTokenAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) {
        BearerAuthToken token = (BearerAuthToken) authentication;
        String bearerToken = token.getPrincipal().toString();
        return getAuthDetails(SCHEMES.BEARER + " " +bearerToken, BearerAuthToken.class);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BearerAuthToken.class.isAssignableFrom(aClass);
    }
}