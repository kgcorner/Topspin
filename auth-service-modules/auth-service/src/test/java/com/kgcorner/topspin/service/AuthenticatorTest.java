package com.kgcorner.topspin.service;

import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.models.DummyToken;
import com.sun.net.httpserver.BasicAuthenticator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;


/*
Description : Tests for Authenticator class
Author: kumar
Created on : 20/10/19
*/

@RunWith(PowerMockRunner.class)
public class AuthenticatorTest {

    private AuthenticationService mockedBearerAuthenticationService;
    private AuthenticationService mockedBasicAuthenticationService;
    private AuthenticationService mockedOAuthAuthenticationService;
    private Authenticator authenticator;

    @Before
    public void setup() {
        this.authenticator = new Authenticator();
        this.mockedBasicAuthenticationService = PowerMockito.mock(BasicTokenAuthentication.class);
        this.mockedBearerAuthenticationService = PowerMockito.mock(BearerTokenAuthentication.class);
        this.mockedOAuthAuthenticationService = PowerMockito.mock(OAuthAuthentication.class);
        Whitebox.setInternalState(this.authenticator,
            "bearerAuthenticationService", mockedBearerAuthenticationService);
        Whitebox.setInternalState(this.authenticator,
            "basicAuthenticationService", mockedBasicAuthenticationService);
        Whitebox.setInternalState(this.authenticator,
            "oAuthAuthenticationService", mockedOAuthAuthenticationService);
    }

    @Test
    public void testAuthenticateWithInvalidToken() {
        Token response = authenticator.authenticateWithToken("invalid_token");
        Assert.assertNull("Returned Token object is not null for invalid token", response);
    }

    @Test
    public void testAuthenticateWithBasicToken() {
        Token token = getDummyToken();
        when(mockedBasicAuthenticationService.authenticateToken(anyString())).thenReturn(token);
        Token response = authenticator.authenticateWithToken("Basic token");
        Assert.assertEquals("Access token is not matching", token.getAccessToken(),
            response.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
            response.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
            response.getExpiresInSeconds());
    }

    @Test
    public void testAuthenticateWithBearerToken() {
        Token token = getDummyToken();
        when(mockedBearerAuthenticationService.authenticateToken(anyString())).thenReturn(token);
        Token response = authenticator.authenticateWithToken("Bearer token");
        Assert.assertEquals("Access token is not matching", token.getAccessToken(),
            response.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
            response.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
            response.getExpiresInSeconds());
    }

    @Test
    public void testAuthenticateWithOAuthToken() {
        Token token = getDummyToken();
        when(mockedOAuthAuthenticationService.authenticateToken(anyString())).thenReturn(token);
        Token response = authenticator.authenticateWithToken("OAuth token");
        Assert.assertEquals("Access token is not matching", token.getAccessToken(),
            response.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
            response.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
            response.getExpiresInSeconds());
    }

    @Test
    public void testAuthenticateWithCode() {
        Token token = getDummyToken();
        when(mockedOAuthAuthenticationService.authenticateCode(anyString(), anyString())).thenReturn(token);
        Token response = authenticator.authenticateWithCode("OAuth token", "redirect uri");
        Assert.assertEquals("Access token is not matching", token.getAccessToken(),
            response.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
            response.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
            response.getExpiresInSeconds());
    }

    @Test
    public void testResolveToken() {
        Token token = getDummyToken();
        when(mockedOAuthAuthenticationService.resolveToken(anyString(), anyString(), anyString())).thenReturn(token);
        Token response = authenticator.resolveToken("OAuth token", "redirect uri",
            "server name");
        Assert.assertEquals("Access token is not matching", token.getAccessToken(),
            response.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
            response.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
            response.getExpiresInSeconds());
    }

    private Token getDummyToken() {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = createDummyToken(accessToken, refreshToken, expireIn);
        return tokenToBeReturned;
    }

    private Token createDummyToken(String accessToken, String refreshToken, int expireIn) {
        Token token = new DummyToken();
        token.setExpiresInSeconds(expireIn);
        token.setRefreshToken(refreshToken);
        token.setAccessToken(accessToken);
        return token;
    }
}
