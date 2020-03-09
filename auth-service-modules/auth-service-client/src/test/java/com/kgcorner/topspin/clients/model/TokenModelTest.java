package com.kgcorner.topspin.clients.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class TokenModelTest {
    private TokenModel tokenModel;

    @Before
    public void setUp() throws Exception {
        tokenModel = new TokenModel();
    }

    @Test
    public void testTokenModel() {
        tokenModel.setAccessToken("Access Token");
        tokenModel.setRefreshToken("Refresh Token");
        tokenModel.setExpiresInSeconds(100);
        assertEquals("Access Token", tokenModel.getAccessToken());
        assertEquals("Refresh Token", tokenModel.getRefreshToken());
        assertEquals(100, tokenModel.getExpiresInSeconds());
    }
}