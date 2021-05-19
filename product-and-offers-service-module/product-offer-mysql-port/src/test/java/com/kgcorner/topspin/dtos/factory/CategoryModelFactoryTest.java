package com.kgcorner.topspin.dtos.factory;

import com.kgcorner.topspin.dtos.Category;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class CategoryModelFactoryTest {

    @Test
    public void testCategoryModelFactory() {
        String name = "name";
        String description = "description";
        String id = "id";
        Category category = new CategoryModelFactory().createCategory(id, name, description);
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
    }
}