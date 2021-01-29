package com.kgcorner.topspin.dao;

import org.junit.Test;
import org.springframework.util.Assert;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/01/21
 */

public class MongoCategoryDaoTest {

    @Test
    public void shouldCreateMongoCategoryDaoObject() {
        Assert.notNull(new MongoCategoryDao<>(null));
    }
}