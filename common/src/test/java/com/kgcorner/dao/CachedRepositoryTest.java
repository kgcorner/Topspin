package com.kgcorner.dao;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Description : Unit Tests for Cached repository
 * Author: kumar
 * Created on : 18/11/19
 * TODO: Modify before making changes in the CacheRepository class
 */



public class CachedRepositoryTest {

    private CachedRepository<DummyModel> cachedRepository;

    @Before
    public void setUp() throws Exception {
        cachedRepository = new CachedRepository();
    }

    @Test
    public void getAll() {
        List<DummyModel> all = cachedRepository.getAll(DummyModel.class);
        assertNotNull(all);
        assertEquals(0, all.size());
    }

    @Test
    public void testGetAll() {
        List<DummyModel> all = cachedRepository.getAll(1, DummyModel.class);
        assertNotNull(all);
        assertEquals(0, all.size());
    }

    @Test
    public void testGetAll1() {
        List<DummyModel> all = cachedRepository.getAll(1,1,DummyModel.class);
        assertNotNull(all);
        assertEquals(0, all.size());
    }

    @Test
    public void getById() {
        assertNull(cachedRepository.getById(null, DummyModel.class));
    }

    @Test
    public void getByKey() {
        assertNull(cachedRepository.getByKey(null, null, DummyModel.class));
    }

    @Test
    public void create() {
        assertNull(cachedRepository.create(new DummyModel()));
    }

    @Test
    public void update() {
        assertNull(cachedRepository.update(new DummyModel()));
    }

    class DummyModel implements Serializable {

    }
}

