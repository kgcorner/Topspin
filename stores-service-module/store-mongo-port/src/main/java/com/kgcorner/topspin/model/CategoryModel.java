package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

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
    private List<CategoryModel> children = new ArrayList<>();

    @Override
    public String getCategoryId() {
        return id;
    }

    public void setCategoryId(String id) {
        this.id = id;
    }

    public List<Category> getChildren() {
        List<Category> childrenList = new ArrayList<>();
        for(Category c : children) {
            childrenList.add(c);
        }
        return childrenList;
    }

    public void addCategory(Category category) {
        Assert.isTrue(category instanceof CategoryModel);
        children.add((CategoryModel) category);
    }
}