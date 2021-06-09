package com.kgcorner.topspin.model;


import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/05/21
 */

public class CategoryResponse extends AbstractCategory {

    private String id;
    private List<Category> children;

    public void setCategoryId(String id) {
        this.id = id;
    }
    @Override
    public String getCategoryId() {
        return id;
    }

    @Override
    public List<? extends Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}