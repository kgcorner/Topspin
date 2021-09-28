package com.kgcorner.topspin.service;

import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.model.CategoryDTO;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AwsServices.class})
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

    @Test
    public void updateBannerAndLogo() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);

        InputStream logoInputStream = mock(InputStream.class);
        InputStream bannerInputStream = mock(InputStream.class);
        InputStream thumbnailInputStream = mock(InputStream.class);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("amazing category");

        String bannerFileName = "banner.jpg";
        String logoFileName = "logo.jpg";
        String thumbnailFileName = "thumbnail.jpg";
        String categoryId = "categoryId";

        when(banner.getOriginalFilename()).thenReturn(bannerFileName);
        when(logo.getOriginalFilename()).thenReturn(logoFileName);
        when(thumbnail.getOriginalFilename()).thenReturn(thumbnailFileName);

        when(banner.getInputStream()).thenReturn(bannerInputStream);
        when(logo.getInputStream()).thenReturn(logoInputStream);
        when(thumbnail.getInputStream()).thenReturn(thumbnailInputStream);
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        when(persistenceLayer.updateCategory(any(CategoryDTO.class), anyString())).thenReturn(categoryDTO);
        CategoryDTO categoryDTO1 = categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo);
        Assert.assertNotNull(categoryDTO1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidBanner() throws IOException {
        String categoryId = "categoryId";
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        when(banner.getOriginalFilename()).thenReturn(null);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("amazing category");
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidLogo() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String categoryId = "categoryId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn(null);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("amazing category");
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidThumbnail() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String categoryId = "categoryId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn("logo.jpg");
        when(thumbnail.getOriginalFilename()).thenReturn(null);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("amazing category");
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidInvalidStream() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String categoryId = "categoryId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn("logo.jpg");
        when(thumbnail.getOriginalFilename()).thenReturn("thumbmnail.jpg");
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("amazing category");
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        when(banner.getInputStream()).thenThrow(IOException.class);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        categoryService.updateBannerAndLogo(categoryId, thumbnail, banner, logo);
    }

    @Test
    public void addChildren() {
        String categoryId = "categoryId";
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        when(persistenceLayer.getCategory(categoryId)).thenReturn(categoryDTO);
        List<CategoryDTO> children = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String childCategoryId = "categoryId" + i;
            CategoryDTO childCategory = new CategoryDTO();
            childCategory.setCategoryId(childCategoryId);
            when(persistenceLayer.getCategory(childCategoryId)).thenReturn(childCategory);
            children.add(childCategory);
        }
        when(persistenceLayer.updateCategory(categoryDTO, categoryId)).thenReturn(categoryDTO);
        CategoryDTO categoryDTO1 = categoryService.addChildren(categoryId, children);
        assertNotNull(categoryDTO1);
        assertEquals(categoryId, categoryDTO1.getCategoryId());
        assertEquals(children.size(), categoryDTO1.getChildren().size());
    }
}