package com.kgcorner.topspin.dao;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/05/21
 */

public class MysqlOfferDaoTest {
    @Test
    public void testConstructor() {
        Assert.assertNotNull(new MysqlOfferDao<>());
    }
}