package com.kgcorner.dao;

/*
Description : Implementation for cached Mysql database
Author: kumar
Created on : 11/8/19
*/

import com.kgcorner.cache.CacheFactory;
import com.kgcorner.cache.CacheHandler;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class MySqlRepository<T extends Serializable> extends CachedRepository <T>{

    private static final Logger LOGGER = Logger.getLogger(MySqlRepository.class);

    protected EntityManager entityManager;

    CacheHandler cacheHandler = getCacheHandler();

    public CacheHandler getCacheHandler() {
        return CacheFactory.getCacheHandler(CacheFactory.CACHE_TYPE.REDIS_CACHE);
    }

    private boolean isCacheEnabled() {
        return true;
    }

    @Override
    public List<T> getAll(Class<T> type) {
        String className = type.getName();
        String hql = "from "+className+" as entity order by entity.id desc";
        return (List<T>) this.entityManager.createQuery(hql).getResultList();
    }

    @Override
    public List<T> getAll(int maxCount, Class<T> type) {
        return getAll(1, maxCount, type);
    }

    @Override
    public List<T> getAll(int page, int itemsPerPage, Class<T> type) {
        String className = type.getName();
        String hql = "from "+className+" as entity order by entity.id desc";
        int firstResult = (page-1)*itemsPerPage+1;
        return (List<T>) this.entityManager.createQuery(hql).setFirstResult(firstResult)
            .setMaxResults(itemsPerPage).getResultList();
    }

    @Override
    public T getById(String id, Class<T> type) {
        T entity = null;
        if(isCacheEnabled()) {
            entity = cacheHandler.getValue(type.getName()+"_"+id, type);
            if(entity != null)
                return entity;
        }
        try {
            entity = this.entityManager.find(type, id);
        }
        catch(NoResultException x) {
            LOGGER.error("No "+type.getName()+" found with ID:"+id);
        }
        return entity;
    }

    @Override
    public T getByKey(String key, String value, Class<T> type) {

        return super.getByKey(key, value, type);
    }

    @Override
    public T create(T document) {
        return super.create(document);
    }

    @Override
    public T update(T document) {
        return super.update(document);
    }
}