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
        LoginResponse login = new LoginResponse();
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
        assertEquals(0, new LoginResponse().hashCode());
    }

    @Test
    public void testRoleWithNull() {
        LoginResponse login = new LoginResponse();
        login.addRole(null);
        assertEquals(0, login.getRoles().size());
    }

    @Test
    public void testRoleWithRole_() {
        LoginResponse login = new LoginResponse();
        login.addRole("ROLE_USER");
        assertEquals(1, login.getRoles().size());
        assertEquals("ROLE_USER", login.getRoles().get(0).getAuthority());
        login.addRole("ROLE_USER");
        assertEquals(2, login.getRoles().size());
    }

    @Test
    public void testEquals() {
        LoginResponse lg1 = new LoginResponse();
        lg1.setUserId("1");

        LoginResponse lg2 = new LoginResponse();
        lg2.setUserId("1");
        boolean isEqual = lg1.equals(lg2);
        assertTrue(isEqual);
        lg2.setUserId("0");
        isEqual = lg1.equals(lg2);
        assertFalse(isEqual);
        isEqual = lg1.equals(new Object());
        assertFalse(isEqual);
    }

    @Test
    public void testWithNoRole() {
        LoginResponse login = new LoginResponse();
        assertEquals(0, login.getRoles().size());
    }

}