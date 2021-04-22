package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.model.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class CategoryDTOTest {

    private CategoryDTO categoryDTO;
    private DemoCategory category;
    @Before
    public void setUp() {
        category = new DemoCategory();
        categoryDTO = new CategoryDTO(category);
    }

    @Test
    public void getChildren() {
        int maxCount = 10;
        for (int i = 0; i < maxCount; i++) {
            categoryDTO.addChildren(new CategoryDTO(category));
        }
        Assert.assertEquals(maxCount, categoryDTO.getChildren().size());
    }

    @Test
    public void setCategoryId() {
        String categoryId = "categoryId";
        category.setCategoryId(categoryId);
        Assert.assertEquals(categoryId, categoryDTO.getCategoryId());
    }

    @Test
    public void setName() {
        String name = "name";
        category.setName(name);
        Assert.assertEquals(name, categoryDTO.getName());
    }

    @Test
    public void setDescription() {
        String description = "description";
        category.setDescription(description);
        Assert.assertEquals(description, categoryDTO.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        category.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, categoryDTO.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        category.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, categoryDTO.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbnailImage = "thumbnailImage";
        category.setThumbnailImage(thumbnailImage);
        Assert.assertEquals(thumbnailImage, categoryDTO.getThumbNailImage());
    }

    @Test
    public void setLargeImage() {
        String largerImage = "largerImage";
        category.setLargeImage(largerImage);
        Assert.assertEquals(largerImage, categoryDTO.getLargeImage());
    }

    @Test
    public void setParent() {
        Category parent = new DemoCategory();
        category.setParent(parent);
        Assert.assertEquals(parent, categoryDTO.getParent());
    }

    @Test
    public void setTagline() {
        String tagline = "tagline";
        category.setTagline(tagline);
        Assert.assertEquals(tagline, categoryDTO.getTagLine());
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
        public List<? extends Category> getChildren() {
            return Collections.emptyList();
        }
    }
}