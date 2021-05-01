package com.kgcorner.topispin.client;


import com.kgcorner.topspin.dtos.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return new ArrayList<>(body.getContent());
    }

    /**
     * @see CategoryResourceClient#create(String, String)
     */
    public CategoryDTO create(String name, String description) {
        return categoryResourceClient.create(name, description).getBody();
    }

    /**
     * @see CategoryResourceClient#update(String, String, String) 
     */
    public CategoryDTO update(String categoryId, String name, String description) {
        return categoryResourceClient.update(categoryId, name, description).getBody();
    }
}