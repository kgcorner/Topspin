package com.kgcorner.topspin.dao;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class MongoStoreDaoTest {

    @Test
    public void testMongoStoreDao() {
        Assert.assertNotNull(new MongoStoreDao<>(null));
    }
}