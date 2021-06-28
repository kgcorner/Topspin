package com.kgcorner.topspin.clients.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class RoleTest {

    private RoleResponse role;

    @Before
    public void setUp() throws Exception {
        role = new RoleResponse();
    }

    @Test
    public void setAuthority() {
        role.setAuthority("User");
        assertEquals("User", role.getAuthority());
    }
}