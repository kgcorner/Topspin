package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.CategoryModel;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

@Component
public class CategoryFactoryImpl implements CategoryFactory {
    @Override
    public Category createCategoryModel(String name, String description) {
        CategoryModel category = new CategoryModel();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    @Override
    public Category createCategoryModel(String name, String description, String longDescription, String bannerImage,
                                        String thumbnailImage, String largeImage, String tagLine, Category parent) {
        CategoryModel category = new CategoryModel();
        category.setName(name);
        category.setDescription(description);
        category.setLongDescription(longDescription);
        category.setBannerImage(bannerImage);
        category.setThumbnailImage(thumbnailImage);
        category.setLargeImage(largeImage);
        category.setTagline(tagLine);
        category.setParent(parent);
        return category;
    }

    @Override
    public Category createCategoryModel() {
        return new CategoryModel();
    }
}