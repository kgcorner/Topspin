package com.kgcorner.topspin.service;

import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */

public class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryPersistenceLayer persistenceLayer;

    @Before
    public void setUp() throws Exception {
        persistenceLayer = mock(CategoryPersistenceLayer.class);
        categoryService = new CategoryService();
        Whitebox.setInternalState(categoryService, "categoryPersistenceLayer", persistenceLayer);
    }

    @Test
    public void createCategory() {
        String name = "amazon";
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        AbstractCategory createdCategory = mock(AbstractCategory.class);
        when(createdCategory.getName()).thenReturn(name);

        when(persistenceLayer.createCategory(categoryDTO)).thenReturn(createdCategory);
        CategoryDTO category = categoryService.createCategory(categoryDTO);
        assertEquals(name, category.getName());
    }

    @Test
    public void updateCategory() {
        String categoryId = "categoryID";
        String name = "amazon";
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        categoryDTO.setName(name);
        AbstractCategory updatedCategory = mock(AbstractCategory.class);
        when(updatedCategory.getName()).thenReturn(name);
        when(persistenceLayer.updateCategory(categoryDTO, categoryId)).thenReturn(updatedCategory);
        CategoryDTO category = categoryService.updateCategory(categoryDTO, categoryId);
        assertEquals(name, category.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCategoryWithWrongId() {
        String categoryId = "categoryID";
        String name = "amazon";
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        categoryDTO.setName(name);
        AbstractCategory updatedCategory = mock(AbstractCategory.class);
        when(updatedCategory.getName()).thenReturn(name);
        when(persistenceLayer.updateCategory(categoryDTO, categoryId)).thenThrow(new IllegalArgumentException());
        categoryService.updateCategory(categoryDTO, categoryId);
    }

    @Test
    public void getCategory() {
        String categoryId = "categoryID";
        String name = "amazon";
        AbstractCategory foundCategory = mock(AbstractCategory.class);
        when(foundCategory.getName()).thenReturn(name);
        when(persistenceLayer.getCategory(categoryId)).thenReturn(foundCategory);
        CategoryDTO category = categoryService.getCategory(categoryId);
        assertEquals(name, category.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCategoryNoCategory() {
        String categoryId = "categoryID";
        when(persistenceLayer.getCategory(categoryId)).thenReturn(null);
        categoryService.getCategory(categoryId);
    }

    @Test
    public void getAllCategories() {
        String name = "amazon";
        int page = 1;
        int size = 10;
        List<AbstractCategory> categories = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            AbstractCategory foundCategory = mock(AbstractCategory.class);
            when(foundCategory.getName()).thenReturn(name);
            categories.add(foundCategory);
        }
        when(persistenceLayer.getAllCategories(page, size)).thenReturn(categories);
        List<CategoryDTO> allCategorys = categoryService.getAllCategories(page, size);
        assertEquals(size, allCategorys.size());
        assertEquals(name, allCategorys.get(1).getName());
    }

    @Test
    public void deleteCategory() {
        String categoryId = "categoryId";
        categoryService.deleteCategory(categoryId);
        Mockito.verify(persistenceLayer).deleteCategory(categoryId);
    }
}