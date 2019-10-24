package com.kgcorner.topspin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 24/10/19
*/

public class PropertiesTest {

    private Properties properties;

    @Before
    public void setup() {
        this.properties = new Properties();
    }

    @Test
    public void getPasswordSalt() {
        String passwordSalt = "password salt";
        properties.setPasswordSalt(passwordSalt);
        Assert.assertEquals("password salt is not matching", passwordSalt, properties.getPasswordSalt());
    }


    @Test
    public void getTokenExpirationInSecond() {
        int expiresIn = 100;
        properties.setTokenExpirationInSecond(expiresIn);
        Assert.assertEquals("Expire time is not matching", expiresIn, properties.getTokenExpirationInSecond());
    }


    @Test
    public void getTokenSalt() {
        String tokenSalt = "Token salt";
        properties.setTokenSalt(tokenSalt);
        Assert.assertEquals("Token salt is not macthing", tokenSalt, properties.getTokenSalt());
    }

}