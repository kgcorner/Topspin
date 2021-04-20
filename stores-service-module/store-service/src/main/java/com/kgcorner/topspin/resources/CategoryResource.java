package com.kgcorner.topspin.resources;


import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

@RestController
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("Get category of given id")
    @GetMapping("categories/{categoryId}")
    public ResponseEntity<CategoryDTO> get(
        @ApiParam("ID of the category to fetch")
        @PathVariable("categoryId") String categoryId
    ) {
        CategoryDTO category = categoryService.getCategory(categoryId);
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(CategoryResource.class).get(category.getCategoryId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CategoryResource.class)
            .update(categoryId,"name","description")).withRel("update");
        category.add(selfRel);
        category.add(updateLink);
        return ResponseEntity.ok(category);
    }

    @ApiOperation("Get all Categories")
    @GetMapping("/categories")
    public ResponseEntity<Resources<CategoryDTO>> getAll(
        @ApiParam("page number")
        @RequestParam(name = "page", defaultValue = "0") int page,
        @ApiParam("number of categories per page")
        @RequestParam(name = "item-count", defaultValue = "30") int itemCount
    ) {

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories(page, itemCount);
        final Resources<CategoryDTO> resources = new Resources<>(categoryDTOS);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        uriString = uriString.replace("page="+page, "page="+(page+1));
        resources.add(new Link(uriString, "next-page"));
        for(CategoryDTO dto : categoryDTOS) {
            Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(CategoryResource.class).get(dto.getCategoryId())).withSelfRel();
            dto.add(selfRel);
        }

        return ResponseEntity.ok(resources);
    }

    @ApiOperation("Create category with given values")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> create(
        @ApiParam("name of the category")
        @RequestParam("name") String name,
        @ApiParam("description of the category")
        @RequestParam("description") String description
    ) {
        CategoryDTO category = categoryService.createCategory(name, description);
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(CategoryResource.class).get(category.getCategoryId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CategoryResource.class)
            .update(category.getCategoryId(),"name","description")).withRel("update");
        category.add(selfRel);
        category.add(updateLink);
        return ResponseEntity.ok(category);
    }

    @ApiOperation("Update the given category")
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> update(
        @ApiParam("ID of the category to fetch")
        @PathVariable("categoryId") String categoryId,
        @ApiParam("name of the category")
        @RequestParam("name") String name,
        @ApiParam("description of the category")
        @RequestParam("description") String description
    ) {
        CategoryDTO category = categoryService.updateCategory(categoryId, name, description);
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(CategoryResource.class).get(category.getCategoryId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CategoryResource.class)
            .update(categoryId,"name","description")).withRel("update");
        category.add(selfRel);
        category.add(updateLink);

        return ResponseEntity.ok(category);
    }
}