package com.kgcorner.topspin.clients.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class TokenResponseTest {
    private TokenResponse tokenResponse;

    @Before
    public void setUp() throws Exception {
        tokenResponse = new TokenResponse();
    }

    @Test
    public void testTokenModel() {
        tokenResponse.setAccessToken("Access Token");
        tokenResponse.setRefreshToken("Refresh Token");
        tokenResponse.setExpiresInSeconds(100);
        assertEquals("Access Token", tokenResponse.getAccessToken());
        assertEquals("Refresh Token", tokenResponse.getRefreshToken());
        assertEquals(100, tokenResponse.getExpiresInSeconds());
    }
}