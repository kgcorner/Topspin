package com.kgcorner.topspin;

/*
Description : Unit Tests for AuthResource class
Author: kumar
Created on : 16/10/19
*/

import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.models.DummyLogin;
import com.kgcorner.topspin.models.DummyToken;
import com.kgcorner.topspin.service.Authenticator;
import com.kgcorner.topspin.service.RegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

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
        Token tokenToBeReturned = createDummyToken(accessToken, refreshToken, expireIn);
        when(mockedAuthenticator.authenticateWithToken(anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.getToken("Basic Token");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }

    @Test
    public void testTokenForOAuth()  {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = createDummyToken(accessToken, refreshToken, expireIn);
        tokenToBeReturned.setAccessToken(accessToken);
        tokenToBeReturned.setRefreshToken(refreshToken);
        tokenToBeReturned.setExpiresInSeconds(expireIn);
        when(mockedAuthenticator.validateAccessTokenAndAuthorize(anyString(), anyString())).thenReturn(tokenToBeReturned);
        Token returnedToken = authResource.getTokenForOAuth("Auth Token", "google");
        Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
        Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
        Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
    }

    @Test
    public void testTokenForOAuthWIthRedirect() {
        try {
            String accessToken = "accessToken";
            String refreshToken = "refreshToken";
            int expireIn = 100;
            Token tokenToBeReturned = createDummyToken(accessToken, refreshToken, expireIn);
            when(mockedAuthenticator.validateAccessTokenAndAuthorize(anyString(), anyString())).thenReturn(tokenToBeReturned);
            Token returnedToken = authResource.getTokenForOAuth("Auth Token", "google");
            Assert.assertEquals("Access token is not matching", accessToken, returnedToken.getAccessToken());
            Assert.assertEquals("Refresh token is not matching", refreshToken, returnedToken.getRefreshToken());
            Assert.assertEquals("Expire time is not matching", expireIn, returnedToken.getExpiresInSeconds());
        } catch (ResourceNotFoundException x){
            Assert.fail(x.getMessage());
        }
    }

    @Test
    public void testResolveAccessToken()  {
        String accessToken ="accessToken";
        String refreshToken ="refreshToken";
        int expireIn = 100;
        Token tokenToBeReturned = createDummyToken(accessToken, refreshToken, expireIn);
        when(mockedAuthenticator.resolveTokenAndAuthorize(anyString(), anyString(), anyString())).thenReturn(tokenToBeReturned);
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
        Login loginToBeReturend = createDummyLogin(username, password, userId);
        when(mockedRegistrationService.createLogin(username, password, userId)).thenReturn(loginToBeReturend);
        Login login = authResource.createLogin(username, password, userId);
        Assert.assertEquals("User name is not matching", username, login.getUsername());
        Assert.assertEquals("password is not matching", "", login.getPassword());
        Assert.assertEquals("User id is not matching", userId, login.getUserId());
        Assert.assertNull(login.getRefreshToken());
    }

    private Login createDummyLogin(String username, String password, String userId) {
        Login login = new DummyLogin();
        login.setUserId(userId);
        login.setUsername(username);
        login.setPassword(password);
        return login;
    }

    private Token createDummyToken(String accessToken, String refreshToken, int expireIn) {
        Token token = new DummyToken();
        token.setExpiresInSeconds(expireIn);
        token.setRefreshToken(refreshToken);
        token.setAccessToken(accessToken);
        return token;
    }


    @Test
    public void createAdmin() {
        String userName = "username";
        String password = "password";
        String userid = "userId";
        Login login  = new DummyLogin();
        login.setPassword(password);
        login.setUserId(userid);
        login.setUsername(userName);
        when(mockedRegistrationService.createAdmin(userName, password, userid)).thenReturn(login);
        Login createdLogin = authResource.createAdmin(userName, password, userid);
        Mockito.verify(mockedRegistrationService).createAdmin(userName, password, userid);
        Assert.assertEquals(userid, createdLogin.getUserId());
        Assert.assertEquals("", createdLogin.getPassword());
        Assert.assertEquals(userName, createdLogin.getUsername());
    }

    @Test
    public void refreshToken() {
        String token = "token";
        String refreshToken = "refresh token";
        authResource.refreshToken(token, refreshToken);
        Mockito.verify(mockedAuthenticator).refreshToken(token, refreshToken);
    }
}