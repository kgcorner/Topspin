package com.kgcorner.topspin.dtos;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class AbstractStoreTest {
    @Test
    public void testAbstractStore() {
        String id = "id";
        String description = "description";
        String name = "name";
        DemoStore demoStore = new DemoStore();
        demoStore.setDescription(description);
        demoStore.setId(id);
        demoStore.setName(name);
        assertEquals(name, demoStore.getName());
        assertEquals(id, demoStore.getId());
        assertEquals(description, demoStore.getDescription());
    }
    class DemoStore extends AbstractStore {}
}