package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/10/21
 */

public class LocalCategoryClientTest {

    private LocalCategoryClient localCategoryClient;
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        categoryService = mock(CategoryService.class);
        localCategoryClient = new LocalCategoryClient();
        Whitebox.setInternalState(localCategoryClient, "categoryService", categoryService);
    }

    @Test
    public void getCategory() {
        String categoryId = "categoryId";
        localCategoryClient.getCategory(categoryId);
        verify(categoryService).getCategory(categoryId);
    }

    @Test
    public void getAll() {
        int page = 0;
        int count = 100;
        localCategoryClient.getAll(page, count);
        verify(categoryService).getAllCategories(page, count);
    }

    @Test
    public void create() {
        CategoryDTO categoryDTO = new CategoryDTO();
        localCategoryClient.create(categoryDTO);
        verify(categoryService).createCategory(categoryDTO);
    }

    @Test
    public void update() {
        String categoryId = "categoryId";
        CategoryDTO categoryDTO = new CategoryDTO();
        localCategoryClient.update(categoryId, categoryDTO);
        verify(categoryService).updateCategory(categoryDTO, categoryId);
    }
}