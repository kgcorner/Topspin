package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
import org.springframework.beans.BeanUtils;
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
    private CategoryPersistenceLayer categoryPersistenceLayer;

    public CategoryDTO getCategory(String categoryId) {
        AbstractCategory category = categoryPersistenceLayer.getCategory(categoryId);
        if(category == null)
            throw new IllegalArgumentException("No Such category");
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    public CategoryDTO createCategory(CategoryDTO category) {
        AbstractCategory abstractCategory = categoryPersistenceLayer.createCategory(category);
        BeanUtils.copyProperties(abstractCategory, category);
        return category;
    }

    public CategoryDTO updateCategory(CategoryDTO category, String categoryId) {
        AbstractCategory abstractCategory = categoryPersistenceLayer.updateCategory(category, categoryId);
        BeanUtils.copyProperties(abstractCategory, category);
        return category;
    }

    public List<CategoryDTO> getAllCategories(int page, int perPage) {
        List<? extends AbstractCategory> allCategories = categoryPersistenceLayer.getAllCategories(page, perPage);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(AbstractCategory category : allCategories) {
            CategoryDTO dto = new CategoryDTO();
            BeanUtils.copyProperties(category, dto);
            categoryDTOS.add(dto);
        }
        return categoryDTOS;
    }

    public void deleteCategory(String categoryId) {
        categoryPersistenceLayer.deleteCategory(categoryId);
    }
}