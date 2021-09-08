package com.kgcorner.topspin.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/21
 */

public class StoreServiceSecurityTest {

    private StoreServiceSecurity serviceSecurity;

    @Before
    public void setUp() throws Exception {
        serviceSecurity = new StoreServiceSecurity();
    }

    @Test
    public void getPublicUrl() {
        assertEquals(2, serviceSecurity.getPublicUrl().length);
    }

    @Test
    public void getAuthenticatedUrl() {
        assertEquals(1, serviceSecurity.getAuthenticatedUrl().size());
    }
}