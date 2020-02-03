package com.kgcorner.topspin.service.google;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.models.DummyLogin;
import com.kgcorner.web.HttpUtil;
import kong.unirest.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/*
Description : Test for Google OAuth Service
Author: kumar
Created on : 06/11/19
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtil.class)
public class GoogleOAuthServiceTest {

    private GoogleOAuthService googleOAuthService;
    private GoogleConfigProvider mockedGoogleConfigProvider;
    private AuthServiceModelFactory mockedAuthServiceModelFactory;

    @Before
    public void setup() {
        this.mockedAuthServiceModelFactory = PowerMockito.mock(AuthServiceModelFactory.class);
        this.mockedGoogleConfigProvider = PowerMockito.mock(GoogleConfigProvider.class);
        this.googleOAuthService = new GoogleOAuthService();
        Whitebox.setInternalState(googleOAuthService, "googleConfigProvider", mockedGoogleConfigProvider);
        Whitebox.setInternalState(googleOAuthService, "authServiceModelFactory", mockedAuthServiceModelFactory);
    }


    @Test
    public void getAccessToken() {
        String redirectUri = "redirectUri";
        String code = "auth_code";
        String tokenExchangeUrl = "TokenExchangeUrl";
        String appKey = "appKey";
        String appSec = "appSec";
        String accessToken = "accessToken";
        PowerMockito.when(this.mockedGoogleConfigProvider.getAccessTokenExchangeUrl(redirectUri, code)).thenReturn(tokenExchangeUrl);
        PowerMockito.when(this.mockedGoogleConfigProvider.getAppKey()).thenReturn(appKey);
        PowerMockito.when(this.mockedGoogleConfigProvider.getSecretKey()).thenReturn(appSec);
        PowerMockito.mockStatic(HttpUtil.class);

        Map<String, String> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("client_id", appKey);
        postData.put("client_secret", appSec);
        postData.put("redirect_uri", redirectUri);
        postData.put("grant_type", "authorization_code");
        String body = "{" +
            "access_token:"+ accessToken+
            "}";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);
        PowerMockito.when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        PowerMockito.when(mockedResponse.getBody()).thenReturn(body);
        PowerMockito.when(HttpUtil.doPost(tokenExchangeUrl, null, null, postData)).thenReturn(mockedResponse);
        String tokenInResponse = googleOAuthService.getAccessToken(code, redirectUri);
        Assert.assertNotNull("Returned token is null", tokenInResponse);
        Assert.assertEquals("Token is not matching", accessToken, tokenInResponse);
    }

    @Test
    public void getAccessTokenFailed() {
        String redirectUri = "redirectUri";
        String code = "auth_code";
        String tokenExchangeUrl = "TokenExchangeUrl";
        String appKey = "appKey";
        String appSec = "appSec";
        String accessToken = "accessToken";
        PowerMockito.when(this.mockedGoogleConfigProvider.getAccessTokenExchangeUrl(redirectUri, code)).thenReturn(tokenExchangeUrl);
        PowerMockito.when(this.mockedGoogleConfigProvider.getAppKey()).thenReturn(appKey);
        PowerMockito.when(this.mockedGoogleConfigProvider.getSecretKey()).thenReturn(appSec);
        PowerMockito.mockStatic(HttpUtil.class);

        Map<String, String> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("client_id", appKey);
        postData.put("client_secret", appSec);
        postData.put("redirect_uri", redirectUri);
        postData.put("grant_type", "authorization_code");
        String body = "{" +
            "access_token:"+ accessToken+
            "}";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);
        PowerMockito.when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        PowerMockito.when(HttpUtil.doPost(tokenExchangeUrl, null, null, postData)).thenReturn(mockedResponse);
        String tokenInResponse = googleOAuthService.getAccessToken(code, redirectUri);
        Assert.assertNull("Returned token is not null", tokenInResponse);
    }

    @Test
    public void validateAccessToken() {
        String accessToken = "accessToken";
        String accessTokenValidationUrl = "accessTokenValidationUrl";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);

        PowerMockito.when(this.mockedGoogleConfigProvider.getAccessTokenValidationUrl(accessToken)).thenReturn(accessTokenValidationUrl);
        PowerMockito.mockStatic(HttpUtil.class);

        PowerMockito.when(mockedResponse.isSuccess()).thenReturn(true);
        PowerMockito.when(HttpUtil.doGet(accessTokenValidationUrl, null, null)).thenReturn(mockedResponse);
        Assert.assertTrue("Access token validation failed", googleOAuthService.validateAccessToken(accessToken));
    }

    @Test
    public void validateAccessTokenFailed() {
        String accessToken = "accessToken";
        String accessTokenValidationUrl = "accessTokenValidationUrl";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);

        PowerMockito.when(this.mockedGoogleConfigProvider.getAccessTokenValidationUrl(accessToken)).thenReturn(accessTokenValidationUrl);
        PowerMockito.mockStatic(HttpUtil.class);

        PowerMockito.when(mockedResponse.isSuccess()).thenReturn(false);
        PowerMockito.when(HttpUtil.doGet(accessTokenValidationUrl, null, null)).thenReturn(mockedResponse);
        Assert.assertFalse("Access token validation failed", googleOAuthService.validateAccessToken(accessToken));
    }


    @Test
    public void getUserInfo() {
        String accessToken = "accessToken";
        String userInfoUrl = "userInfoUrl";
        String userInfo = "userInfo";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);
        Map<String, String>  headerData = new HashMap<>();
        headerData.put("Authorization","Bearer "+accessToken);
        PowerMockito.when(this.mockedGoogleConfigProvider.getUserInfoUrl(null, null))
            .thenReturn(userInfoUrl);
        PowerMockito.mockStatic(HttpUtil.class);
        PowerMockito.when(HttpUtil.doGet(userInfoUrl, null, headerData)).thenReturn(mockedResponse);
        PowerMockito.when(mockedResponse.isSuccess()).thenReturn(true);
        PowerMockito.when(mockedResponse.getBody()).thenReturn(userInfo);
        String userInfoInResponse = googleOAuthService.getUserInfo(accessToken);
        Assert.assertNotNull("User info is null", userInfoInResponse);
        Assert.assertEquals("User info is not matching", userInfo, userInfoInResponse);
    }

    @Test
    public void getUserInfoFailed() {
        String accessToken = "accessToken";
        String userInfoUrl = "userInfoUrl";
        HttpResponse mockedResponse = PowerMockito.mock(HttpResponse.class);

        Map<String, String>  headerData = new HashMap<>();
        headerData.put("Authorization","Bearer "+accessToken);
        PowerMockito.when(this.mockedGoogleConfigProvider.getUserInfoUrl(null, null))
            .thenReturn(userInfoUrl);
        PowerMockito.mockStatic(HttpUtil.class);
        PowerMockito.when(HttpUtil.doGet(userInfoUrl, null, headerData)).thenReturn(mockedResponse);
        String userInfo = "userinfo";
        PowerMockito.when(mockedResponse.getBody()).thenReturn(userInfo);
        PowerMockito.when(mockedResponse.isSuccess()).thenReturn(false);
        String userInfoInResponse = googleOAuthService.getUserInfo(accessToken);
        Assert.assertNull("User info is not null", userInfoInResponse);
    }

    @Test
    public void createLoginObject() {
        String email = "email";
        String data = "{" +
            "emailAddresses:[" +
                    "{" +
                        "value:" + email +
                    "}"+
                "]"+
            "}";
        PowerMockito.when(mockedAuthServiceModelFactory.createNewLogin()).thenReturn(new DummyLogin());
        Login loginInResponse = googleOAuthService.createLoginObject(data);
        Assert.assertNotNull("Login object is null", loginInResponse);
        Assert.assertEquals("User name is not matching", email, loginInResponse.getUserName());
    }

    @Test
    public void getOAuthServiceName() {
        Assert.assertEquals("Auth service name is not matching", GoogleOAuthService.GOOGLE,
            googleOAuthService.getOAuthServiceName());
    }
}