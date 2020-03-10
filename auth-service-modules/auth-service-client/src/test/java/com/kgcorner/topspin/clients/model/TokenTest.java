package com.kgcorner.topspin.clients.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class TokenTest {
    private Token token;

    @Before
    public void setUp() throws Exception {
        token = new Token();
    }

    @Test
    public void testTokenModel() {
        token.setAccessToken("Access Token");
        token.setRefreshToken("Refresh Token");
        token.setExpiresInSeconds(100);
        assertEquals("Access Token", token.getAccessToken());
        assertEquals("Refresh Token", token.getRefreshToken());
        assertEquals(100, token.getExpiresInSeconds());
    }
}