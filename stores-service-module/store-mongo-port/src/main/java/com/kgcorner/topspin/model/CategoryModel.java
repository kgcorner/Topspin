package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public class CategoryModel extends AbstractCategory {

    @Id
    private String id;
    private List<Category> children = new ArrayList<>();

    @Override
    public String getCategoryId() {
        return id;
    }

    public void setCategoryId(String id) {
        this.id = id;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void addCategory(Category category) {
        children.add(category);
    }
}