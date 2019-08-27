package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.crypto.BigStringGenerator;
import com.kgcorner.crypto.Hasher;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.dao.AuthenticationDao;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import org.apache.commons.lang.NotImplementedException;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("basic")
public class BasicTokenAuthentication implements AuthenticationService {

    public static final String BASIC_ = "basic ";

    @Autowired
    private AuthenticationDao<Login> loginDao;

    @Autowired
    private Properties properties;

    @Override
    public Token authenticateToken(String token) {
        if(token == null || !token.toLowerCase().startsWith(BASIC_)) {
            throw new IllegalArgumentException("Not a valid basic token");
        }
        token = token.substring(BASIC_.length());
        token = new String(Base64.decode(token));
        String[] credential = token.split(":");
        if(credential.length != 2) {
            throw new IllegalArgumentException("Not a valid basic token");
        }
        Login login = loginDao.getByKey(Login.USER_NAME_COLUMN, credential[0], Login.class);
        if(login == null)
            return null;
        String hashedPassword = Hasher.getCrypt(credential[1], properties.getPasswordSalt());
        if(!login.getPassword().equals(hashedPassword)) {
            return null;
        }
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", login.getUserName());
        claims.put("USER_ID", login.getUserId()+"");
        String refreshToken = BigStringGenerator.generateBigString();
        login.setRefreshToken(refreshToken);
        loginDao.update(login);
        String accessToken = JwtUtility.createJWTToken(properties.getTokenSalt(), claims,
            properties.getTokenExpirationInSecond());
        Token authToken = new Token();
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiresInSeconds(properties.getTokenExpirationInSecond());
        return authToken;
    }

    @Override
    public Token authenticateCode(String code, String redirectUrl) {
        throw new NotImplementedException("Not an oauth authentication service");
    }
}