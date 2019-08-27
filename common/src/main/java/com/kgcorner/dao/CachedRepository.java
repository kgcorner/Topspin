package com.kgcorner.dao;

/*
Description : Represents cached repository
Author: kumar
Created on : 11/8/19
*/

import java.io.Serializable;
import java.util.List;

public class CachedRepository<T extends Serializable> implements  DataRepository <T> {
    @Override
    public List<T> getAll(Class<T> type) {
        return null;
    }

    @Override
    public List<T> getAll(int maxCount, Class<T> type) {
        return null;
    }

    @Override
    public List<T> getAll(int page, int itemsCount, Class<T> type) {
        return null;
    }

    @Override
    public T getById(String id, Class<T> type) {
        return null;
    }

    @Override
    public T getByKey(String key, String value, Class<T> type) {
        return null;
    }

    @Override
    public T create(T document) {
        return null;
    }

    @Override
    public T update(T document) {
        return null;
    }
}