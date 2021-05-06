package com.kgcorner.topspin.model;


import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/05/21
 */

public class StoreResponse extends AbstractStore {

    private String storeId;

    private List<Category> categories;

    @Override
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}