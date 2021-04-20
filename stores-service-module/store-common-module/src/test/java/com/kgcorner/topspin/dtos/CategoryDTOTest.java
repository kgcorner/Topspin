package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class CategoryDTOTest {

    private CategoryDTO categoryDTO;

    @Before
    public void setUp() throws Exception {
        categoryDTO = new CategoryDTO();
    }

    @Test
    public void setCategoryId() {
        String categoryId = "categoryId";
        categoryDTO.setCategoryId(categoryId);
        Assert.assertEquals(categoryId, categoryDTO.getCategoryId());
    }

    @Test
    public void setName() {
        String name = "name";
        categoryDTO.setName(name);
        Assert.assertEquals(name, categoryDTO.getName());
    }

    @Test
    public void setDescription() {
        String description = "description";
        categoryDTO.setDescription(description);
        Assert.assertEquals(description, categoryDTO.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        categoryDTO.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, categoryDTO.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        categoryDTO.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, categoryDTO.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbnailImage = "thumbnailImage";
        categoryDTO.setThumbnailImage(thumbnailImage);
        Assert.assertEquals(thumbnailImage, categoryDTO.getThumbNailImage());
    }

    @Test
    public void setLargeImage() {
        String largerImage = "largerImage";
        categoryDTO.setLargeImage(largerImage);
        Assert.assertEquals(largerImage, categoryDTO.getLargeImage());
    }

    @Test
    public void setParent() {
        Category parent = new CategoryDTO();
        categoryDTO.setParent(parent);
        Assert.assertEquals(parent, categoryDTO.getParent());
    }

    @Test
    public void setTagline() {
        String tagline = "tagline";
        categoryDTO.setTagline(tagline);
        Assert.assertEquals(tagline, categoryDTO.getTagLine());
    }

    @Test
    public void getChildren() {
        int maxCount = 10;
        for (int i = 0; i < maxCount; i++) {
            categoryDTO.addChild(new CategoryDTO());
        }
        Assert.assertEquals(maxCount, categoryDTO.getChildren().size());
    }
}