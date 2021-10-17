package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.CategoryDTO;

import java.util.List;

public interface CategoryClient {

    /**
     * Get category by id
     */
    public CategoryDTO getCategory(String categoryId);

    /**
     * Get all categories page by page
     */
    List<CategoryDTO> getAll(int page, int itemsPerPage);

    /**
     * Creates a category
     */
    CategoryDTO create(CategoryDTO categoryDTO);

    /**
     * Updates a category
     */
    CategoryDTO update(String categoryId, CategoryDTO categoryDTO);
}