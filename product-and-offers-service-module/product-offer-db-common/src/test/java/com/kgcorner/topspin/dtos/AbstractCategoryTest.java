package com.kgcorner.topspin.dtos;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class AbstractCategoryTest {

    @Test
    public  void testAbstractCategory() {
        AbstractCategory category = new AbstractCategory() {};
        String id = "id";
        String description = "description";
        String name = "name";
        category.setDescription(description);
        category.setId(id);
        category.setName(name);
        assertEquals(name, category.getName());
        assertEquals(id, category.getId());
        assertEquals(description, category.getDescription());
    }


}