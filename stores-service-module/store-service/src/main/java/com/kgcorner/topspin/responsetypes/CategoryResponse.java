package com.kgcorner.topspin.responsetypes;


import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.resources.CategoryResource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/01/21
 */

public class CategoryResponse extends ResourceSupport {
    private final Category category;

    public CategoryResponse(Category category) {
        this.category = category;
        final String id = category.getCategoryId();
        add(linkTo(methodOn(CategoryResource.class).get(id)).withSelfRel());
    }

    public Category getCategory() {
        return category;
    }
}