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
    public T create(T model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T update(T model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T updateOrCreate(T model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(String modelId, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(T model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Object key, String modelName, String keyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(String modelId, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getIn(List args, String argumentUnderCheck, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getIn(List args, List<Operation> conditions, String argumentUnderCheck, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getAll(Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getAll(int page, int itemsPerPage, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getAll(List<Operation> conditions, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getAll(List<Operation> conditions, List<Order> orders, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(List<Operation> conditions, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Object[]> getAll(Procedure procedure) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object runSelectNativeQuery(String query, Object... params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int runUpdateNativeQuery(String query, Object... params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCount(List<Operation> OperationList, Class<T> entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] callProc(String procedureName, List<Operation> Operation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Object[]> get(List<String> groupBy, List<Operation> conditions, Class<T> model) {
        throw new UnsupportedOperationException();
    }
}