package com.kgcorner.dao;

import com.kgcorner.cache.CacheHandler;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
/**
 * Description : Unit test for MysqlRepository
 * Author: kumar
 * Created on : 18/11/19
 */

public class MySqlRepositoryTest {

    private MySqlRepository<DummySQLModel> repository;
    private EntityManager mockedEntityManager;
    private CacheHandler mockedCachedHandler;

    @Before
    public void setUp() throws Exception {
        mockedEntityManager = PowerMockito.mock(EntityManager.class);
        mockedCachedHandler = PowerMockito.mock(CacheHandler.class);
        repository = new DummyMySqlRepository(mockedEntityManager);
        Whitebox.setInternalState(repository, "cacheHandler", mockedCachedHandler);
    }

    @Test
    public void getCacheHandler() {
        assertNotNull(repository.getCacheHandler());
    }

    @Test
    public void getAll() {
        List<DummySQLModel> list = prepareDummyList(9);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.setFirstResult(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.setMaxResults(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(list);
        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);
        List<DummySQLModel> response = repository.getAll( DummySQLModel.class);
        assertNotNull("Response is null", response);
        assertEquals("Response is not matching", list, response);
    }

    @Test
    public void testGetAll() {
        List<DummySQLModel> list = prepareDummyList(9);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.setFirstResult(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.setMaxResults(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(list);
        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);
        List<DummySQLModel> response = repository.getAll( 1, DummySQLModel.class);
        assertNotNull("Response is null", response);
        assertEquals("Response is not matching", list, response);
    }

    @Test
    public void testGetAll1() {
        List<DummySQLModel> list = prepareDummyList(9);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.setFirstResult(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.setMaxResults(anyInt())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(list);
        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);
        List<DummySQLModel> response = repository.getAll( 1,9,DummySQLModel.class);
        assertNotNull("Response is null", response);
        assertEquals("Response is not matching", list, response);
    }

    @Test
    public void getByIdWithCache() {
        String id = "1";
        DummySQLModel model = new DummySQLModel("1");
        when(mockedCachedHandler.getValue(DummySQLModel.class.getName() + "_" + id, DummySQLModel.class))
            .thenReturn(model);
        DummySQLModel response = repository.getById("1", DummySQLModel.class);
        assertNotNull(response);
        assertEquals(model.getId(), response.getId());
    }

    @Test
    public void getByIdNoRec() {
        String id = "1";
        when(mockedCachedHandler.getValue(DummySQLModel.class.getName() + "_" + id, DummySQLModel.class))
            .thenReturn(null);
        when(mockedEntityManager.find(DummySQLModel.class, "1")).thenThrow(new NoResultException());
        DummySQLModel response = repository.getById("1", DummySQLModel.class);
        assertNull(response);
    }

    @Test
    public void getByIdWithoutCache() {
        String id = "1";
        DummySQLModel model = new DummySQLModel("1");
        when(mockedCachedHandler.getValue(DummySQLModel.class.getName() + "_" + id, DummySQLModel.class))
            .thenReturn(null);
        when(mockedEntityManager.find(DummySQLModel.class, "1")).thenReturn(model);
        DummySQLModel response = repository.getById("1", DummySQLModel.class);
        assertNotNull(response);
        assertEquals(model.getId(), response.getId());
    }

    /**
     * TODO: Implement tests before implementing method
     */
    @Test
    public void getByKey() {
        assertNull(repository.getByKey(null, null, null));
    }

    /**
     * TODO: Implement tests before implementing method
     */
    @Test
    public void create() {
        assertNull(repository.create(null));
    }

    /**
     * TODO: Implement tests before implementing method
     */
    @Test
    public void update() {
        assertNull(repository.update(null));
    }

    private List<DummySQLModel> prepareDummyList(int i) {
        List<DummySQLModel> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            DummySQLModel model = new DummySQLModel(j+"");
            list.add(model);
        }
        return list;
    }
}

class DummyMySqlRepository extends MySqlRepository<DummySQLModel> {
    public DummyMySqlRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }
}

class DummySQLModel implements Serializable {
    private String id;
    public DummySQLModel(){}
    public DummySQLModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}