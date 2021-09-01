package com.kgcorner.topspin.service.facebook;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.models.DummyLogin;
import com.kgcorner.web.HttpUtil;
import kong.unirest.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/*
Description : <Write class Description>
Author: kumar
Created on : 22/10/19
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtil.class)
public class FacebookOAuthServiceTest {
    private FacebookOAuthService facebookOAuthService;
    private FacebookConfigProvider mockedFacebookConfigProvider;
    private AuthServiceModelFactory mockedAuthServiceModelFactory;

    @Before
    public void setUp() throws Exception {
        facebookOAuthService = new FacebookOAuthService();
        mockedFacebookConfigProvider = PowerMockito.mock(FacebookConfigProvider.class);
        mockedAuthServiceModelFactory = PowerMockito.mock(AuthServiceModelFactory.class);
        Whitebox.setInternalState(facebookOAuthService, "facebookConfigProvider", mockedFacebookConfigProvider);
        Whitebox.setInternalState(facebookOAuthService, "authServiceModelFactory", mockedAuthServiceModelFactory);
    }

    @Test
    public void getAccessToken() {
        when(mockedFacebookConfigProvider.getAccessTokenExchangeUrl("redirect_uri", "auth_code"))
            .thenReturn("exchange_url");
        String accessToken = "access_token";
        HttpResponse mockedResponse = mock(HttpResponse.class);
        mockStatic(HttpUtil.class);
        when(HttpUtil.doGet("exchange_url", null, null))
            .thenReturn(mockedResponse);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getBody()).thenReturn(prepareFbResponseForAccessToken(accessToken));
        String response = facebookOAuthService.getAccessToken("auth_code", "redirect_uri");
        Assert.assertNotNull("Returned access token is null", response);
        Assert.assertEquals("access token is not matching", accessToken, response);
    }

    @Test
    public void getAccessTokenForInvalidAuthCode() {
        when(mockedFacebookConfigProvider.getAccessTokenExchangeUrl("redirect_uri", "auth_code"))
            .thenReturn("exchange_url");
        String accessToken = "access_token";
        HttpResponse mockedResponse = mock(HttpResponse.class);
        mockStatic(HttpUtil.class);
        when(HttpUtil.doGet("exchange_url", null, null))
            .thenReturn(mockedResponse);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        when(mockedResponse.getBody()).thenReturn(prepareFbResponseForAccessToken(accessToken));
        String response = facebookOAuthService.getAccessToken("auth_code", "redirect_uri");
        Assert.assertNull("Returned access token is not null", response);
    }

    @Test
    public void validateAccessToken() {
        String accessToken = "access_token";
        when(mockedFacebookConfigProvider.getAccessTokenValidationUrl(accessToken))
            .thenReturn("validation_url");
        HttpResponse mockedResponse = mock(HttpResponse.class);
        mockStatic(HttpUtil.class);
        when(HttpUtil.doGet("validation_url", null, null))
            .thenReturn(mockedResponse);
        when(mockedResponse.isSuccess()).thenReturn(true);
        boolean response = facebookOAuthService.validateAccessToken(accessToken);
        Assert.assertTrue("access token validation failed", response);
    }

    @Test
    public void getOAuthServiceName() {
        assertEquals("OAuth service name is not matching","facebook",
            facebookOAuthService.getOAuthServiceName());
    }

    private String prepareFbResponseForAccessToken(String accessToken) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", accessToken);
        return jsonObject.toString();
    }

    @Test
    public void createLoginObject() {
        String data = "{\n" +
            " \"id\":\"fbid\",\n" +
            " \"name\":\"kumar gaurav\",\n" +
            " \"email\":\"kumar@fb.com\"\n" +
            "}";
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        when(mockedAuthServiceModelFactory.createNewLogin(roles)).thenReturn(new DummyLogin());
        Login loginObject = facebookOAuthService.createLoginObject(data);
        Assert.assertNotNull(loginObject);
        Assert.assertEquals("username of the login object is ot matching",
            "kumar@fb.com", loginObject.getUsername());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoginObjectNull() {
        facebookOAuthService.createLoginObject(null);
    }

    @Test
    public void getUserInfo() {
        String accessToken = "accessToken";
        String url = "facebook.user.info.url";
        String userInfoData = "userInfoData";
        when(mockedFacebookConfigProvider.getUserInfoUrl("email,name", accessToken)).thenReturn(url);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        mockStatic(HttpUtil.class);
        when(HttpUtil.doGet(url, null, null)).thenReturn(mockedResponse);
        when(mockedResponse.isSuccess()).thenReturn(true);
        when(mockedResponse.getBody()).thenReturn(userInfoData);
        String response = facebookOAuthService.getUserInfo(accessToken);
        Assert.assertNotNull("User info can't be null", response);
        Assert.assertEquals("User info not matching", userInfoData, response);
    }

    @Test
    public void getUserInfoFailed() {
        String accessToken = "accessToken";
        String url = "facebook.user.info.url";
        String userInfoData = "userInfoData";
        when(mockedFacebookConfigProvider.getUserInfoUrl("email,name", accessToken)).thenReturn(url);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        mockStatic(HttpUtil.class);
        when(HttpUtil.doGet(url, null, null)).thenReturn(mockedResponse);
        when(mockedResponse.isSuccess()).thenReturn(false);
        String response = facebookOAuthService.getUserInfo(accessToken);
        Assert.assertNull("User info should be null", response);
    }
}