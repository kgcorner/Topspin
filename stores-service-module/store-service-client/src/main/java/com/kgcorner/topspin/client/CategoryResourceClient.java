package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 01/05/21
 */
@FeignClient("store-service")
public interface CategoryResourceClient {

    /**
     * Returns category identified by ID
     * @param categoryId
     * @return
     */
    @GetMapping("categories/{categoryId}")
    ResponseEntity<CategoryResponse> get(
        @PathVariable("categoryId") String categoryId
    );

    /**
     * Returns all categories based on page and items per page
     * @param page
     * @param itemCount
     * @return
     */
    @GetMapping("/categories")
    ResponseEntity<Resources<CategoryResponse>> getAll(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "item-count", defaultValue = "30") int itemCount
    );

    /**
     * Creates a category
     * @param name
     * @param description
     * @return
     */
    @PostMapping("/categories")
    ResponseEntity<CategoryResponse> create(
        @RequestParam("name") String name,
        @RequestParam("description") String description
    );

    /**
     * updates a category
     * @param categoryId
     * @param name
     * @param description
     * @return
     */
    @PutMapping("/categories/{categoryId}")
    ResponseEntity<CategoryResponse> update(
        @PathVariable("categoryId") String categoryId,
        @RequestParam("name") String name,
        @RequestParam("description") String description
    );
}