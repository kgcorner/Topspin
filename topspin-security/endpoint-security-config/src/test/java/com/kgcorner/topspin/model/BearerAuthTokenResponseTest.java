package com.kgcorner.topspin.model;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/02/20
 */

public class BearerAuthTokenResponseTest {

    @Test
    public void getCredentials() {
        BearerAuthToken token = new BearerAuthToken(Collections.emptyList(), "token");
        assertNull(token.getCredentials());
    }

    @Test
    public void getPrincipal() {
        BearerAuthToken token = new BearerAuthToken(Collections.emptyList(), "token");
        assertEquals("token", token.getPrincipal());
    }
}