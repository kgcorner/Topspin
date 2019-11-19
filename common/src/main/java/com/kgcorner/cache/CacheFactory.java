package com.kgcorner.cache;

/**
 * Description : Factory for getting cache handler
 * Author: kumar
 * Created on : 11/8/19
 */
public class CacheFactory {
    public enum CACHE_TYPE  {
        REDIS_CACHE
    }

    public static CacheHandler getCacheHandler (CACHE_TYPE type) {
        if(type != null) {
            switch (type) {
                case REDIS_CACHE:
                    return RedisCache.getInstance();
            }
        }
        return null;
    }
}