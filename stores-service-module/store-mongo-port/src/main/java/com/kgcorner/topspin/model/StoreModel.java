package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public class StoreModel extends AbstractStore<CategoryModel> implements Serializable {

    @Id
    private String id;

    @Override
    public String getStoreId() {
        return id;
    }

    public void setStoreId(String storeId) {
        this.id = storeId;
    }
}