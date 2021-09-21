package com.kgcorner.dao;

/*
Description : Implementation for cached Mysql database
Author: kumar
Created on : 11/8/19
*/

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MySqlRepository<T extends Serializable> extends CachedRepository <T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T create(T model) {
        this.entityManager.persist(model);
        this.entityManager.flush();
        return model;
    }

    @Override
    public T update(T model) {
        model = this.entityManager.merge(model);
        this.entityManager.flush();
        return model;
    }

    @Override
    public void remove(String modelId, Class<T> model) {
        var object = get(modelId, model);
        remove(object);
    }

    @Override
    public void remove(T model) {
        Assert.notNull(model, "entity is null");
        if(!entityManager.contains(model))
            throw new IllegalArgumentException("No such entity found");
        this.entityManager.remove(model);
    }

    @Override
    public void remove(Object key, String modelName, String keyName) {
        String queryStr = "delete from "+modelName+" where "+keyName+"=?";
        var query = this.entityManager.createQuery(queryStr);
        query.setParameter(1, key);
        query.executeUpdate();
    }

    @Override
    public T get(String modelId, Class<T> model) {
        T entity = null;
        try {
            entity = this.entityManager.find(model, modelId);
        }
        catch(NoResultException x) {
            throw new IllegalArgumentException("No "+model.getName()+" found with ID:"+modelId);
        }
        return entity;
    }

    @Override
    public List<T> getIn(List<?> args, String argumentUnderCheck, Class<T> model) {
        var cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        Expression<String> parentExpression = entity.get(argumentUnderCheck);
        var predicate = parentExpression.in(args);
        criteriaQuery = criteriaQuery.select(entity).where(cb.and(predicate));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> getIn(List<?> args, List<Operation> conditions, String argumentUnderCheck, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        Expression<String> parentExpression = entity.get(argumentUnderCheck);
        var predicate = parentExpression.in(args);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        predicates.add(predicate);
        criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        return typedQuery.getResultList();
    }

    @Override
    public List<T> getAll(Class<T> model) {
        String className = model.getName();
        String hql = "from "+className+" as entity order by entity.id desc";
        return this.entityManager.createQuery(hql).getResultList();
    }

    @Override
    public List<T> getAll(int page, int itemsPerPage, Class<T> model) {
        String className = model.getName();
        String hql = "from " +className + " as entity order by entity.id desc";
        int firstResult = (page-1) * itemsPerPage + 1;
        firstResult = firstResult == 1 ? 0 : firstResult;
        return this.entityManager.createQuery(hql).setFirstResult(firstResult)
            .setMaxResults(itemsPerPage).getResultList();
    }

    @Override
    public List<T> getAll(List<Operation> conditions, Class<T> model) {
        if(conditions == null || conditions.isEmpty()) {
            return getAll(model);
        }
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        var i=0;
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName() + i, operand.getValue());
            i++;
        }
        return typedQuery.getResultList();
    }

    @Override
    public List<T> getAll(List<Operation> conditions, List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        TypedQuery<T> typedQuery = getTypedQuery(conditions, orders, criteriaBuilder,
            criteriaQuery, entity, predicates);
        return typedQuery.getResultList();
    }

    private TypedQuery<T> getTypedQuery(List<Operation> conditions, List<Order> orders, CriteriaBuilder criteriaBuilder,
                                        CriteriaQuery<T> criteriaQuery, Root<T> entity, List<Predicate> predicates) {
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        if(orders != null) {
            for (Order o : orders) {
                javax.persistence.criteria.Order order = new OrderImpl(entity.get(o.getName()), o.isAscending());
                orderList.add(order);
            }
        }
        if (!orderList.isEmpty()) {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(orderList);
        } else {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for (Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        return typedQuery;
    }

    @Override
    public T get(List<Operation> conditions, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        T result = null;
        criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Object[]> getAll(Procedure procedure) {
        var query =  this.entityManager.createStoredProcedureQuery(procedure.getName());
        for(Operation o : procedure.getArguments()) {
            query.registerStoredProcedureParameter(o.getName(), o.getOperandType(), o.getMode());
            query.setParameter(o.getName(), o.getValue());
        }
        return query.getResultList();
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page,
                                                     int itemPerPage, Class<T> model) {
        return getCroppedList(conditions, page, itemPerPage, null, model);
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage,
                                                     List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        entity.alias("entitySub");
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        List<T> result = null;
        int start = (page - 1) * itemPerPage + 1;

        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        if(orders != null && !orders.isEmpty()) {
            for (Order o : orders) {
                javax.persistence.criteria.Order order = new OrderImpl(entity.get(o.getName()), o.isAscending());
                orderList.add(order);
            }
        }
        TypedQuery<T> typedQuery = getTypedQuery(conditions, orders, criteriaBuilder,
            criteriaQuery, entity, predicates);
        result = typedQuery.setFirstResult(start).setMaxResults(itemPerPage).getResultList();
        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        Root<?> entityType = countCriteria.from(model);
        entityType.alias("entitySub");
        countCriteria.select(criteriaBuilder.count(entityType));
        var restriction = criteriaQuery.getRestriction();
        if (restriction != null) {
            countCriteria.where(restriction); // Copy restrictions, throws: 'Invalid path: 'generatedAlias1.message'
        }
        TypedQuery<Long> countTypedQuery = this.entityManager.createQuery(countCriteria);
        for(Operation operand : conditions) {
            countTypedQuery.setParameter(operand.getName(), operand.getValue());
        }
        Long maxItems = countTypedQuery.getSingleResult();
        return new CroppedCollection<>(maxItems.intValue(), result);
    }

    @Override
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        List<T> result = null;
        int start = (page - 1) * itemPerPage + 1;
        TypedQuery<T> typedQuery = getTypedQuery(conditions, orders, criteriaBuilder, criteriaQuery, entity, predicates);
        if(page > 0) {
            result = typedQuery.setFirstResult(start).setMaxResults(itemPerPage).getResultList();
        } else {
            result = typedQuery.getResultList();
        }
        return result;
    }

    @Override
    public Object runSelectNativeQuery(String query, Object... params) {
        var nativeQuery = this.entityManager.createNativeQuery(query);

        for (var i = 1; i <= params.length; i++) {
            nativeQuery.setParameter(i, params[i-1]);
        }

        return  nativeQuery.getResultList();
    }

    @Override
    public int runUpdateNativeQuery(String query, Object... params) {
        var nativeQuery = this.entityManager.createNativeQuery(query);

        for (var i = 1; i <= params.length; i++) {
            nativeQuery.setParameter(i, params[i-1]);
        }

        return  nativeQuery.executeUpdate();
    }

    @Override
    public long getCount(List<Operation> conditions, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);

        criteriaQuery.select(criteriaBuilder.count(entity))
            .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        return typedQuery.getSingleResult();
    }

    @Override
    public Object[] callProc(String procedureName, List<Operation> operation) {
        var storedProcedureQuery = this.entityManager.createStoredProcedureQuery(procedureName);
        if(operation != null) {
            for(Operation o : operation) {
                storedProcedureQuery.registerStoredProcedureParameter(o.getName(), o.getOperandType(), o.getMode());
                storedProcedureQuery.setParameter(o.getName(), o.getValue());
            }
        }
        Object singleResult = storedProcedureQuery.getSingleResult();
        return (Object[]) singleResult;
    }

    @Override
    public List<Object[]> get(List<String> groupBy, List<Operation> conditions, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<T> entity = criteriaQuery.from(model);
        List<Object[]>  result = new ArrayList<>();
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        for(String groupByProperty : groupBy) {
            criteriaQuery.groupBy(entity.get(groupByProperty));
            criteriaQuery.multiselect(entity.get(groupByProperty), criteriaBuilder.count(entity));
        }
        List<Tuple> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        for(Tuple tuple : resultList) {
            var objects = new Object[2];
            objects[0] = tuple.get(0);
            objects[1] = tuple.get(1);
            result.add(objects);
        }
        return result;
    }

    private List<Predicate> getPredicates(List<Operation> conditions, CriteriaBuilder criteriaBuilder, Root<T> entity) {
        List<Predicate> predicates = new ArrayList<>();
        for(Operation operand : conditions) {
            ParameterExpression param = criteriaBuilder.parameter(operand.getOperandType(),operand.getName());
            switch(operand.getOperator()) {
                case EQ:
                    predicates.add(criteriaBuilder.equal(entity.get(operand.getName()), param));
                    break;
                case GE:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(entity.get(operand.getName()), param));
                    break;
                case LE:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(entity.get(operand.getName()), param));
                    break;
                case LT:
                    predicates.add(criteriaBuilder.lessThan(entity.get(operand.getName()), param));
                    break;
                case GT:
                    predicates.add(criteriaBuilder.greaterThan(entity.get(operand.getName()), param));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(entity.get(operand.getName()), param));
                    break;
                case IS_NOT_NULL:
                    predicates.add(criteriaBuilder.isNotNull(entity.get(operand.getName())));
                    break;
                case IS_NULL:
                    predicates.add(criteriaBuilder.isNull(entity.get(operand.getName())));
                    break;
            }
        }
        return predicates;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void getDbConfig() {

    }

    @Override
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, Class<T> model) {
        return getAll(conditions, page, itemPerPage, Collections.emptyList(), model);
    }
}