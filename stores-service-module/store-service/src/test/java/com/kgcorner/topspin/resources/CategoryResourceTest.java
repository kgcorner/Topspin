package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.model.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/08/21
 */

public class CategoryResourceTest {

    private CategoryService categoryService;
    private CategoryResource categoryResource;

    @Before
    public void setUp() throws Exception {
        categoryResource = new CategoryResource();
        categoryService = mock(CategoryService.class);
        Whitebox.setInternalState(categoryResource, "categoryService", categoryService);
    }

    @Test
    public void createCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        String categoryId = "categoryId";
        categoryDTO.setCategoryId(categoryId);
        when(categoryService.createCategory(categoryDTO)).thenReturn(categoryDTO);
        ResponseEntity<CategoryDTO> categoryResponse = categoryResource.create(categoryDTO);
        assertEquals(HttpStatus.CREATED, categoryResponse.getStatusCode());
        assertEquals(categoryResponse.getBody(), categoryDTO);
        assertTrue(categoryDTO.getLinks().get(0).getHref().contains(categoryId));
    }

    @Test
    public void put() {
        CategoryDTO categoryDTO = new CategoryDTO();
        String categoryId = "categoryId";
        categoryDTO.setCategoryId(categoryId);
        when(categoryService.updateCategory(categoryDTO, categoryId)).thenReturn(categoryDTO);
        ResponseEntity<CategoryDTO> categoryResponse = categoryResource.update(categoryId, categoryDTO);
        assertEquals(HttpStatus.OK, categoryResponse.getStatusCode());
        assertEquals(categoryResponse.getBody(), categoryDTO);
        assertTrue(categoryDTO.getLinks().get(0).getHref().contains(categoryId));
    }

    @Test
    public void get() {
        CategoryDTO categoryDTO = new CategoryDTO();
        String categoryId = "categoryId";
        categoryDTO.setCategoryId(categoryId);
        when(categoryService.getCategory(categoryId)).thenReturn(categoryDTO);
        ResponseEntity<CategoryDTO> categoryResponse = categoryResource.get(categoryId);
        assertEquals(HttpStatus.OK, categoryResponse.getStatusCode());
        assertEquals(categoryResponse.getBody(), categoryDTO);
        assertTrue(categoryDTO.getLinks().get(0).getHref().contains(categoryId));
    }

    @Test
    public void getAllCategorys() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        int page = 1;
        int size = 10;

        for (int i = 0; i < size; i++) {
            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId("categoryId");
            dto.setName("Category");
            categoryDTOS.add(dto);
        }
        when(categoryService.getAllCategories(page, size)).thenReturn(categoryDTOS);
        ResponseEntity<Resources<CategoryDTO>> allCategorys = categoryResource.getAll(page, size);
        assertEquals(HttpStatus.OK, allCategorys.getStatusCode());
        Resources<CategoryDTO> resources = new Resources<>(categoryDTOS);
        assertEquals(((Resources<CategoryDTO>) allCategorys.getBody()).getContent().size(), size);

    }

    @Test
    public void deleteCategory() {
        String categoryId = "categoryId";
        categoryResource.deleteCategory(categoryId);
        Mockito.verify(categoryService).deleteCategory(categoryId);
    }

    @Test
    public void uploadBannerAndLogo() {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String categoryId = "categoryId";
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        when(categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo)).thenReturn(categoryDTO);
        assertNotNull(categoryResource.uploadBannerAndLogo(categoryId, banner, logo, thumbnail));
    }

    @Test
    public void addChildrenCategory() {
        String categoryId = "categoryId";
        CategoryDTO[] categoryDTOS = new CategoryDTO[2];
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        when(categoryService.addChildren(categoryId, categoryDTOS)).thenReturn(categoryDTO);
        ResponseEntity<CategoryDTO> categoryDTOResponseEntity = categoryResource.addChildrenCategory(categoryId,
            categoryDTOS);
        CategoryDTO body = categoryDTOResponseEntity.getBody();
        assertEquals(categoryId, body.getCategoryId());
    }
}