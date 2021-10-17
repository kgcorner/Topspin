package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/10/21
 */

@Component
public class LocalCategoryClient implements CategoryClient{

    @Autowired
    private CategoryService categoryService;

    @Override
    public CategoryDTO getCategory(String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @Override
    public List<CategoryDTO> getAll(int page, int itemsPerPage) {
        return categoryService.getAllCategories(page, itemsPerPage);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @Override
    public CategoryDTO update(String categoryId, CategoryDTO categoryDTO) {
        return categoryService.updateCategory(categoryDTO, categoryId);
    }
}