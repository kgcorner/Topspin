package com.kgcorner.topspin.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/*
Description : Unit test for Login model
Author: kumar
Created on : 17/11/19
*/

public class LoginModelTest {

    private LoginModel loginModel;

    @Before
    public void setUp() throws Exception {
        loginModel = new LoginModel();
    }

    @Test
    public void getId() {
        loginModel.setId("0");
        assertEquals("login id is not matching", "0", loginModel.getId());
    }

    @Test
    public void setId() {
        loginModel.setId("0");
        assertEquals("login id is not matching", "0", loginModel.getId());
    }

    @Test
    public void getUserName() {
        loginModel.setUserName("0");
        assertEquals("login userName is not matching", "0", loginModel.getUserName());
    }

    @Test
    public void setUserName() {
        loginModel.setUserName("0");
        assertEquals("login userName is not matching", "0", loginModel.getUserName());
    }

    @Test
    public void getPassword() {
        loginModel.setPassword("0");
        assertEquals("login password is not matching", "0", loginModel.getPassword());
    }

    @Test
    public void setPassword() {
        loginModel.setPassword("0");
        assertEquals("login password is not matching", "0", loginModel.getPassword());
    }

    @Test
    public void getUserId() {
        loginModel.setUserId("0");
        assertEquals("login user id is not matching", "0", loginModel.getUserId());
    }

    @Test
    public void setUserId() {
        loginModel.setUserId("0");
        assertEquals("login user id is not matching", "0", loginModel.getUserId());
    }

    @Test
    public void getRefreshToken() {
        loginModel.setRefreshToken("0");
        assertEquals("login refer token is not matching", "0", loginModel.getRefreshToken());
    }

    @Test
    public void setRefreshToken() {
        loginModel.setRefreshToken("0");
        assertEquals("login refer token is not matching", "0", loginModel.getRefreshToken());
    }

    @Test
    public void testEquals() {
        Login login1 = new LoginModel();
        login1.setUserId("0");
        Login login2 = new LoginModel();
        login2.setUserId("0");
        boolean equal = login1.equals(login2);
        assertTrue("logins are not equal", equal);
    }

    @Test
    public void testEqualsFailureSameLoginType() {
        Login login1 = new LoginModel();
        login1.setUserId("0");
        Login login2 = new LoginModel();
        login2.setUserId("1");
        boolean equal = login1.equals(login2);
        assertFalse("logins are equal", equal);
    }

    @Test
    public void testEqualsFailureDifferentType() {
        Login login1 = new LoginModel();
        login1.setId("0");
        boolean equal = login1.equals(new Object());
        assertFalse("logins are equal", equal);
    }

    @Test
    public void setLoginProvider() {
        loginModel.setLoginProvider("0");
        assertEquals("login provider is not matching", "0", loginModel.getLoginProvider());
    }

    @Test
    public void setOAuthAccessToken() {
        loginModel.setOAuthAccessToken("0");
        assertEquals("login provider is not matching", "0", loginModel.getOAuthAccessToken());
    }

    @Test
    public void getLoginProvider() {
        loginModel.setLoginProvider("0");
        assertEquals("login provider is not matching", "0", loginModel.getLoginProvider());
    }

    @Test
    public void getOAuthAccessToken() {
        loginModel.setOAuthAccessToken("0");
        assertEquals("login provider is not matching", "0", loginModel.getOAuthAccessToken());
    }
}