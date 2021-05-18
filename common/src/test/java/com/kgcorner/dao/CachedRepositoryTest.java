package com.kgcorner.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class CachedRepositoryTest {

    private CachedRepository repository;

    @Before
    public void setup() {
        repository = new CachedRepository();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void create() {
        repository.create(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void update() {
        repository.update(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        repository.remove(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        repository.remove(null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove1() {
        repository.remove(null, null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void get() {
        repository.get("id", null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getIn() {
        repository.getIn(null, "cols",  null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIn() {
        repository.getIn(null, null, null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAll() {
        repository.getAll(CachedRepository.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAll() {
        repository.getAll(new Procedure("new Proc", null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAll1() {
        repository.getAll(0,0, Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAll2() {
        repository.getAll(null, Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGet() {
        repository.get(Collections.emptyList(), null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAll3() {
        repository.get(Collections.emptyList(), Collections.emptyList(), Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCroppedList() {
        repository.getCroppedList(Collections.emptyList(), 0, 0, Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCroppedList() {
        repository.getCroppedList(Collections.emptyList(), 0, 0, Collections.emptyList(),
            Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAll4() {
        repository.getAll(Collections.emptyList(), 0, 0, Collections.emptyList(),
            Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void runSelectNativeQuery() {
        repository.runSelectNativeQuery("Query", null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void runUpdateNativeQuery() {
        repository.runSelectNativeQuery("Query", null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCount() {
        repository.getCount(Collections.EMPTY_LIST, Object.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void callProc() {
        repository.callProc("new Proc", Collections.emptyList());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGet1() {
        repository.get(Collections.emptyList(), Collections.emptyList(), Object.class);
    }
}