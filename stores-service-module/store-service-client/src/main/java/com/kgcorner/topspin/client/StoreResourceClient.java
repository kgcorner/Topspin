package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.StoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/04/21
 */

@FeignClient("store-service")
public interface StoreResourceClient {

    /**
     * Creates a store with given details
     * @param name
     * @param description
     * @param link
     * @param affiliateId
     * @param surferPlaceHolder
     * @param placeholder
     * @return
     */
    @PostMapping("/stores")
    ResponseEntity<StoreResponse> createStore(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("link") String link,
        @RequestParam("affiliateId") String affiliateId,
        @RequestParam("surferPlaceHolder") String surferPlaceHolder,
        @RequestParam("placeholder") String placeholder
    );

    /**
     * Updates the store with given values
     * @param storeId
     * @param name
     * @param affiliateId
     * @param link
     * @param surferPlaceHolder
     * @param placeholder
     * @param description
     * @return
     */
    @PutMapping("/stores/{storeId}")
    ResponseEntity<StoreResponse> put(
        @PathVariable("storeId") String storeId,
        @RequestParam("name") String name,
        @RequestParam("affiliateId") String affiliateId,
        @RequestParam("link") String link,
        @RequestParam("surferPlaceHolder") String surferPlaceHolder,
        @RequestParam("placeholder") String placeholder,
        @RequestParam("description") String description);

    /**
     * Returns the store with given ID
     * @param storeId
     * @return
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> get(
        @PathVariable("storeId") String storeId);

    /**
     * Returns list of stores page wise, per page will contain given number of stores
     * @param page
     * @param itemPerPage
     * @return
     */
    @GetMapping("/stores")
    ResponseEntity<Resources<StoreResponse>> getAllStores(
        @RequestParam(name = "id") int page,
        @RequestParam(name = "item-count") int itemPerPage);
}