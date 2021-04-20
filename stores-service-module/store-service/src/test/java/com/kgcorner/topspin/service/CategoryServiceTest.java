package com.kgcorner.topspin.service;

import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.factory.CategoryFactory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public class CategoryServiceTest {
    private CategoryFactory categoryFactory;
    private CategoryPersistenceLayer persistenceLayer;
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        categoryService = new CategoryService();
        categoryFactory = PowerMockito.mock(CategoryFactory.class);
        persistenceLayer = PowerMockito.mock(CategoryPersistenceLayer.class);
        Whitebox.setInternalState(categoryService, "categoryFactory", categoryFactory);
        Whitebox.setInternalState(categoryService, "categoryPersistenceLayer", persistenceLayer);
    }

    @Test
    public void getCategory() {
        CategoryDTO category = new CategoryDTO();
        category.setName("name");
        String id = "id";
        PowerMockito.when(persistenceLayer.getCategory(id)).thenReturn(category);
        Category result = categoryService.getCategory(id);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof CategoryDTO);
        Assert.assertEquals(category.getName(), result.getName());
    }

    @Test
    public void createCategory() {
        String name = "name";
        String description = "description";
        CategoryDTO category = new CategoryDTO();
        category.setName(name);
        category.setDescription(description);
        PowerMockito.when(categoryFactory.createCategoryModel(name, description)).thenReturn(category);
        PowerMockito.when(persistenceLayer.createCategory(category)).thenReturn(category);
        Category result = categoryService.createCategory(name, description);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof CategoryDTO);
        Assert.assertEquals(name, result.getName());
        Assert.assertEquals(description, result.getDescription());
    }

    @Test
    public void updateCategory() {
        String name = "name";
        String description = "description";
        CategoryDTO category = new CategoryDTO();
        category.setName(name);
        category.setDescription(description);
        String id = "id";
        PowerMockito.when(categoryFactory.createCategoryModel(name, description)).thenReturn(category);
        PowerMockito.when(persistenceLayer.getCategory(id)).thenReturn(category);
        PowerMockito.doNothing().when(persistenceLayer).updateCategory(category, id);
        Category result = categoryService.updateCategory(id, name, description);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof CategoryDTO);
        Assert.assertEquals(name, result.getName());
        Assert.assertEquals(description, result.getDescription());
    }

    @Test
    public void getAllCategories() {
        int page = 1;
        int maxItem = 10;
        List<Category> categories = new ArrayList<>();
        categories.add(new CategoryDTO());
        PowerMockito.when(persistenceLayer.getAllCategories(page, maxItem)).thenReturn(categories);
        List<CategoryDTO> result = categoryService.getAllCategories(page, maxItem);
        Assert.assertNotNull(result);
        Assert.assertEquals(categories.size(), result.size());
    }
}