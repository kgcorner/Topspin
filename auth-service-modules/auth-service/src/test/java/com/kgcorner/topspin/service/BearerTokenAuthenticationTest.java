package com.kgcorner.topspin.service;

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.models.DummyToken;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;


/*
Description : <Write class Description>
Author: kumar
Created on : 21/10/19
*/
@RunWith(PowerMockRunner.class)
@PrepareForTest(JwtUtility.class)
public class BearerTokenAuthenticationTest {
    private Properties mockedProperties;
    private BearerTokenAuthentication bearerTokenAuthentication;
    private AuthServiceModelFactory mockedAuthServiceModelFactory;
    public static final String TOKEN_SALT = "token salt";

    @Before
    public void setup() {
        this.bearerTokenAuthentication = new BearerTokenAuthentication();
        this.mockedProperties = PowerMockito.mock(Properties.class);
        this.mockedAuthServiceModelFactory = PowerMockito.mock(AuthServiceModelFactory.class);
        Whitebox.setInternalState(this.bearerTokenAuthentication, "properties", mockedProperties);
        Whitebox.setInternalState(this.bearerTokenAuthentication, "authServiceModelFactory", mockedAuthServiceModelFactory);
    }

    @Test
    public void authenticateToken() {
        String sampleToken = "bearer valid JWT token";
        PowerMockito.mockStatic(JwtUtility.class);
        when(mockedProperties.getTokenSalt()).thenReturn(TOKEN_SALT);
        when(JwtUtility.validateToken(TOKEN_SALT, sampleToken.substring("bearer ".length()))).thenReturn(true);
        when(mockedAuthServiceModelFactory.createNewToken()).thenReturn(new DummyToken());
        Token response = this.bearerTokenAuthentication.authenticateToken(sampleToken);
        Assert.assertNotNull("Returned token is null for bearer authentication");
        Assert.assertEquals("Access token  is not matching", sampleToken.substring("bearer ".length()),
            response.getAccessToken());
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateTokenWithNullToken() {
        this.bearerTokenAuthentication.authenticateToken(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateTokenWithInvalidToken() {
        this.bearerTokenAuthentication.authenticateToken("invalid bearer token");
    }

    @Test
    public void authenticateTokenWithInvalidJwtToken() {
        String sampleToken = "bearer valid JWT token";
        PowerMockito.mockStatic(JwtUtility.class);
        when(JwtUtility.validateToken(TOKEN_SALT, sampleToken)).thenReturn(false);
        Token token = this.bearerTokenAuthentication.authenticateToken("bearer invalid JWT token");
        Assert.assertNull("Returned token is not null for invalid jwt token", token);
    }

    @Test(expected = NotImplementedException.class)
    public void authenticateCode() {
        this.bearerTokenAuthentication.authenticateCode("auth code", "redirect uri");
    }

    @Test(expected = NotImplementedException.class)
    public void resolveToken() {
        this.bearerTokenAuthentication.resolveToken("auth code",
            "redirect uri", "Server name");
    }
}