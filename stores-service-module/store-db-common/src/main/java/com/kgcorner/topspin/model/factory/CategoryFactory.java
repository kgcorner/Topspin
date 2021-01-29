package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.model.Category;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public interface CategoryFactory {
    /**
     * Creates a category with given values and return instance of the created category
     * @param name
     * @param description
     * @return
     */
    Category createCategoryModel(String name, String description);

    /**
     * Creates a category with given values and return instance of the created category
     * @param name
     * @param description
     * @param longDescription
     * @param bannerImage
     * @param thumbnailImage
     * @param largeImage
     * @param tagLine
     * @param parent
     * @return
     */
    Category createCategoryModel(String name, String description, String longDescription, String bannerImage,
                            String thumbnailImage, String largeImage, String tagLine, Category parent);

    /**
     * Creates a category with given values and return instance of the created category
     * @return
     */
    Category createCategoryModel();
}