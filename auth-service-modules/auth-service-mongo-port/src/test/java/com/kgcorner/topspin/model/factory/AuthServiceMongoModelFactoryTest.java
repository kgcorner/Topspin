package com.kgcorner.topspin.model.factory;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
        List<String> roles = new ArrayList<>();
        roles.add("R1");
        roles.add("R2");
        roles.add("R3");
        Login login = authServiceMongoModelFactory.createNewLogin(roles);
        assertNotNull("Login is null", login);
        assertEquals(roles.size(), login.getAuthorities().size());
    }

    @Test
    public void createNewToken() {
        Token token = authServiceMongoModelFactory.createNewToken();
        assertNotNull("Token is null", token);
    }
}