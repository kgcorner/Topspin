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

    private Role role;

    @Before
    public void setUp() throws Exception {
        role = new Role();
    }

    @Test
    public void setAuthority() {
        role.setAuthority("User");
        assertEquals("User", role.getAuthority());
    }
}