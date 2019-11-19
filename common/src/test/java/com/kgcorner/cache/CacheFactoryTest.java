package com.kgcorner.cache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : Unit test for {@code CacheFactory}
 * Author: kumar
 * Created on : 18/11/19
 */

public class CacheFactoryTest {

    @Test
    public void getCacheHandler() {
        CacheHandler cacheHandler = CacheFactory.getCacheHandler(CacheFactory.CACHE_TYPE.REDIS_CACHE);
        assertNotNull("Cache handler is null", cacheHandler);
        assertTrue("Cache handler is not of expected type", cacheHandler instanceof RedisCache);
    }

    @Test
    public void getCacheHandlerFailure() {
        CacheHandler cacheHandler = CacheFactory.getCacheHandler(null);
        assertNull("Cache handler is not null", cacheHandler);
    }
}