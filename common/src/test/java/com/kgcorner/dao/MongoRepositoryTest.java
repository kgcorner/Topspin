package com.kgcorner.dao;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.*;
/**
 * Description : Unit test for MongoRepository
 * Author: kumar
 * Created on : 18/11/19
 */

public class MongoRepositoryTest {

    private MongoTemplate mockedMongoTemplate;
    private MongoRepository<DummyModel> repository;

    @Before
    public void setUp() throws Exception {
        mockedMongoTemplate = PowerMockito.mock(MongoTemplate.class);
        repository = new DummyMongoRepository(mockedMongoTemplate);
    }

    @Test
    public void getAll() {
        List list = prepareDummyList(9);
        when(mockedMongoTemplate.find(any(), any())).thenReturn(list);
        List response = repository.getAll(1, DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("Lists are not euqal", list, response);
    }

    @Test
    public void getById() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(model);
        DummyModel response = repository.getById("id", DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void getByKey() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(model);
        DummyModel response = repository.getByKey("id", "1", DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void create() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.insert(any(DummyModel.class))).thenReturn(model);
        DummyModel response = repository.create(model);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void update() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.save(any(DummyModel.class))).thenReturn(model);
        DummyModel response = repository.update(model);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    private List<DummyModel> prepareDummyList(int i) {
        List<DummyModel> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            DummyModel model = new DummyModel(j+"");
            list.add(model);
        }
        return list;
    }
}

class DummyMongoRepository extends MongoRepository<DummyModel> {
    public DummyMongoRepository(MongoTemplate template){
        this.template = template;
    }
}

class DummyModel implements Serializable {
    private String id;
    public DummyModel(){}
    public DummyModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}