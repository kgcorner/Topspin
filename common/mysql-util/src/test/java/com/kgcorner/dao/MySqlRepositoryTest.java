package com.kgcorner.dao;

import org.hibernate.jpa.criteria.OrderImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 11/05/21
 */

public class MySqlRepositoryTest {
    private MySqlRepository<SampleEntity> repository;
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManager = PowerMockito.mock(EntityManager.class);
        this.repository = new TestClass();
        Whitebox.setInternalState(repository, "entityManager", this.entityManager);
    }

    @Test
    public void create() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId("random"); //setting id to ensure it's overwritten
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SampleEntity se = invocationOnMock.getArgument(0);
                se.setId("id");
                return null;
            }
        }).when(this.entityManager).persist(sampleEntity);
        doNothing().when(this.entityManager).flush();
        SampleEntity result = this.repository.create(sampleEntity);
        Assert.assertEquals(result.getId(), sampleEntity.getId());
    }

    @Test
    public void update() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId("random"); //setting id to ensure it's overwritten
        when(entityManager.merge(sampleEntity)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SampleEntity se = invocationOnMock.getArgument(0);
                se.setId("updated-id");
                return se;
            }
        });
        doNothing().when(this.entityManager).flush();
        SampleEntity result = this.repository.update(sampleEntity);
        Assert.assertEquals(result.getId(), sampleEntity.getId());
    }

    @Test
    public void remove() {
        String id = "sample id";
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        when(entityManager.find(SampleEntity.class, id)).thenReturn(entity);
        when(entityManager.contains(entity)).thenReturn(true);
        doNothing().when(entityManager).remove(entity);
        this.repository.remove(id, SampleEntity.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeWithNullEntity() {
        this.repository.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeWithNonExistingEntity() {
        String id = "sample id";
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        when(entityManager.contains(entity)).thenReturn(false);
        this.repository.remove(entity);
    }

    @Test
    public void testRemoveWithAKey() {
        String keyName = "key";
        String keyValue = "key";
        String modelName = "model";
        String queryStr = "delete from "+modelName+" where "+keyName+"=?";

        Query query = PowerMockito.mock(Query.class);
        when(this.entityManager.createQuery(queryStr)).thenReturn(query);
        when(query.setParameter(1, keyValue)).thenReturn(null);
        when(query.executeUpdate()).thenReturn(1);
        this.repository.remove(keyValue, modelName, keyName);
    }

    @Test
    public void get() {
        String id = "sample id";
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        when(entityManager.find(SampleEntity.class, id)).thenReturn(entity);
        SampleEntity result = this.repository.get(id, SampleEntity.class);
        Assert.assertEquals(entity, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNoResult() {
        String id = "sample id";
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        when(entityManager.find(SampleEntity.class, id)).thenThrow(new NoResultException());
        this.repository.get(id, SampleEntity.class);
    }

    @Test
    public void getIn() {
        List arguments = new ArrayList();
        List result = new ArrayList();
        result.add(new Object());
        String argCheck = "argCheck";
        Path expression = mock(Path.class);
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        when(root.get(argCheck)).thenReturn(expression);
        when(expression.in(arguments)).thenReturn(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicate)).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        when(query.getResultList()).thenReturn(result);
        Assert.assertEquals(result, repository.getIn(arguments, argCheck, SampleEntity.class));
    }

    @Test
    public void testGetIn() {
        List arguments = new ArrayList();
        List result = new ArrayList();
        result.add(new Object());
        String argCheck = "argCheck";
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        operations.add(new Operation(500, Operation.TYPES.NUMBER, "price", Operation.OPERATORS.LT));
        operations.add(new Operation(500, Operation.TYPES.NUMBER, "price", Operation.OPERATORS.GT));
        operations.add(new Operation(500, Operation.TYPES.NUMBER, "price", Operation.OPERATORS.GE));
        operations.add(new Operation(500, Operation.TYPES.NUMBER, "price", Operation.OPERATORS.LE));
        operations.add(new Operation("ABC", Operation.TYPES.STRING, "price", Operation.OPERATORS.LIKE));
        operations.add(new Operation(null, Operation.TYPES.STRING, "description",
            Operation.OPERATORS.IS_NULL));
        operations.add(new Operation("Some value", Operation.TYPES.STRING, "description",
            Operation.OPERATORS.IS_NOT_NULL));


        Path expression = mock(Path.class);
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        when(root.get(argCheck)).thenReturn(expression);
        when(expression.in(arguments)).thenReturn(predicate);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        predicates.add(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        when(query.getResultList()).thenReturn(result);
        Assert.assertEquals(result, repository.getIn(arguments, operations, argCheck, SampleEntity.class));
    }

    @Test
    public void getAll() {
        List<SampleEntity> entities = new ArrayList<>();
        String query = "from "+ SampleEntity.class.getName() +" as entity order by entity.id desc";
        Query queryObj = mock(Query.class);
        when(entityManager.createQuery(query)).thenReturn(queryObj);
        when(queryObj.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(SampleEntity.class));
    }

    @Test
    public void getAllWithPages() {
        List<SampleEntity> entities = new ArrayList<>();
        String query = "from "+ SampleEntity.class.getName() +" as entity order by entity.id desc";
        int page = 10;
        int itemCount = 10;
        Query queryObj = mock(Query.class);
        when(entityManager.createQuery(query)).thenReturn(queryObj);
        when(queryObj.setFirstResult(91)).thenReturn(queryObj);
        when(queryObj.setMaxResults(itemCount)).thenReturn(queryObj);
        when(queryObj.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(page, itemCount, SampleEntity.class));
    }

    @Test
    public void getAllWithNullCondition() {
        List<SampleEntity> entities = new ArrayList<>();
        String query = "from "+ SampleEntity.class.getName() +" as entity order by entity.id desc";
        Query queryObj = mock(Query.class);
        when(entityManager.createQuery(query)).thenReturn(queryObj);
        when(queryObj.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(null, SampleEntity.class));
    }

    @Test
    public void getAllWithEmptyCondition() {
        List<SampleEntity> entities = new ArrayList<>();
        String query = "from "+ SampleEntity.class.getName() +" as entity order by entity.id desc";
        Query queryObj = mock(Query.class);
        when(entityManager.createQuery(query)).thenReturn(queryObj);
        when(queryObj.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(Collections.emptyList(), SampleEntity.class));
    }

    @Test
    public void getAllWithCondition() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        predicates.add(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        List<SampleEntity> entities = new ArrayList<>();
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(operations, SampleEntity.class));
    }



    @Test
    public void testGetAllWithOrder() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));

        List<DataRepository.Order> ordering =  new ArrayList<>();
        DataRepository.Order o1  = new DataRepository.Order();
        o1.setAsending(true);
        o1.setName("name");
        ordering.add(o1);


        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        List<Order> orderList = new ArrayList<>();
        for (DataRepository.Order o : ordering) {
            Order order = new OrderImpl(root.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        when(cq.orderBy(orderList)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        List<SampleEntity> entities = new ArrayList<>();
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(operations, ordering, SampleEntity.class));

    }

    @Test
    public void testGetAllWithEmptyOrder() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        List<DataRepository.Order> ordering =  Collections.emptyList();
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        List<Order> orderList = new ArrayList<>();
        for (DataRepository.Order o : ordering) {
            Order order = new OrderImpl(root.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        when(cq.orderBy(orderList)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        List<SampleEntity> entities = new ArrayList<>();
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(operations, ordering, SampleEntity.class));

    }

    @Test
    public void testGetWithCondition() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        predicates.add(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        SampleEntity entity = new SampleEntity();
        when(query.getSingleResult()).thenReturn(entity);
        Assert.assertEquals(entity, repository.get(operations, SampleEntity.class));
    }

    @Test
    public void testGetWithProcedure() {
        String procedureName = "newProc";
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING,
            "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        Procedure procedure = new Procedure(procedureName, operations);
        StoredProcedureQuery query = mock(StoredProcedureQuery.class);
        when(entityManager.createStoredProcedureQuery(procedureName)).thenReturn(query);
        for(Operation o : procedure.getArguments()) {
            when(query.registerStoredProcedureParameter(o.getName(), o.getOperandType(),o.getMode())).thenReturn(query);
            when(query.setParameter(o.getName(), o.getValue())).thenReturn(query);
        }
        List<SampleEntity>  entities = new ArrayList<>();
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(procedure));
    }

    @Test
    public void getCroppedList() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));

        List<DataRepository.Order> ordering =  new ArrayList<>();
        DataRepository.Order o1  = new DataRepository.Order();
        o1.setAsending(true);
        o1.setName("name");
        ordering.add(o1);


        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Root countEntity = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);

        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        List<Order> orderList = new ArrayList<>();
        for (DataRepository.Order o : ordering) {
            Order order = new OrderImpl(root.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        when(cq.orderBy(orderList)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        List<SampleEntity> entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entities.add(new SampleEntity());
        }
        int page = 10;
        int itemPerPage = 10;
        when(query.setFirstResult(91)).thenReturn(query);
        when(query.setMaxResults(itemPerPage)).thenReturn(query);
        when(query.getResultList()).thenReturn(entities);

        when(cb.createQuery(Long.class)).thenReturn(countQuery);
        when(countQuery.from(SampleEntity.class)).thenReturn(countEntity);
        Expression countExp = mock(Expression.class);
        when(cb.count(countEntity)).thenReturn(countExp);
        when(countQuery.select(countExp)).thenReturn(countQuery);
        TypedQuery countTypedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(countQuery)).thenReturn(countTypedQuery);
        long totalResult = 200;
        when(countTypedQuery.getSingleResult()).thenReturn(totalResult);
        CroppedCollection<List<SampleEntity>> croppedList = repository.getCroppedList(operations, page,
            itemPerPage, ordering, SampleEntity.class);
        Assert.assertEquals(entities, croppedList.getItems());
        Assert.assertEquals(totalResult, croppedList.getSize());
        croppedList = repository.getCroppedList(operations, page,
            itemPerPage, SampleEntity.class);
        Assert.assertEquals(entities, croppedList.getItems());
        Assert.assertEquals(totalResult, croppedList.getSize());
        croppedList = repository.getCroppedList(operations, page,
            itemPerPage, Collections.emptyList(), SampleEntity.class);
        Assert.assertEquals(entities, croppedList.getItems());
        Assert.assertEquals(totalResult, croppedList.getSize());
    }

    @Test
    public void testGetAllWithOrderAndPage() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        List<DataRepository.Order> ordering =  Collections.emptyList();
        CriteriaQuery<SampleEntity> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(SampleEntity.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(root);
        List<Predicate> predicates = mockGetPredicate(operations, cb, root);
        when(cq.select(root)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(predicate);
        when(cq.where(predicate)).thenReturn(cq);
        List<Order> orderList = new ArrayList<>();
        for (DataRepository.Order o : ordering) {
            Order order = new OrderImpl(root.get(o.getName()), o.isAsending());
            orderList.add(order);
        }
        when(cq.orderBy(orderList)).thenReturn(cq);
        when(entityManager.createQuery(cq)).thenReturn(query);
        for(Operation operation : operations) {
            when(query.setParameter(operation.getName(), operation.getValue())).thenReturn(null);
        }
        int page = 10;
        int itemCount = 10;
        List<SampleEntity> entities = new ArrayList<>();
        when(query.setFirstResult(91)).thenReturn(query);
        when(query.setMaxResults(itemCount)).thenReturn(query);
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.getAll(operations, page, itemCount, ordering, SampleEntity.class));
    }

    @Test
    public void runSelectNativeQuery() {
        Object[] param = new Object[4];
        String procedure = "procedure";
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(procedure)).thenReturn(query);
        List<SampleEntity> entities = new ArrayList<>();
        entities.add(new SampleEntity());
        entities.add(new SampleEntity());
        when(query.getResultList()).thenReturn(entities);
        Assert.assertEquals(entities, repository.runSelectNativeQuery(procedure, param));
    }

    @Test
    public void runUpdateNativeQuery() {
        Object[] param = new Object[4];
        String procedure = "procedure";
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(procedure)).thenReturn(query);
        int updatedRecords = 9;
        when(query.executeUpdate()).thenReturn(updatedRecords);
        Assert.assertEquals(updatedRecords, repository.runUpdateNativeQuery(procedure, param));
    }

    @Test
    public void getCount() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Long> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> entityRoot = mock(Root.class);
        TypedQuery<Long> typedQuery = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(entityRoot);
        List<Predicate> predicates = mockGetPredicate(operations, cb, entityRoot);
        Predicate allPredicates = mock(Predicate.class);
        Expression countExp = mock(Expression.class);
        when(cb.count(entityRoot)).thenReturn(countExp);
        when(cq.select(countExp)).thenReturn(cq);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(allPredicates);
        when(cq.where(allPredicates)).thenReturn(cq);
        long totalCount = 1000;
        when(entityManager.createQuery(cq)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(totalCount);
        Assert.assertEquals(totalCount, repository.getCount(operations, SampleEntity.class));
    }

    @Test
    public void callProc() {
        String procedure = "StoredProcedure";
        StoredProcedureQuery query = mock(StoredProcedureQuery.class);
        when(entityManager.createStoredProcedureQuery(procedure)).thenReturn(query);
        Object[] results = new Object[7];
        when(query.getSingleResult()).thenReturn(results);
        Assert.assertEquals(results, repository.callProc(procedure, null));
    }

    @Test
    public void callProcWithCondition() {
        String procedure = "StoredProcedure";
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        StoredProcedureQuery query = mock(StoredProcedureQuery.class);
        when(entityManager.createStoredProcedureQuery(procedure)).thenReturn(query);
        Object[] results = new Object[7];
        when(query.getSingleResult()).thenReturn(results);
        Assert.assertEquals(results, repository.callProc(procedure, operations));
    }

    @Test
    public void testGet1() {
        List<String> groupByNames = new ArrayList<>();
        groupByNames.add("state");
        groupByNames.add("street");
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("Sample name", Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ));
        operations.add(new Operation("Song", Operation.TYPES.STRING, "lyrics", Operation.OPERATORS.EQ));
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Tuple> cq = mock(CriteriaQuery.class);
        Root<SampleEntity> entityRoot = mock(Root.class);
        TypedQuery<Tuple> typedQuery = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Tuple.class)).thenReturn(cq);
        when(cq.from(SampleEntity.class)).thenReturn(entityRoot);
        List<Predicate> predicates = mockGetPredicate(operations, cb, entityRoot);
        Predicate allPredicates = mock(Predicate.class);
        Expression countExp = mock(Expression.class);
        when(cb.and(predicates.toArray(new Predicate[0]))).thenReturn(allPredicates);
        when(cq.where(allPredicates)).thenReturn(cq);
        List<Object[]> result = new ArrayList<>();

        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Tuple t = mock(Tuple.class);
            Object[] row = new Object[2];
            Object o = new Object();
            int count = 10;
            when(t.get(0)).thenReturn(o);
            when(t.get(1)).thenReturn(count);
            row[0] = o;
            row[1] = count;
            result.add(row);
            tuples.add(t);
        }
        when(this.entityManager.createQuery(cq)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(tuples);
        Assert.assertEquals(result.size(), repository.get(groupByNames, operations, SampleEntity.class).size());
    }

    @Test
    public void getEntityManager() {
        Assert.assertEquals(entityManager, repository.getEntityManager());
    }

    private List<Predicate> mockGetPredicate(List<Operation> conditions,
                                  CriteriaBuilder criteriaBuilder, Root<SampleEntity> entity) {
        int i = 0;
        List<Predicate> predicates = new ArrayList<>();
        for(Operation operand : conditions) {
            ParameterExpression param = mock(ParameterExpression.class);
            when(criteriaBuilder.parameter(operand.getOperandType(), operand.getName() + i)).thenReturn(param);
            Predicate predicate = mock(Predicate.class);
            predicates.add(predicate);
            Path path = mock(Path.class);
            when(entity.get(operand.getName())).thenReturn(path);
            switch(operand.getOperator()) {
                case EQ:
                    when(criteriaBuilder.equal(path, param)).thenReturn(predicate);
                    break;
                case GE:
                    when(criteriaBuilder.greaterThanOrEqualTo(path, param)).thenReturn(predicate);
                    break;
                case LE:
                    when(criteriaBuilder.lessThanOrEqualTo(path, param)).thenReturn(predicate);
                    break;
                case LT:
                    when(criteriaBuilder.lessThan(path, param)).thenReturn(predicate);
                    break;
                case GT:
                    when(criteriaBuilder.greaterThan(path, param)).thenReturn(predicate);
                    break;
                case LIKE:
                    when(criteriaBuilder.like(path, param)).thenReturn(predicate);
                    break;
                case IS_NOT_NULL:
                    when(criteriaBuilder.isNotNull(path)).thenReturn(predicate);
                    break;
                case IS_NULL:
                    when(criteriaBuilder.isNull(path)).thenReturn(predicate);
                    break;
            }
            i++;
        }
        return predicates;
    }
}

class SampleEntity implements Serializable{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class TestClass extends MySqlRepository <SampleEntity> {

}