package com.kgcorner.cache;

/**
 * Description : Redis Cache handler
 * Author: kumar
 * Created on : 11/8/19
 */
public class RedisCache implements CacheHandler{

    private static final RedisCache INSTANCE = new RedisCache();

    private RedisCache(){}

    public static RedisCache getInstance() {
        return INSTANCE;
    }

    /**
     * Not implemented yet
     * @param key key for the cache
     * @param value value to cache
     */
    public void setValue(Object key, Object value) {
        throw new IllegalStateException("Not implemented yet");
    }

    public <T> T getValue(Object key, Class<T> object) {
        return null;
    }
}