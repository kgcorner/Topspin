package com.kgcorner.topspin.service;

/*
Description : Implementation for basic token auth
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.BigStringGenerator;
import com.kgcorner.crypto.Hasher;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("basic")
public class BasicTokenAuthentication implements AuthenticationService {

    public static final String BASIC = "basic ";

    @Autowired
    private LoginPersistentLayer loginPersistentLayer;

    @Autowired
    private Properties properties;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    @Override
    public Token authenticateToken(String token) {
        if(token == null || !token.toLowerCase().startsWith(BASIC)) {
            throw new IllegalArgumentException("Not a valid basic token");
        }
        token = token.substring(BASIC.length());
        token = new String(Base64.decode(token));
        String[] credential = token.split(":");
        if(credential.length != 2) {
            throw new IllegalArgumentException("Not a valid basic token");
        }
        var login = loginPersistentLayer.getLogin(credential[0]);
        if(login == null)
            throw new  ForbiddenException("invalid username and password provided");
        if(!Hasher.checkPassword(credential[1], login.getPassword())) {
            throw new  ForbiddenException("invalid username and password provided");
        }
        Map<String, String> claims = new HashMap<>();
        claims.put(USER_NAME, login.getUsername());
        claims.put(USER_ID, login.getUserId()+"");
        Collection<? extends GrantedAuthority> authorities = login.getAuthorities();
        var rolesBuilder = new StringBuilder();
        for(GrantedAuthority authority : authorities) {
            rolesBuilder.append(authority.getAuthority()+",");
        }
        var roles = rolesBuilder.toString();
        roles = roles.substring(0, roles.length() -1);
        claims.put(ROLE, roles );
        var refreshToken = BigStringGenerator.generateBigString();
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

    @Override
    public Token validateAccessTokenAndAuthenticate(String code, String serverName) {
        throw new IllegalStateException("Not an oauth authentication service");
    }

    @Override
    public Token resolveAuthCodeAndAuthenticate(String token, String redirectUri, String serverName) {
        throw new IllegalStateException("Not an oauth authentication service");
    }
}