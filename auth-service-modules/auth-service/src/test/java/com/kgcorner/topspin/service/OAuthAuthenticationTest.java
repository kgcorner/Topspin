package com.kgcorner.topspin.service;

import com.kgcorner.crypto.BigStringGenerator;
import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import com.kgcorner.topspin.models.DummyLogin;
import com.kgcorner.topspin.models.DummyToken;
import com.kgcorner.topspin.service.facebook.FacebookOAuthService;
import com.kgcorner.topspin.service.google.GoogleOAuthService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.*;


/*
Description : Test for OAuthAuthentication
Author: kumar
Created on : 07/11/19
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest({BigStringGenerator.class, JwtUtility.class})
public class OAuthAuthenticationTest {
    private List<OAuthService> mockedServicesList;
    private AuthServiceModelFactory mockedAuthServiceModelFactory;
    private LoginPersistentLayer mockedLoginPersistentLayer;
    private Properties mockedProperties;
    private OAuthAuthentication oAuthAuthentication;
    private FacebookOAuthService mockedFbService;
    private GoogleOAuthService mockedGoogleService;

    @Before
    public void setUp() throws Exception {
        oAuthAuthentication = new OAuthAuthentication();
        mockedFbService = PowerMockito.mock(FacebookOAuthService.class);
        mockedGoogleService = PowerMockito.mock(GoogleOAuthService.class);
        mockedServicesList = new ArrayList();
        mockedServicesList.add(mockedFbService);
        mockedServicesList.add(mockedGoogleService);
        mockedAuthServiceModelFactory = PowerMockito.mock(AuthServiceModelFactory.class);
        mockedLoginPersistentLayer = PowerMockito.mock(LoginPersistentLayer.class);
        mockedProperties = PowerMockito.mock(Properties.class);

        WhiteboxImpl.setInternalState(oAuthAuthentication, "oAuthServices", mockedServicesList);
        WhiteboxImpl.setInternalState(oAuthAuthentication, "authServiceModelFactory", mockedAuthServiceModelFactory);
        WhiteboxImpl.setInternalState(oAuthAuthentication, "loginPersistentLayer", mockedLoginPersistentLayer);
        WhiteboxImpl.setInternalState(oAuthAuthentication, "properties", mockedProperties);

        when(mockedFbService.getOAuthServiceName()).thenReturn(FacebookOAuthService.FACEBOOK);
        when(mockedGoogleService.getOAuthServiceName()).thenReturn(GoogleOAuthService.GOOGLE);

    }

    @Test(expected = IllegalStateException.class)
    public void authenticateToken() {
        oAuthAuthentication.authenticateToken("");
    }

    @Test
    public void testResolveAuthCodeAndAuthenticateForFacebook() {
        ResolveAuthCodeAndAuthenticate("facebook", true);
    }

    @Test
    public void testResolveAuthCodeAndAuthenticateForGoogle() {
        ResolveAuthCodeAndAuthenticate("google", true);
    }

    @Test
    public void testResolveAuthCodeAndAuthenticateForFacebookWithNoLogin() {
        ResolveAuthCodeAndAuthenticate("facebook", false);
    }

    @Test
    public void testResolveAuthCodeAndAuthenticateForGoogleWithNoLogin() {
        ResolveAuthCodeAndAuthenticate("google", false);
    }

    @Test
    public void testResolveAuthCodeAndAuthenticateForNoMatchingService() {
        ResolveAuthCodeAndAuthenticate("non-existent-oauth-server", false);
    }



    private void ResolveAuthCodeAndAuthenticate(String serverName, boolean loginExists) {
        String code = "code";
        String redirectUri = "redirectUri";
        String accessToken = "accessToken";
        String userInfo = "userInfo";
        Login dummyLogin = new DummyLogin();
        String email = "Email";
        String refreshToken = "refreshToken";
        String tokenSalt = "tokenSalt";
        int expiration = 100;
        String jwtToken = "jwtToken";
        dummyLogin.setUsername(email);
        dummyLogin.setUserId("userId");
        OAuthService mockedService = null;
        for(OAuthService service : mockedServicesList) {
            if(service.getOAuthServiceName().equalsIgnoreCase(serverName)) {
                mockedService = service;
                break;
            }
        }

        if(mockedService == null) {
            try {
                Token response = oAuthAuthentication.resolveAuthCodeAndAuthenticate(code, redirectUri, serverName);
                Assert.assertNull("Token is not null for non existent oAuth service", response);
                return;
            } catch (ResourceNotFoundException e) {
                if(loginExists)
                    Assert.fail("We shouldn't reach here for no matching oAuthService");
                else
                    return;
            }

        }
        when(mockedService.getAccessToken(code, redirectUri)).thenReturn(accessToken);
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", email);
        claims.put("USER_ID", dummyLogin.getUserId());
        setupMock(mockedService, accessToken, userInfo,dummyLogin, email, tokenSalt, expiration, refreshToken, claims, jwtToken, loginExists);
        try {
            Token response = oAuthAuthentication.resolveAuthCodeAndAuthenticate(code, redirectUri, serverName);
            Assert.assertNotNull("Token is null", response);
            Assert.assertEquals("Access token is not matching", jwtToken, response.getAccessToken());
            Assert.assertEquals("refresh token is not matching", refreshToken, response.getRefreshToken());
            Assert.assertEquals("expiration time is not matching", expiration, response.getExpiresInSeconds());
        } catch (ResourceNotFoundException e) {
            if(loginExists)
                Assert.fail("No OAuth service found for google");
            else
                return;
        }
    }

    private void setupMock(OAuthService mockedService, String accessToken, String userInfo,
                           Login dummyLogin, String email, String tokenSalt, int expiration, String refreshToken,
                           Map<String, String> claims, String jwtToken, boolean loginExists
    ) {
        when(mockedService.getUserInfo(accessToken)).thenReturn(userInfo);
        when(mockedService.createLoginObject(userInfo)).thenReturn(dummyLogin);
        if(loginExists)
            when(mockedLoginPersistentLayer.getLogin(email)).thenReturn(dummyLogin);
        else
            when(mockedLoginPersistentLayer.getLogin(email)).thenReturn(null);
        when(mockedAuthServiceModelFactory.createNewToken()).thenReturn(new DummyToken());
        when(mockedProperties.getTokenSalt()).thenReturn(tokenSalt);
        when(mockedProperties.getTokenExpirationInSecond()).thenReturn(expiration);
        PowerMockito.mockStatic(BigStringGenerator.class);
        when(BigStringGenerator.generateBigString()).thenReturn(refreshToken);
        PowerMockito.mockStatic(JwtUtility.class);
        when(JwtUtility.createJWTToken(tokenSalt, claims, expiration)).thenReturn(jwtToken);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForFacebookWithExistingLoginAndValidToken() {
        validateAccessTokenAndAuthenticate("facebook", true, true);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForGoogleWithExistingLoginAndValidToken() {
        validateAccessTokenAndAuthenticate("google", true, true);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForFacebookWithExistingLoginAndInValidToken() {
        validateAccessTokenAndAuthenticate("facebook", true, false);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForGoogleWithExistingLoginAndVInalidToken() {
        validateAccessTokenAndAuthenticate("google", true, false);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForFacebookWithNonExistingLoginAndValidToken() {
        validateAccessTokenAndAuthenticate("facebook", false, true);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForGoogleWithNonExistingLoginAndValidToken() {
        validateAccessTokenAndAuthenticate("google", false, true);
    }

    @Test
    public void validateAccessTokenAndAuthenticateForNonExistingOAuthServer() {
        validateAccessTokenAndAuthenticate("non-existent-oauth-server", true, true);
    }

    private void validateAccessTokenAndAuthenticate(String serverName, boolean loginExists, boolean validToken) {
        String accessToken = "accessToken";
        String userInfo = "userInfo";
        Login dummyLogin = new DummyLogin();
        String email = "Email";
        String refreshToken = "refreshToken";
        String tokenSalt = "tokenSalt";
        int expiration = 100;
        String jwtToken = "jwtToken";
        dummyLogin.setUsername(email);
        dummyLogin.setUserId("userId");
        OAuthService mockedService = null;
        for(OAuthService service : mockedServicesList) {
            if(service.getOAuthServiceName().equalsIgnoreCase(serverName)) {
                mockedService = service;
                break;
            }
        }

        if(mockedService == null) {
            try {
                Token response = oAuthAuthentication.validateAccessTokenAndAuthenticate(accessToken, serverName);
                Assert.assertNull("Token is not null for non existent oAuth service", response);
                return;
            } catch (ResourceNotFoundException e) {
                if(loginExists)
                    Assert.fail("We shouldn't reach here for no matching oAuthService");
                else
                    return;
            }
        }

        when(mockedService.validateAccessToken(accessToken)).thenReturn(validToken);
        Map<String, String> claims = new HashMap<>();
        claims.put("USER_NAME", email);
        claims.put("USER_ID", dummyLogin.getUserId());
        setupMock(mockedService, accessToken, userInfo,dummyLogin, email, tokenSalt, expiration, refreshToken, claims, jwtToken, loginExists);
        try {
            Token response = oAuthAuthentication.validateAccessTokenAndAuthenticate(accessToken, serverName);

            Assert.assertNotNull("Token is null", response);
            Assert.assertEquals("Access token is not matching", jwtToken, response.getAccessToken());
            Assert.assertEquals("refresh token is not matching", refreshToken, response.getRefreshToken());
            Assert.assertEquals("expiration time is not matching", expiration, response.getExpiresInSeconds());
        } catch (ResourceNotFoundException e) {
            if(loginExists)
                Assert.fail("No OAuth service found for google");
            else
                return;
        } catch (IllegalArgumentException x) {
            if(validToken) {
               Assert.fail("Access token validation failed");
            }
        }

    }
}