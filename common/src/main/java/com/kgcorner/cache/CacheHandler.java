package com.kgcorner.cache;


/**
 * Description : Contract for cache handler
 * Author: kumar
 * Created on : 11/8/19
 */
public interface CacheHandler {
    /**
     * Sets cache value
     * @param key key for the cache
     * @param value value to cache
     */
    void setValue(Object key, Object value);

    /**
     * Returns cached value for the key
     * @param key key for the cache
     * @param object type of the cached value
     * @param <T> Type of the cached value extending serializable
     * @return cached value
     */
    <T>T getValue(Object key, Class<T> object);
}