package com.kgcorner.topspin.dao;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

public class MongoTransactionDaoTest {

    @Test
    public void testMongoTransactionDao() {
        Assert.assertNotNull(new MongoTransactionDao<>(null));
    }

}