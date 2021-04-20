package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.Category;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public interface CategoryPersistenceLayer {
    /**
     * Creates a category with given values and return instance of the created category
     * @return
     */
    Category createCategory(Category category);

    /**
     * Returns all categories
     * @param page
     * @param itemCount
     * @return
     */
    List<Category> getAllCategories(int page, int itemCount);

    /**
     * return category by given ID
     * @param categoryId
     * @return
     */
    Category getCategory(String categoryId);

    /**
     * Get instance of parent category of give child's id
     * @param childCategoryId
     * @return
     */
    Category getCategoryParent(String childCategoryId);

    /**
     * Return children of given category
     * @return
     */
    List<Category> getAllChildren(String parentCategoryId);

    /**
     * Add child category to a parent
     * @param child
     * @param parentId
     */
    void addAChild(Category child, String parentId);

    /**
     * Update given category, identified by id
     * @param updatedCategory
     * @param categoryId
     */
    void updateCategory(Category updatedCategory, String categoryId);
}