package com.kgcorner.topspin.resources;


import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

@RestController
public class CategoryResource {

    public static final String CATEGORIES_CATEGORY_ID = "/categories/{categoryId}";
    public static final String MANAGE_CATEGORIES_CATEGORY_ID = "/manage/categories/{categoryId}";
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("Get category of given id")
    @GetMapping(CATEGORIES_CATEGORY_ID)
    public ResponseEntity<CategoryDTO> get(
        @ApiParam("ID of the category to fetch")
        @PathVariable("categoryId") String categoryId
    ) {
        CategoryDTO category = categoryService.getCategory(categoryId);
        category.addLink(CATEGORIES_CATEGORY_ID.replace("{categoryId}", categoryId), Link.REL_SELF);
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
        String uriString = "categories?page="+page+"&item-count="+itemCount;
        uriString = uriString.replace("page="+page, "page="+(page+1));
        resources.add(new Link(uriString, "next-page"));
        for(CategoryDTO dto : categoryDTOS) {
            dto.addLink(CATEGORIES_CATEGORY_ID.replace("{categoryId}", dto.getCategoryId()), Link.REL_SELF);
        }
        return ResponseEntity.ok(resources);
    }

    @ApiOperation("Create category with given values")
    @PostMapping("/manage/categories")
    public ResponseEntity<CategoryDTO> create(
        @RequestBody CategoryDTO categoryDTO
    ) {
        CategoryDTO category = categoryService.createCategory(categoryDTO);
        category.addLink(CATEGORIES_CATEGORY_ID.replace("{categoryId}", category.getCategoryId()), Link.REL_SELF);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @ApiOperation("Update the given category")
    @PutMapping(MANAGE_CATEGORIES_CATEGORY_ID)
    public ResponseEntity<CategoryDTO> update(
        @ApiParam("id of the store")
        @PathVariable("categoryId") String categoryId,
        @RequestBody CategoryDTO categoryDTO
    ) {
        CategoryDTO category = categoryService.updateCategory(categoryDTO, categoryId);
        category.addLink(CATEGORIES_CATEGORY_ID.replace("{categoryId}", category.getCategoryId()), Link.REL_SELF);
        return ResponseEntity.ok(category);
    }

    @ApiOperation("Deletes a CAtegory")
    @DeleteMapping(MANAGE_CATEGORIES_CATEGORY_ID)
    public ResponseEntity<Void> deleteCategory( @ApiParam("id of the store")
                                                    @PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}