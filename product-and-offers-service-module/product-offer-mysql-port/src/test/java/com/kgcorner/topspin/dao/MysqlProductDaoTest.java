package com.kgcorner.topspin.dao;

import org.junit.Assert;
import org.junit.Test;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/05/21
 */

public class MysqlProductDaoTest {
    @Test
    public void testConstructor() {
        Assert.assertNotNull(new MysqlProductDao<>());
    }
}