package com.kgcorner.topspin.dtos.factory;

import com.kgcorner.topspin.dtos.Store;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class StoreModelFactoryTest {

    @Test
    public void testStoreModelFactory() {
        String name = "name";
        String id = "id";
        String description = "description";
        Store store = new StoreModelFactory().createStore(id, name, description);
        assertEquals(id, store.getId());
        assertEquals(name, store.getName());
        assertEquals(description, store.getDescription());

    }
}