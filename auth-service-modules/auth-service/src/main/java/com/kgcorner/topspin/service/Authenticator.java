package com.kgcorner.topspin.service;

/*
Description : Authentication Service
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Authenticator {

    @Autowired
    @Qualifier("bearer")
    private AuthenticationService bearerAuthenticationService;

    @Autowired
    @Qualifier("basic")
    private AuthenticationService basicAuthenticationService;

    @Autowired
    @Qualifier("oauth")
    private AuthenticationService oAuthAuthenticationService;

    /**
     * Authenticate with token
     * @param token provided token
     * @return
     */
    public Token authenticateWithToken(String token) {
        if(token.contains(" ")){
            String[] tokens = token.split(" ");
            switch (tokens[0].toLowerCase()) {
                case "basic":
                    return basicAuthenticationService.authenticateToken(token);
                case "bearer":
                    return bearerAuthenticationService.authenticateToken(token);
                default:
                    return  oAuthAuthenticationService.authenticateToken(token);
            }
        }
        return null;
    }

    public Token validateAccessTokenAndAuthorize(String code, String serverName)  {
        return oAuthAuthenticationService.validateAccessTokenAndAuthenticate(code, serverName);
    }

    public Token resolveTokenAndAuthorize(String token, String redirectUri, String serverName)  {
        return oAuthAuthenticationService.resolveAuthCodeAndAuthenticate(token, redirectUri, serverName);
    }
}