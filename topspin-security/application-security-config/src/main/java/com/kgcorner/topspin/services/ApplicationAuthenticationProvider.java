package com.kgcorner.topspin.services;


import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/08/21
 */

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ApplicationAuthenticationService applicationAuthenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof ApplicationRequestCredentials) {
            ApplicationRequestCredentials credentials = (ApplicationRequestCredentials) authentication;
            return applicationAuthenticationService.validateRequest(credentials.getName(),
                credentials.getHash(), credentials.getRequestSentAt());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ApplicationRequestCredentials.class.isAssignableFrom(aClass);
    }
}