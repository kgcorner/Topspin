package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ServletUriComponentsBuilder.class)
public class CategoryResourceTest {

    private CategoryResource categoryResource;
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        categoryResource = new CategoryResource();
        categoryService = PowerMockito.mock(CategoryService.class);
        Whitebox.setInternalState(categoryResource, "categoryService", categoryService);
    }

    @Test
    public void get() {
        String id = "id";
        CategoryDTO category = new CategoryDTO();
        PowerMockito.when(categoryService.getCategory(id)).thenReturn(category);
        Assert.notNull(categoryResource.get(id));
    }

    @Test
    public void getAll() {
        int page = 0;
        int maxCount = 10;
        PowerMockito.mockStatic(ServletUriComponentsBuilder.class);
        ServletUriComponentsBuilder servletContext = PowerMockito.mock(ServletUriComponentsBuilder.class);
        PowerMockito.when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(servletContext);
        UriComponents uri = PowerMockito.mock(UriComponents.class);
        PowerMockito.when(servletContext.build()).thenReturn(uri);
        PowerMockito.when(uri.toUriString()).thenReturn("HATEOS url");
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(new CategoryDTO());
        PowerMockito.when(categoryService.getAllCategories(page, maxCount)).thenReturn(categoryDTOS);
        Assert.notNull(categoryResource.getAll(page, maxCount));
    }

    @Test
    public void create() {
        String name = "name";
        String description = "description";
        CategoryDTO categoryDTO = new CategoryDTO();
        PowerMockito.when(categoryService.createCategory(name, description)).thenReturn(categoryDTO);
        Assert.notNull(categoryResource.create(name, description));
    }

    @Test
    public void update() {
        String name = "name";
        String id = "id";
        String description = "description";
        CategoryDTO categoryDTO = new CategoryDTO();
        PowerMockito.when(categoryService.updateCategory(id, name, description)).thenReturn(categoryDTO);
        Assert.notNull(categoryResource.update(id, name, description));
    }
}