package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.BigStringGenerator;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("oauth")
public class OAuthAuthentication implements AuthenticationService {

    @Autowired
    private List<OAuthService> oAuthServices;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    @Autowired
    private LoginPersistentLayer loginPersistentLayer;

    @Autowired
    private Properties properties;

    @Override
    public Token authenticateToken(String token) {
        throw new IllegalStateException("Not a basic or bearer authentication");
    }

    @Override
    public Token validateAccessTokenAndAuthenticate(String accessToken, String serverName)  {
        for(OAuthService service : oAuthServices) {
            if (service.getOAuthServiceName().equalsIgnoreCase(serverName)) {
                if(service.validateAccessToken(accessToken)) {
                    return createTokenUsingAccessToken(accessToken, service);
                } else {
                    throw new IllegalArgumentException("Access token  authentication failed by " + serverName);
                }
            }
        }

        return  null;
    }

    @Override
    public Token resolveAuthCodeAndAuthenticate(String code, String redirectUrl, String serverName)  {
        for(OAuthService service : oAuthServices) {
            if(service.getOAuthServiceName().equalsIgnoreCase(serverName)) {
                String accessToken = service.getAccessToken(code, redirectUrl);
                return createTokenUsingAccessToken(accessToken, service);
            }
        }
        return null;
    }

    private Token createTokenUsingAccessToken(String accessToken, OAuthService service)  {
        String userInfo = service.getUserInfo(accessToken);
        var login = service.createLoginObject(userInfo);
        var email = login.getUsername();
        login = loginPersistentLayer.getLogin(email);
        if(login == null)
            throw new ResourceNotFoundException("Can't find user with email:"+ email);
        var token = authServiceModelFactory.createNewToken();
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", email);
        claims.put("USER_ID", login.getUserId());
        var refreshToken = BigStringGenerator.generateBigString();
        login.setRefreshToken(refreshToken);
        var jwtToken = JwtUtility.createJWTToken(properties.getTokenSalt(), claims, properties.getTokenExpirationInSecond());
        loginPersistentLayer.update(login);
        token.setAccessToken(jwtToken);
        token.setRefreshToken(refreshToken);
        token.setExpiresInSeconds(properties.getTokenExpirationInSecond());
        return token;
    }
}