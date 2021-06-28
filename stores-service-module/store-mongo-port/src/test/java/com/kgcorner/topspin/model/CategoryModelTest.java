package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Test;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/01/21
 */

public class CategoryModelTest {

    @Test
    public void shouldCreateAndSetMembersOfCategoryObject() {
        CategoryModel categoryModel = new CategoryModel();
        Assert.assertNotNull(categoryModel);
        final String categoryId = "categoryId";
        final String name = "name";
        final String description = "description";
        final String longDescription = "longDescription";
        final String bannerImage = "bannerImage";
        final String thumbnailImage = "thumbnailImage";
        final String largeImage = "largeImage";
        final String tagLine = "tagline";
        final Category parent = new CategoryModel();
        categoryModel.setCategoryId(categoryId);
        categoryModel.setParent(parent);
        categoryModel.setTagline(tagLine);
        categoryModel.setLargeImage(largeImage);
        categoryModel.setThumbnailImage(thumbnailImage);
        categoryModel.setBannerImage(bannerImage);
        categoryModel.setLongDescription(longDescription);
        categoryModel.setDescription(description);
        categoryModel.setName(name);
        Assert.assertEquals(name, categoryModel.getName());
        Assert.assertEquals(description, categoryModel.getDescription());
        Assert.assertEquals(longDescription, categoryModel.getLongDescription());
        Assert.assertEquals(tagLine, categoryModel.getTagLine());
        Assert.assertEquals(bannerImage, categoryModel.getBannerImage());
        Assert.assertEquals(thumbnailImage, categoryModel.getThumbNailImage());
        Assert.assertEquals(largeImage, categoryModel.getLargeImage());
        Assert.assertEquals(parent, categoryModel.getParent());
        Assert.assertEquals(categoryId, categoryModel.getCategoryId());
        Assert.assertNotNull(categoryModel.getChildren());
        Assert.assertEquals(0, categoryModel.getChildren().size());
        categoryModel.addCategory(new CategoryModel());
        Assert.assertEquals(1, categoryModel.getChildren().size());
    }
}