package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.junit.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.ArrayList;
import java.util.Collections;
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
    private DemoCategory demoCategory;
    @Before
    public void setUp() throws Exception {
        categoryResource = new CategoryResource();
        demoCategory = new DemoCategory();
        categoryService = PowerMockito.mock(CategoryService.class);
        Whitebox.setInternalState(categoryResource, "categoryService", categoryService);
    }

    @Test
    public void get() {
        String id = "id";
        CategoryDTO category = new CategoryDTO(demoCategory);
        PowerMockito.when(categoryService.getCategory(id)).thenReturn(category);
        Assert.assertNotNull(categoryResource.get(id));
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
        categoryDTOS.add(new CategoryDTO(demoCategory));
        PowerMockito.when(categoryService.getAllCategories(page, maxCount)).thenReturn(categoryDTOS);
        Assert.assertNotNull(categoryResource.getAll(page, maxCount));
    }

    @Test
    public void create() {
        String name = "name";
        String description = "description";
        CategoryDTO categoryDTO = new CategoryDTO(demoCategory);
        PowerMockito.when(categoryService.createCategory(name, description)).thenReturn(categoryDTO);
        Assert.assertNotNull(categoryResource.create(name, description));
    }

    @Test
    public void update() {
        String name = "name";
        String id = "id";
        String description = "description";
        CategoryDTO categoryDTO = new CategoryDTO(demoCategory);
        PowerMockito.when(categoryService.updateCategory(id, name, description)).thenReturn(categoryDTO);
        Assert.assertNotNull(categoryResource.update(id, name, description));
    }

    class DemoCategory extends AbstractCategory {
        private String cateId;

        public void setCategoryId(String cateId) {
            this.cateId = cateId;
        }
        @Override
        public String getCategoryId() {
            return cateId;
        }

        @Override
        public List<Category> getChildren() {
            return Collections.emptyList();
        }
    }
}