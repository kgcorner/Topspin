package com.kgcorner.dao;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Description : DataRepo implementation for MongoDb
 *
 * Author: kumar
 * Created on : 26/08/19
 * @param <T> Serializable
 */
public abstract class MongoRepository<T extends Serializable> extends CachedRepository <T> {
    protected MongoTemplate template;

    public List getAll(int maxCount, Class<T> type) {
        return getAll(0, maxCount, type);
    }

    /**
     * @see DataRepository#getAll(int, int, Class)
     * @param page
     * @param itemsCount
     */
    @Override
    public List<T> getAll(int page, int itemsCount, Class<T> type) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, type);
    }

    public T getById(String id, Class<T> type) {
        return getByKey("id", id, type);
    }

    public T getByKey(String key, String value, Class<T> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, type);
    }

    /**
     * @see DataRepository#create(Serializable)
     */
    @Override
    public T create(T document) {
        document = template.insert(document);
        return  document;
    }

    @Override
    public T update(T document) {
        document = template.save(document);
        return document;
    }

    @Override
    public void remove(T document) {
        template.remove(document);
    }
}