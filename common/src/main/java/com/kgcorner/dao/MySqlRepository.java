package com.kgcorner.dao;

/*
Description : Implementation for cached Mysql database
Author: kumar
Created on : 11/8/19
*/

import org.apache.log4j.Logger;
import org.hibernate.jpa.criteria.OrderImpl;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class MySqlRepository<T extends Serializable> extends CachedRepository <T>{

    private static final Logger LOGGER = Logger.getLogger(MySqlRepository.class);
    public static final String DID_NOT_FOUND = "Did not found ";

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
        if(!entityManager.contains(model))
            model = entityManager.merge(model);
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
            LOGGER.error("No "+model.getName()+" found with ID:"+modelId);
        }
        return entity;
    }

    @Override
    public List<T> getIn(List args, String argumentUnderCheck, Class<T> model) {
        var cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        Expression<String> parentExpression = entity.get(argumentUnderCheck);
        var predicate = parentExpression.in(args);
        criteriaQuery.select(entity).where(cb.and(predicate));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> getIn(List args, List<Operation> conditions, String argumentUnderCheck, Class<T> model) {
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
        if(conditions == null) {
            return getAll(model);
        }
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        List<T> result = null;
        int i=0;
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName() + i, operand.getValue());
            i++;
        }
        try{
            result = typedQuery.getResultList();
        }
        catch(NoResultException e) {
            LOGGER.error(DID_NOT_FOUND + model.getName());
        }
        catch(Exception x) {
            x.printStackTrace();
        }

        return result;
    }

    @Override
    public List<T> getAll(List<Operation> conditions, List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        List<T> result = null;
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        for(Order o : orders) {
            javax.persistence.criteria.Order order = new OrderImpl(entity.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        if(orderList.isEmpty()) {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(orderList);
        } else {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        try{
            result = typedQuery.getResultList();
        }
        catch(NoResultException e) {
            LOGGER.error("Did not found "+model.getName());
        }
        return result;
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
        try{
            result = typedQuery.getSingleResult();
        }
        catch(NoResultException e) {
            LOGGER.error("Did not found "+model.getName());
        }
        return result;
    }

    @Override
    public List<Object[]> getAll(Procedure procedure) {
        var query =  this.entityManager.createStoredProcedureQuery(procedure.getName());
        for(Operation o : procedure.getArguments()) {
            query.registerStoredProcedureParameter(o.getName(), o.getOperandType(), o.getMode());
            query.setParameter(o.getName(), o.getValue());
        }
        return (List<Object[]>) query.getResultList();
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page,
                                                     int itemPerPage, Class<T> model) {
        return getCroppedList(conditions, page, itemPerPage, null, model);
    }

    @Override
    public CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        entity.alias("entitySub");
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        List<T> result = null;
        int start = (page - 1) * itemPerPage;

        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        if(orders != null && orders.isEmpty()) {
            for (Order o : orders) {
                javax.persistence.criteria.Order order = new OrderImpl(entity.get(o.getName()), o.isAsending());
                orderList.add(order);
            }
        }

        if(orderList.size() > 0) {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(orderList);
        } else {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        Long maxItems = 0L;
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        try{
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
            maxItems = countTypedQuery.getSingleResult();
        }
        catch(NoResultException e) {
            LOGGER.error("Did not found "+model.getName());
        }


        return new CroppedCollection<>(maxItems.intValue(), result);
    }

    @Override
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(model);
        Root<T> entity = criteriaQuery.from(model);
        List<Predicate> predicates = getPredicates(conditions, criteriaBuilder, entity);
        List<T> result = null;
        int start = (page - 1) * itemPerPage;
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        for(Order o : orders) {
            javax.persistence.criteria.Order order = new OrderImpl(entity.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        if(orderList.isEmpty()) {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(orderList);
        } else {
            criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        for(Operation operand : conditions) {
            typedQuery.setParameter(operand.getName(), operand.getValue());
        }
        try{
            if(page > 0) {
                result = typedQuery.setFirstResult(start).setMaxResults(itemPerPage).getResultList();
            } else {
                result = typedQuery.getResultList();
            }
        }
        catch(NoResultException e) {
            LOGGER.error("Did not found "+model.getName());
        }
        return result;
    }

    @Override
    public Object runSelectNativeQuery(String query, Object... params) {
        var nativeQuery = this.entityManager.createNativeQuery(query);

        for (int i = 1; i <= params.length; i++) {
            nativeQuery.setParameter(i, params[i-1]);
        }

        return  nativeQuery.getResultList();
    }

    @Override
    public int runUpdateNativeQuery(String query, Object... params) {
        var nativeQuery = this.entityManager.createNativeQuery(query);

        for (int i = 1; i <= params.length; i++) {
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
        try{
            return typedQuery.getSingleResult();
        }
        catch(NoResultException e) {
            LOGGER.error("Did not found "+model.getName());
        }
        return 0;
    }

    @Override
    public Object[] callProc(String procedureName, List<Operation> Operation) {
        var storedProcedureQuery = this.entityManager.createStoredProcedureQuery(procedureName);
        if(Operation != null) {
            for(Operation o : Operation) {
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
            Object[] objects = new Object[2];
            objects[0] = tuple.get(0);
            objects[1] = tuple.get(1);
            result.add(objects);
        }
        return result;
    }

    private List<Predicate> getPredicates(List<Operation> conditions, CriteriaBuilder criteriaBuilder, Root<T> entity) {
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for(Operation operand : conditions) {
            ParameterExpression param = criteriaBuilder.parameter(operand.getOperandType(),operand.getName() + i);
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
                case LIKE:
                    predicates.add(criteriaBuilder.like(entity.get(operand.getName()), param));
                    break;
                case IS_NOT_NULL:
                    predicates.add(criteriaBuilder.isNull(entity.get(operand.getName())));
                    break;
                case IS_NULL:
                    predicates.add(criteriaBuilder.isNotNull(entity.get(operand.getName())));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operand.getOperator());
            }
            i++;
        }
        return predicates;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}