package com.kgcorner.topspin.service;

import com.kgcorner.crypto.Hasher;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.dao.AuthenticationDao;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/*
Description : <Write class Description>
Author: kumar
Created on : 20/10/19
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest(JwtUtility.class)
public class BasicTokenAuthenticationTest {

    public static final String PASSWORD_SALT = "askdhajskldjskldjklasjdklasjdklasjdklasdaskdhajskldjskldjklasjdklasjdklas" +
        "jdklasdaskdasdasedashajskldjskldjklasjdklasjdklasjdklasdaskdhaj";
    public static final String TOKEN_SALT = "sjdklasjdklasjdklasdaskdhajskldjskldjklasjdkla";
    public static final int EXPIRATION_TIME = 5000;
    private AuthenticationDao<Login> mockedLoginDao;
    private BasicTokenAuthentication basicTokenAuthentication;
    private Properties mockedProperties;

    @Before
    public void setup() {
        this.basicTokenAuthentication = new BasicTokenAuthentication();
        this.mockedLoginDao = PowerMockito.mock(AuthenticationDao.class);
        this.mockedProperties = PowerMockito.mock(Properties.class);
        Whitebox.setInternalState(this.basicTokenAuthentication, "loginDao", mockedLoginDao);
        Whitebox.setInternalState(this.basicTokenAuthentication, "properties", mockedProperties);
    }

    @Test
    public void authenticateToken() {
        Login login = getDummyLogin();
        when(mockedLoginDao.getByKey(Login.USER_NAME_COLUMN, login.getUserName(), Login.class)).thenReturn(login);
        when(mockedProperties.getTokenSalt()).thenReturn(TOKEN_SALT);
        when(mockedProperties.getTokenExpirationInSecond()).thenReturn(EXPIRATION_TIME);
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", login.getUserName());
        claims.put("USER_ID", login.getUserId()+"");
        PowerMockito.mockStatic(JwtUtility.class);
        when(JwtUtility.createJWTToken(TOKEN_SALT, claims, EXPIRATION_TIME)).thenReturn("Access Token");
        Token token = this.basicTokenAuthentication.authenticateToken("Basic " + Base64.getEncoder()
            .encodeToString((login.getUserName()+":"+"password").getBytes()));
        Assert.assertNotNull("Returned token is null", token);
        Assert.assertNotNull("Returned access token is null", token.getAccessToken());
        Assert.assertEquals("Rexpired time is not matching", EXPIRATION_TIME, token.getExpiresInSeconds());
        Assert.assertNotNull("Returned refresh token is null", token.getRefreshToken());
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateTokenWithIncorrectToken() {
        this.basicTokenAuthentication.authenticateToken("invalid token");
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateTokenWithNonBasicToken() {
        this.basicTokenAuthentication.authenticateToken("Basic "+Base64.getEncoder().encodeToString("invalid token".getBytes()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateTokenWithNullToken() {
        this.basicTokenAuthentication.authenticateToken(null);
    }

    @Test
    public void authenticateTokenWithInvalidPassword() {
        Login login = getDummyLogin();
        when(mockedLoginDao.getByKey(Login.USER_NAME_COLUMN, login.getUserName(), Login.class)).thenReturn(login);
        when(mockedProperties.getTokenSalt()).thenReturn(TOKEN_SALT);
        when(mockedProperties.getTokenExpirationInSecond()).thenReturn(EXPIRATION_TIME);
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", login.getUserName());
        claims.put("USER_ID", login.getUserId()+"");
        PowerMockito.mockStatic(JwtUtility.class);
        when(JwtUtility.createJWTToken(TOKEN_SALT, claims, EXPIRATION_TIME)).thenReturn("Access Token");
        Token token = this.basicTokenAuthentication.authenticateToken("Basic " + Base64.getEncoder()
            .encodeToString((login.getUserName()+":"+"incorrect_password").getBytes()));
        Assert.assertNull("Returned token is not null for invalid password", token);
    }

    @Test
    public void authenticateTokenWithNonExtstingLogin() {
        Login login = getDummyLogin();
        when(mockedLoginDao.getByKey(Login.USER_NAME_COLUMN, login.getUserName(), Login.class)).thenReturn(null);

        Token token = this.basicTokenAuthentication.authenticateToken("Basic " + Base64.getEncoder()
            .encodeToString((login.getUserName()+":"+"password").getBytes()));
        Assert.assertNull("Returned token is not null for non existing user", token);
    }

    @Test(expected = NotImplementedException.class)
    public void authenticateCode() {
        this.basicTokenAuthentication.authenticateCode("auth code", "redirect uri");
    }

    @Test(expected = NotImplementedException.class)
    public void resolveToken() {
        this.basicTokenAuthentication.resolveToken("auth code", "redirect uri", "server name");
    }

    private Login getDummyLogin() {
        Login login = new Login();
        login.setUserId("XXX");
        login.setPassword(Hasher.getCrypt("password",
            PASSWORD_SALT));
        login.setUserName("user");
        login.setId("0");
        return login;
    }
}