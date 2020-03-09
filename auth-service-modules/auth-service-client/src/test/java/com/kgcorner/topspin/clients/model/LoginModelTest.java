package com.kgcorner.topspin.clients.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class LoginModelTest {

    @Test
    public void testLoginModel() {
        LoginModel loginModel = new LoginModel();
        loginModel.setId("1");
        loginModel.setLoginProvider("Google");
        loginModel.setOAuthAccessToken("AccessToken");
        loginModel.setPassword("Password");
        loginModel.setRefreshToken("RefreshToken");
        loginModel.setUserId("1");
        loginModel.setUsername("Username");
        loginModel.addRole("user");

        assertEquals("1", loginModel.getId());
        assertEquals("1", loginModel.getUserId());
        assertEquals("Google", loginModel.getLoginProvider());
        assertEquals("Password", loginModel.getPassword());
        assertEquals("AccessToken", loginModel.getOAuthAccessToken());
        assertEquals("RefreshToken", loginModel.getRefreshToken());
        assertEquals("Username", loginModel.getUsername());
        assertEquals(1, loginModel.getRoles().size());
        assertEquals(1, loginModel.getAuthorities().size());
        assertEquals("ROLE_user", loginModel.getRoles().get(0).getAuthority());
        assertEquals(loginModel.getId().hashCode(), loginModel.hashCode());
        assertEquals(0, new LoginModel().hashCode());
    }

    @Test
    public void testRoleWithNull() {
        LoginModel loginModel = new LoginModel();
        loginModel.addRole(null);
        assertEquals(0, loginModel.getRoles().size());
    }

    @Test
    public void testRoleWithRole_() {
        LoginModel loginModel = new LoginModel();
        loginModel.addRole("ROLE_USER");
        assertEquals(1, loginModel.getRoles().size());
        assertEquals("ROLE_USER", loginModel.getRoles().get(0).getAuthority());
        loginModel.addRole("ROLE_USER");
        assertEquals(2, loginModel.getRoles().size());
    }

    @Test
    public void testEquals() {
        LoginModel lg1 = new LoginModel();
        lg1.setUserId("1");

        LoginModel lg2 = new LoginModel();
        lg2.setUserId("1");
        assertTrue(lg1.equals(lg2));
        lg2.setUserId("0");
        assertFalse(lg1.equals(lg2));
        assertFalse(lg1.equals(new Object()));
    }

    @Test
    public void testWithNoRole() {
        LoginModel loginModel = new LoginModel();
        assertEquals(0, loginModel.getRoles().size());
    }

}