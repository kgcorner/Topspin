package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.factory.CategoryFactory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/01/21
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryFactory categoryFactory;

    @Autowired
    private CategoryPersistenceLayer categoryPersistenceLayer;

    public CategoryDTO getCategory(String categoryId) {
        Category category = categoryPersistenceLayer.getCategory(categoryId);
        CategoryDTO dto = new CategoryDTO(category);
        return dto;
    }

    public CategoryDTO createCategory(String name, String description) {
        Category category = categoryFactory.createCategoryModel(name, description);
        category = categoryPersistenceLayer.createCategory(category);
        CategoryDTO dto = new CategoryDTO(category);
        return dto;
    }

    public CategoryDTO updateCategory(String id, String name, String description) {
        Category category = categoryFactory.createCategoryModel(name, description);
        categoryPersistenceLayer.updateCategory(category, id);
        category = categoryPersistenceLayer.getCategory(id);
        CategoryDTO dto = new CategoryDTO(category);
        return dto;
    }

    public List<CategoryDTO> getAllCategories(int page, int perPage) {
        List<Category> categories = categoryPersistenceLayer.getAllCategories(page, perPage);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories) {
            CategoryDTO dto = new CategoryDTO(category);
            categoryDTOS.add(dto);
        }
        return categoryDTOS;
    }
}