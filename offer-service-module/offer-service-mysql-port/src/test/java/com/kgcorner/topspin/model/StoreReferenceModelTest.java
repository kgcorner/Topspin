package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class StoreReferenceModelTest {
    @Test
    public void testStoreModelRef() {
        String id = "id";
        String description = "description";
        String name = "name";
        String maxCashback = "8%";
        StoreReferenceModel storeRef = new StoreReferenceModel();
        storeRef.setDescription(description);
        storeRef.setId(id);
        storeRef.setName(name);
        storeRef.setMaxCashback(maxCashback);
        assertEquals(name, storeRef.getName());
        assertEquals(id, storeRef.getId());
        assertEquals(description, storeRef.getDescription());
        assertEquals(maxCashback, storeRef.getMaxCashback());
    }
}