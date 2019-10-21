package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Token;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("bearer")
public class BearerTokenAuthentication implements AuthenticationService {
    public static final String BEARER_ = "bearer ";

    @Autowired
    private Properties properties;

    @Override
    public Token authenticateToken(String token) {
        if(token ==null || !token.toLowerCase().startsWith(BEARER_)) {
            throw new IllegalArgumentException("Not a valid bearer token");
        }
        token = token.substring(BEARER_.length());
        if(!JwtUtility.validateToken(properties.getTokenSalt(), token))
            return null;
        else {
            Token tempBearerToken = new Token();
            tempBearerToken.setAccessToken(token);
            return tempBearerToken;
        }
    }

    @Override
    public Token authenticateCode(String code, String redirectUrl) {
        throw new NotImplementedException("Not an oauth authentication service");
    }

    @Override
    public Token resolveToken(String token, String redirect_uri, String serverName) {
        throw new NotImplementedException("Not an oauth authentication service");
    }
}