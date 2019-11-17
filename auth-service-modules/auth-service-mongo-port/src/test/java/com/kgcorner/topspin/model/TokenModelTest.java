package com.kgcorner.topspin.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

public class TokenModelTest {

    private TokenModel tokenModel;

    @Before
    public void setUp() throws Exception {
        tokenModel = new TokenModel();
    }

    @Test
    public void getAccessToken() {
        tokenModel.setAccessToken("Token");
        assertEquals("Access token is not matching", "Token", tokenModel.getAccessToken());
    }

    @Test
    public void setAccessToken() {
        tokenModel.setAccessToken("Token");
        assertEquals("Access token is not matching", "Token", tokenModel.getAccessToken());
    }

    @Test
    public void getRefreshToken() {
        tokenModel.setRefreshToken("Token");
        assertEquals("Refresh token is not matching", "Token", tokenModel.getRefreshToken());
    }

    @Test
    public void setRefreshToken() {
        tokenModel.setRefreshToken("Token");
        assertEquals("Refresh token is not matching", "Token", tokenModel.getRefreshToken());
    }

    @Test
    public void getExpiresInSeconds() {
        tokenModel.setExpiresInSeconds(1);
        assertEquals("expiry time is not matching", 1, tokenModel.getExpiresInSeconds());
    }

    @Test
    public void setExpiresInSeconds() {
        tokenModel.setExpiresInSeconds(1);
        assertEquals("expiry time is not matching", 1, tokenModel.getExpiresInSeconds());
    }
}