package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public class CategoryModel extends AbstractCategory implements Serializable {

    @Id
    private String id;

    @Override
    public String getCategoryId() {
        return id;
    }

    public void setCategoryId(String id) {
        this.id = id;
    }
}