package com.kgcorner.cache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/11/19
 */

public class RedisCacheTest {

    private RedisCache redisCache;

    @Before
    public void setUp() throws Exception {
        redisCache = RedisCache.getInstance();
        assertNotNull("Instantiation failed", redisCache);
    }

    @Test(expected = IllegalStateException.class)
    public void setValue() {
        redisCache.setValue("a","a");
    }

    @Test
    public void getValue() {
       assertNull(redisCache.getValue("a", null));
    }
}