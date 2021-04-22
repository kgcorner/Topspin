package com.kgcorner.topspin.dtos;


import com.kgcorner.topspin.model.Category;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public class CategoryDTO extends ResourceSupport implements Category {

    private final Category category;
    private final List<CategoryDTO> children = new ArrayList<>();

    public CategoryDTO(Category category) {
        this.category = category;
    }

    @Override
    public String getName() {
        return category.getName();
    }

    @Override
    public String getDescription() {
        return category.getDescription();
    }

    @Override
    public String getLongDescription() {
        return category.getLongDescription();
    }

    @Override
    public String getBannerImage() {
        return category.getBannerImage();
    }

    @Override
    public String getThumbNailImage() {
        return category.getThumbNailImage();
    }

    @Override
    public String getLargeImage() {
        return category.getLargeImage();
    }

    @Override
    public Category getParent() {
        return category.getParent();
    }

    @Override
    public String getTagLine() {
        return category.getTagLine();
    }

    @Override
    public String getCategoryId() {
        return category.getCategoryId();
    }

    public void addChildren(Category child) {
        this.children.add(new CategoryDTO(child));
    }

    @Override
    public List<? extends Category> getChildren() {
        return this.children;
    }
}