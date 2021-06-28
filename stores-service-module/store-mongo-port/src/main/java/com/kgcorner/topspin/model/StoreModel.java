package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public class StoreModel extends AbstractStore {

    @Id
    private String id;
    private List<CategoryModel> categories = new ArrayList<>();

    @Override
    public String getStoreId() {
        return id;
    }

    public void setStoreId(String storeId) {
        this.id = storeId;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<>();
        for(Category c : categories)
            categoryList.add(c);
        return categoryList;
    }

    public void setCategories(List<? extends CategoryModel> categories) {
        this.categories = (List<CategoryModel>) categories;
    }
}