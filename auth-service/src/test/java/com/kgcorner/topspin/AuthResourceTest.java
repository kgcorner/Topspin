package com.kgcorner.topspin;

/*
Description : Unit Tests for AuthResource class
Author: kumar
Created on : 16/10/19
*/

import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
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
@PrepareForTest(AuthResource.class)
public class AuthResourceTest {
    private AuthResource authResource;
    private Authenticator mockedAuthenticator;

    @Before
    public void setup() {
        this.authResource = new AuthResource();
        this.mockedAuthenticator = PowerMockito.mock(Authenticator.class);
        Whitebox.setInternalState(this.authResource, "authenticator", this.mockedAuthenticator);
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

}