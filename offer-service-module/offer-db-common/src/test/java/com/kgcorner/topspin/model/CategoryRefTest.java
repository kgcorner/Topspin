package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class CategoryRefTest {

    @Test
    public void testCategoryRef() {
        String id = "id";
        String description = "description";
        String name = "name";
        CategoryRef categoryRef = new CategoryRef();
        categoryRef.setName(name);
        categoryRef.setId(id);
        categoryRef.setDescription(description);
        assertEquals(id, categoryRef.getId());
        assertEquals(description, categoryRef.getDescription());
        assertEquals(name, categoryRef.getName());
    }

}