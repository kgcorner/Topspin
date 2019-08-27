package com.kgcorner.cache;

/*
Description : Redis Cache handler
Author: kumar
Created on : 11/8/19
*/

public class RedisCache implements CacheHandler{

    private static RedisCache INSTANCE = null;

    public static RedisCache getInstance() {
        return INSTANCE;
    }

    public void setValue(Object key, Object value) {

    }

    public <T> T getValue(Object key, Class<T> object) {
        return null;
    }
}