package com.kgcorner.topspin.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

public class MongoAuthenticationDaoTest {

    @Test
    public void MongoAuthenticationDao() throws Exception {
        MongoAuthenticationDao mongoAuthenticationDao = new MongoAuthenticationDao(null);
        assertNotNull("mongoAuthenticationDao is null", mongoAuthenticationDao);
    }
}