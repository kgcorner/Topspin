package com.kgcorner.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/21
 */

public class DataRepositoryTest {

    private DataRepository.Order order;

    @Before
    public void setUp() throws Exception {
        order = new DataRepository.Order();
    }

    @Test
    public void testDataRepoOrder() {
        String name = "name";
        boolean ascending = true;
        order.setName(name);
        order.setAscending(ascending);
        Assert.assertEquals(name, order.getName());
        Assert.assertEquals(ascending, order.isAscending());
    }
}