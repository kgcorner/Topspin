package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class StoreRefTest {

    @Test
    public void testStoreRef() {
        String id = "id";
        String description = "description";
        String name = "name";
        StoreRef storeRef = new StoreRef();
        storeRef.setDescription(description);
        storeRef.setId(id);
        storeRef.setName(name);
        assertEquals(name, storeRef.getName());
        assertEquals(id, storeRef.getId());
        assertEquals(description, storeRef.getDescription());
    }

    @Test
    public void testAllArgConstructor() {
        String id = "id";
        String description = "description";
        String name = "name";
        String maxCashback ="8%";
        int offersCount = 10;
        StoreRef storeRef = new StoreRef(id, name, description, maxCashback, offersCount);
        assertEquals(name, storeRef.getName());
        assertEquals(id, storeRef.getId());
        assertEquals(description, storeRef.getDescription());
        assertEquals(maxCashback, storeRef.getMaxCashback());
        assertEquals(offersCount, storeRef.getOffersCount());
    }
}