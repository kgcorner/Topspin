package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/04/21
 */

public class AbstractCategoryTest {

    private DemoCategory categoryDTO;
    @Before
    public void setUp() throws Exception {
        categoryDTO = new DemoCategory();
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
        Category parent = new DemoCategory();
        categoryDTO.setParent(parent);
        Assert.assertEquals(parent, categoryDTO.getParent());
    }

    @Test
    public void setTagline() {
        String tagline = "tagline";
        categoryDTO.setTagline(tagline);
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
        public List<Category> getChildren() {
            return Collections.emptyList();
        }
    }
}