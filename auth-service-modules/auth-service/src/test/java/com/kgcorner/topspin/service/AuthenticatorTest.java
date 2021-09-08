package com.kgcorner.topspin.service;

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.exception.UnAuthorizeException;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.models.DummyToken;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.*;


/*
Description : Tests for Authenticator class
Author: kumar
Created on : 20/10/19
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest({JwtUtility.class})
public class AuthenticatorTest {

    private AuthenticationService mockedBearerAuthenticationService;
    private AuthenticationService mockedBasicAuthenticationService;
    private AuthenticationService mockedOAuthAuthenticationService;
    private LoginPersistentLayer loginPersistentLayer;
    private Authenticator authenticator;
    private Properties properties;
    private AuthServiceModelFactory authServiceModelFactory;

    @Before
    public void setup() {
        this.authenticator = new Authenticator();
        this.mockedBasicAuthenticationService = PowerMockito.mock(BasicTokenAuthentication.class);
        this.mockedBearerAuthenticationService = PowerMockito.mock(BearerTokenAuthentication.class);
        this.mockedOAuthAuthenticationService = PowerMockito.mock(OAuthAuthentication.class);
        this.loginPersistentLayer = mock(LoginPersistentLayer.class);
        this.properties = mock(Properties.class);
        this.authServiceModelFactory = mock(AuthServiceModelFactory.class);
        Whitebox.setInternalState(this.authenticator,
            "bearerAuthenticationService", mockedBearerAuthenticationService);
        Whitebox.setInternalState(this.authenticator,
            "basicAuthenticationService", mockedBasicAuthenticationService);
        Whitebox.setInternalState(this.authenticator,
            "oAuthAuthenticationService", mockedOAuthAuthenticationService);
        Whitebox.setInternalState(this.authenticator,
            "loginPersistentLayer", loginPersistentLayer);
        Whitebox.setInternalState(this.authenticator,
            "properties", properties);
        Whitebox.setInternalState(this.authenticator,
            "authServiceModelFactory", authServiceModelFactory);
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
        try {
            Token token = getDummyToken();
            when(mockedOAuthAuthenticationService.validateAccessTokenAndAuthenticate(anyString(), anyString())).thenReturn(token);
            Token response = authenticator.validateAccessTokenAndAuthorize("OAuth token", "google");
            Assert.assertEquals("Access token is not matching", token.getAccessToken(),
                response.getAccessToken());
            Assert.assertEquals("Refresh token is not matching", token.getRefreshToken(),
                response.getRefreshToken());
            Assert.assertEquals("Expire time is not matching", token.getExpiresInSeconds(),
                response.getExpiresInSeconds());
        } catch (ResourceNotFoundException x) {
            Assert.fail(x.getMessage());
        }
    }

    @Test
    public void testResolveToken()  {
        Token token = getDummyToken();
        when(mockedOAuthAuthenticationService.resolveAuthCodeAndAuthenticate(anyString(), anyString(), anyString())).thenReturn(token);
        Token response = authenticator.resolveTokenAndAuthorize("OAuth token", "redirect uri",
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

    @Test
    public void refreshToken() {
        String token = "Bearer Token";
        String refreshToken = "Refresh Token";
        String userName = "userName";
        String userId = "userId";
        String salt = "salt";
        int exp = 10000;
        Login login = mock(Login.class);
        mockStatic(JwtUtility.class);
        when(JwtUtility.getClaim(AuthenticationService.USER_NAME, "Token")).thenReturn(userName);
        when(loginPersistentLayer.getLogin(userName)).thenReturn(login);
        when(login.getRefreshToken()).thenReturn(refreshToken);
        when(login.getUserId()).thenReturn(userId);
        when(login.getUsername()).thenReturn(userName);
        GrantedAuthority mockedAuthority = mock(GrantedAuthority.class);
        when(mockedAuthority.getAuthority()).thenReturn("ADMIN");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(mockedAuthority);
        when(login.getAuthorities()).thenReturn((Collection)authorities);
        doNothing().when(loginPersistentLayer).update(login);
        when(properties.getTokenSalt()).thenReturn(salt);
        when(properties.getTokenExpirationInSecond()).thenReturn(exp);
        when(JwtUtility.createJWTToken(anyString(), anyMap(), anyInt())).thenReturn(token);
        Token authToken = mock(Token.class);
        when(authServiceModelFactory.createNewToken()).thenReturn(authToken);
        authenticator.refreshToken(token, refreshToken);
        Mockito.verify(authToken).setAccessToken(token);
        Mockito.verify(authToken).setRefreshToken(anyString());
        Mockito.verify(authToken).setExpiresInSeconds(exp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refreshTokenWithInvalidAccessToken() {
        authenticator.refreshToken("invalid token", "refresh token");
    }

    @Test(expected = UnAuthorizeException.class)
    public void refreshTokenWithInvalidRefreshToken() {
        String token = "Bearer Token";
        String refreshToken = "Invalid Refresh Token";
        String userName = "userName";
        String userId = "userId";
        String salt = "salt";
        int exp = 10000;
        Login login = mock(Login.class);
        mockStatic(JwtUtility.class);
        when(JwtUtility.getClaim(AuthenticationService.USER_NAME, "Token")).thenReturn(userName);
        when(loginPersistentLayer.getLogin(userName)).thenReturn(login);
        when(login.getRefreshToken()).thenReturn("valid Refresh token");
        authenticator.refreshToken(token, refreshToken);
    }

}
