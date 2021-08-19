package com.kgcorner.dao;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    public List<T> getAll(int maxCount, Class<T> type) {
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


    @Override
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, Class<T> model) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(page, itemPerPage);
        if(itemPerPage > 0)
            query.with(pageable);
        Criteria criteria = new Criteria();
        for(Operation operation : conditions) {
            switch (operation.getOperator()) {

                case GE:
                    criteria.andOperator(Criteria.where(operation.getName()).gte(operation.getValue()));
                    break;
                case EQ:
                    criteria.andOperator(Criteria.where(operation.getName()).is(operation.getValue()));
                    break;
                case LE:
                    criteria.andOperator(Criteria.where(operation.getName()).lte(operation.getValue()));
                    break;
                case GT:
                    criteria.andOperator(Criteria.where(operation.getName()).gt(operation.getValue()));
                    break;
                case LT:
                    criteria.andOperator(Criteria.where(operation.getName()).lt(operation.getValue()));
                    break;
                case LIKE:
                case IS_NOT_NULL:
                    throw new UnsupportedOperationException("Like operator is not supported for mongo yet");
                case IS_NULL:
                    criteria.andOperator(Criteria.where(operation.getName()).exists(false));
                    break;
            }
        }
        query.addCriteria(criteria);
        return template.find(query, model);
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