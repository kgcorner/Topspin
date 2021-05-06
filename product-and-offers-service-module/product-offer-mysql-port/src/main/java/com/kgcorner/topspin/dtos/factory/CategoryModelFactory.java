package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.CategoryReferenceModel;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Component
public class CategoryModelFactory implements CategoryFactory {
    @Override
    public Category createCategory(String id, String name, String description) {
        CategoryReferenceModel category = new CategoryReferenceModel();
        category.setDescription(description);
        category.setId(id);
        category.setName(name);
        return category;
    }
}