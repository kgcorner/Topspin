package com.kgcorner.topspin.dao;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class MysqlOfferDaoTest {
    @Test
    public void testMysqlOfferDao() {
        assertNotNull(new MysqlOfferDao<>());
    }
}