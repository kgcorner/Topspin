package com.kgcorner.topspin.providers;


import com.kgcorner.topspin.model.BasicAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Description : Default basic token auth provider
 * Author: kumar
 * Created on : 02/12/19
 */
@Component
public class DefaultBasicTokenAuthenticationProvider extends DefaultTokenAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        BasicAuthToken basicAuthToken = (BasicAuthToken) authentication;
        String principle = basicAuthToken.getPrincipal().toString();
        String credentials = basicAuthToken.getCredentials().toString();
        String basicToken = new String(Base64.getEncoder().encode((principle+":"+credentials).getBytes()));
        return getAuthDetails(SCHEMES.BASIC +" " +basicToken, BasicAuthToken.class);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BasicAuthToken.class.isAssignableFrom(aClass);
    }
}