package com.kgcorner.cache;

/**
 * Description : Factory for getting cache handler
 * Author: kumar
 * Created on : 11/8/19
 */
public final class CacheFactory {
    public enum CACHE_TYPE  {
        REDIS_CACHE
    }
    private CacheFactory(){}
    public static CacheHandler getCacheHandler (CACHE_TYPE type) {
        return type == null ? null : RedisCache.getInstance();
    }
}