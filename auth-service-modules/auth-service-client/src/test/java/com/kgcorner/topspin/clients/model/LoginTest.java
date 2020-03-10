package com.kgcorner.topspin.clients.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class LoginTest {

    @Test
    public void testLoginModel() {
        Login login = new Login();
        login.setId("1");
        login.setLoginProvider("Google");
        login.setOAuthAccessToken("AccessToken");
        login.setPassword("Password");
        login.setRefreshToken("RefreshToken");
        login.setUserId("1");
        login.setUsername("Username");
        login.addRole("user");

        assertEquals("1", login.getId());
        assertEquals("1", login.getUserId());
        assertEquals("Google", login.getLoginProvider());
        assertEquals("Password", login.getPassword());
        assertEquals("AccessToken", login.getOAuthAccessToken());
        assertEquals("RefreshToken", login.getRefreshToken());
        assertEquals("Username", login.getUsername());
        assertEquals(1, login.getRoles().size());
        assertEquals(1, login.getAuthorities().size());
        assertEquals("ROLE_user", login.getRoles().get(0).getAuthority());
        assertEquals(login.getId().hashCode(), login.hashCode());
        assertEquals(0, new Login().hashCode());
    }

    @Test
    public void testRoleWithNull() {
        Login login = new Login();
        login.addRole(null);
        assertEquals(0, login.getRoles().size());
    }

    @Test
    public void testRoleWithRole_() {
        Login login = new Login();
        login.addRole("ROLE_USER");
        assertEquals(1, login.getRoles().size());
        assertEquals("ROLE_USER", login.getRoles().get(0).getAuthority());
        login.addRole("ROLE_USER");
        assertEquals(2, login.getRoles().size());
    }

    @Test
    public void testEquals() {
        Login lg1 = new Login();
        lg1.setUserId("1");

        Login lg2 = new Login();
        lg2.setUserId("1");
        assertTrue(lg1.equals(lg2));
        lg2.setUserId("0");
        assertFalse(lg1.equals(lg2));
        assertFalse(lg1.equals(new Object()));
    }

    @Test
    public void testWithNoRole() {
        Login login = new Login();
        assertEquals(0, login.getRoles().size());
    }

}