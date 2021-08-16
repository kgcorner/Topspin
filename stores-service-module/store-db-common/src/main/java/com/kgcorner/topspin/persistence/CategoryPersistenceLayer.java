package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.AbstractCategory;

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
    AbstractCategory createCategory(AbstractCategory category);

    /**
     * Returns all categories
     * @param page
     * @param itemCount
     * @return
     */
    List<AbstractCategory> getAllCategories(int page, int itemCount);

    /**
     * return category by given ID
     * @param categoryId
     * @return
     */
    AbstractCategory getCategory(String categoryId);

    /**
     * Get instance of parent category of give child's id
     * @param childCategoryId
     * @return
     */
    AbstractCategory getCategoryParent(String childCategoryId);

    /**
     * Return children of given category
     * @return
     */
    List<? extends AbstractCategory> getAllChildren(String parentCategoryId);

    /**
     * Update given category, identified by id
     * @param updatedCategory
     * @param categoryId
     */
    AbstractCategory updateCategory(AbstractCategory updatedCategory, String categoryId);

    /**
     * Deletes a category by given id
     * @param categoryId
     */
    void deleteCategory(String categoryId);
}