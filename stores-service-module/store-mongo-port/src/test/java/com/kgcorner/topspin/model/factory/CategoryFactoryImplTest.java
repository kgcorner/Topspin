package com.kgcorner.topspin.model.factory;

import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.CategoryModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/01/21
 */

public class CategoryFactoryImplTest {

    private CategoryFactoryImpl categoryFactory;
    @Before
    public void setup() {
        categoryFactory = new CategoryFactoryImpl();
    }

    @Test
    public void createCategoryModelNoArgConstructor() {
        Category category = categoryFactory.createCategoryModel();
        Assert.assertNotNull(category);
        Assert.assertTrue(category instanceof CategoryModel);
    }

    @Test
    public void testCreateCategoryModelWith2Arg() {
        String name = "name";
        String description = "description";
        Category category = categoryFactory.createCategoryModel(name, description);
        Assert.assertNotNull(category);
        Assert.assertTrue(category instanceof CategoryModel);
        Assert.assertEquals(name, category.getName());
        Assert.assertEquals(description, category.getDescription());
    }

    @Test
    public void testCreateCategoryModelAllMember() {
        final String categoryId = "categoryId";
        final String name = "name";
        final String description = "description";
        final String longDescription = "longDescription";
        final String bannerImage = "bannerImage";
        final String thumbnailImage = "thumbnailImage";
        final String largeImage = "largeImage";
        final String tagLine = "tagline";
        final Category parent = new CategoryModel();
        Category categoryModel = categoryFactory.createCategoryModel(name, description, longDescription, bannerImage,
            thumbnailImage, largeImage, tagLine, parent);
        Assert.assertNotNull(categoryModel);
        Assert.assertTrue(categoryModel instanceof CategoryModel);
        Assert.assertEquals(name, categoryModel.getName());
        Assert.assertEquals(description, categoryModel.getDescription());
        Assert.assertEquals(longDescription, categoryModel.getLongDescription());
        Assert.assertEquals(tagLine, categoryModel.getTagLine());
        Assert.assertEquals(bannerImage, categoryModel.getBannerImage());
        Assert.assertEquals(thumbnailImage, categoryModel.getThumbNailImage());
        Assert.assertEquals(largeImage, categoryModel.getLargeImage());
        Assert.assertEquals(parent, categoryModel.getParent());
    }
}