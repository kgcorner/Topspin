package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 01/05/21
 */

@Component
public class CategoryClient {

    @Autowired
    private CategoryResourceClient categoryResourceClient;

    /**
     * @see CategoryResourceClient#get(String)
     */
    public CategoryDTO getCategory(String categoryId) {
        return categoryResourceClient.get(categoryId).getBody();
    }

    /**
     * @see CategoryResourceClient#getAll(int, int)
     */
    public List<CategoryDTO> getAll(int page, int itemsPerPage) {
        Resources<CategoryDTO> body = categoryResourceClient.getAll(page, itemsPerPage).getBody();
        if(body != null)
            return new ArrayList<>(body.getContent());
        else
            return Collections.emptyList();
    }

    /**
     * @see CategoryResourceClient#create(CategoryDTO) 
     */
    public CategoryDTO create(CategoryDTO categoryDTO) {
        return categoryResourceClient.create(categoryDTO).getBody();
    }

    /**
     * @see CategoryResourceClient#update(String, CategoryDTO)
     */
    public CategoryDTO update(String categoryId, CategoryDTO categoryDTO) {
        return categoryResourceClient.update(categoryId, categoryDTO).getBody();
    }
}