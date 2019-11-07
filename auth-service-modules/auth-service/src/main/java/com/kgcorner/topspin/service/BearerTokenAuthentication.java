package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("bearer")
public class BearerTokenAuthentication implements AuthenticationService {
    public static final String BEARER_ = "bearer ";

    @Autowired
    private Properties properties;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    @Override
    public Token authenticateToken(String token) {
        if(token ==null || !token.toLowerCase().startsWith(BEARER_)) {
            throw new IllegalArgumentException("Not a valid bearer token");
        }
        token = token.substring(BEARER_.length());
        if(!JwtUtility.validateToken(properties.getTokenSalt(), token))
            return null;
        else {
            Token tempBearerToken = authServiceModelFactory.createNewToken();
            tempBearerToken.setAccessToken(token);
            return tempBearerToken;
        }
    }

    @Override
    public Token validateAccessTokenAndAuthenticate(String code, String serverName) {
        throw new RuntimeException("Not an oauth authentication service");
    }

    @Override
    public Token resolveAuthCodeAndAuthenticate(String token, String redirect_uri, String serverName) {
        throw new RuntimeException("Not an oauth authentication service");
    }
}