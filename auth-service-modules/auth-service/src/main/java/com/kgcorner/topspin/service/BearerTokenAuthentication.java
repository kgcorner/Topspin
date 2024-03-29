package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.exception.UnAuthorizeException;
import com.kgcorner.topspin.exception.WrongDataException;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("bearer")
public class BearerTokenAuthentication implements AuthenticationService {
    public static final String BEARER = "bearer ";

    @Autowired
    private Properties properties;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    @Override
    public Token authenticateToken(String token) {
        if(token ==null || !token.toLowerCase().startsWith(BEARER)) {
            throw new WrongDataException("Not a valid bearer token");
        }
        token = token.substring(BEARER.length());
        if(!JwtUtility.validateToken(properties.getTokenSalt(), token))
            throw new UnAuthorizeException("invalid access token provided, it may be expired");
        else {
            var tempBearerToken = authServiceModelFactory.createNewToken();
            tempBearerToken.setAccessToken(token);
            return tempBearerToken;
        }
    }

    @Override
    public Token validateAccessTokenAndAuthenticate(String code, String serverName) {
        throw new IllegalStateException("Not an oauth authentication service");
    }

    @Override
    public Token resolveAuthCodeAndAuthenticate(String token, String redirectUri, String serverName) {
        throw new IllegalStateException("Not an oauth authentication service");
    }
}