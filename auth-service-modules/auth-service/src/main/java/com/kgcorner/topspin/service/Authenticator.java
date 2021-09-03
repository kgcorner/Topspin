package com.kgcorner.topspin.service;

/*
Description : Authentication Service
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.BigStringGenerator;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private LoginPersistentLayer loginPersistentLayer;

    @Autowired
    private Properties properties;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

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

    public Token refreshToken(String token, String refreshToken) {
        String[] tokenParts = token.split(" ");
        if(tokenParts.length != 2 || !tokenParts[0].toLowerCase().equals("bearer")){
            throw new IllegalArgumentException("Invalid Access token");
        }
        token = tokenParts[1];
        String userName = JwtUtility.getClaim(AuthenticationService.USER_NAME, token);
        Login login = loginPersistentLayer.getLogin(userName);
        if(!login.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh Token");
        }
        Map<String, String> claims = new HashMap<>();
        claims.put(AuthenticationService.USER_NAME, login.getUsername());
        claims.put(AuthenticationService.USER_ID, login.getUserId()+"");
        Collection<? extends GrantedAuthority> authorities = login.getAuthorities();
        var rolesBuilder = new StringBuilder();
        for(GrantedAuthority authority : authorities) {
            rolesBuilder.append(authority.getAuthority()+",");
        }
        var roles = rolesBuilder.toString();
        roles = roles.substring(0, roles.length() -1);
        claims.put(AuthenticationService.ROLE, roles );
        refreshToken = BigStringGenerator.generateBigString();
        login.setRefreshToken(refreshToken);
        loginPersistentLayer.update(login);
        String accessToken = JwtUtility.createJWTToken(properties.getTokenSalt(), claims,
            properties.getTokenExpirationInSecond());
        var authToken = authServiceModelFactory.createNewToken();
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiresInSeconds(properties.getTokenExpirationInSecond());
        return authToken;
    }
}