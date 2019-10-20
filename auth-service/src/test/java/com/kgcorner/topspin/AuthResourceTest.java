package com.kgcorner.topspin;

/*
Description : Unit Tests for AuthResource class
Author: kumar
Created on : 16/10/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import com.kgcorner.topspin.service.RegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import  static org.powermock.api.mockito.PowerMockito.when;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.regex.Matcher;

@RunWith(PowerMockRunner.class)
public class AuthResourceTest {
    private AuthResource authResource;
    private Authenticator mockedAuthenticator;
    private RegistrationService mockedRegistrationService;

    @Before
    public void setup() {
        this.authResource = new AuthResource();
        this.mockedAuthenticator = PowerMockito.mock(Authenticator.class);
        this.mockedRegistrationService = PowerMockito.mock(RegistrationService.class);
        Whitebox.setInternalState(this.authResource, "authenticator", this.mockedAuthenticator);
        Whitebox.setInternalState(this.authResource, "registrationService", this.mockedRegistrationService);
    }

    @Test
    public void testGetToken() {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = new Token();
        tokenToBeReturned.setAccessToken(accessToken);
        tokenToBeReturned.setRefreshToken(refreshToken);
        tokenToBeReturned.setExpiresInSeconds(expireIn);
        when(mockedAuthenticator.authenticateWithToken(anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.getToken("Basic Token");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }

    @Test
    public void testHealth() {
        String response = authResource.getHealth();
        Assert.assertEquals("Response isn't matching", "Ok", response);
    }

    @Test
    public void testTokenForOAuth() {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = new Token();
        tokenToBeReturned.setAccessToken(accessToken);
        tokenToBeReturned.setRefreshToken(refreshToken);
        tokenToBeReturned.setExpiresInSeconds(expireIn);
        when(mockedAuthenticator.authenticateWithToken(anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.getTokenForOAuth("Auth Token");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }

    @Test
    public void testTokenForOAuthWIthRedirect() {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = new Token();
        tokenToBeReturned.setAccessToken(accessToken);
        tokenToBeReturned.setRefreshToken(refreshToken);
        tokenToBeReturned.setExpiresInSeconds(expireIn);
        when(mockedAuthenticator.authenticateWithCode(anyString(), anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.getTokenForOAuth("Auth Token", "redirect uri");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }

    @Test
    public void testResolveAccessToken() {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = new Token();
        tokenToBeReturned.setAccessToken(accessToken);
        tokenToBeReturned.setRefreshToken(refreshToken);
        tokenToBeReturned.setExpiresInSeconds(expireIn);
        when(mockedAuthenticator.resolveToken(anyString(), anyString(), anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.resolveAccessToken("Auth code", "redirect uri", "Server name");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }


    @Test
    public void testCreateLogin() {
        String username = "user";
        String password = "password";
        String userId = "userId";
        Login loginToBeReturend = new Login();
        loginToBeReturend.setUserName(username);
        loginToBeReturend.setPassword(password);
        loginToBeReturend.setUserId(userId);
        when(mockedRegistrationService.createLogin(username, password, userId)).thenReturn(loginToBeReturend);
        Login login = authResource.createLogin(username, password, userId);
        Assert.assertEquals("User name is not matching", username, login.getUserName());
        Assert.assertEquals("password is not matching", password, login.getPassword());
        Assert.assertEquals("User id is not matching", userId, login.getUserId());
        Assert.assertNull(login.getRefreshToken());
    }



}