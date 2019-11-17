package com.kgcorner.topspin.model.factory;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

public class AuthServiceMongoModelFactoryTest {

    private AuthServiceMongoModelFactory authServiceMongoModelFactory;

    @Before
    public void setUp() throws Exception {
        authServiceMongoModelFactory = new AuthServiceMongoModelFactory();
    }

    @Test
    public void createNewLogin() {
        Login login = authServiceMongoModelFactory.createNewLogin();
        assertNotNull("Login is null", login);
    }

    @Test
    public void createNewToken() {
        Token token = authServiceMongoModelFactory.createNewToken();
        assertNotNull("Token is null", token);
    }
}