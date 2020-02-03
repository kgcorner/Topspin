package com.kgcorner.topspin.dao;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/11/19
 */

public class MongoUserDaoTest {
    @Test
    public void testMongoUserDao() {
        assertNotNull(new MongoUserDao(null));
    }
}