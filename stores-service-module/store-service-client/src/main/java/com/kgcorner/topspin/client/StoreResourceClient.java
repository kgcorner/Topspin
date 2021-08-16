package com.kgcorner.topspin.client;


import com.kgcorner.topspin.dtos.StoreDTO;
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
     * @ storeDTO
     * @return
     */
    @PostMapping("/stores")
    ResponseEntity<StoreDTO> createStore(
        @RequestBody StoreDTO storeDTO
    );

    /**
     * Updates the store with given values
     * @param storeId
     * @param storeDTO
     * @return
     */
    @PutMapping("/stores/{storeId}")
    ResponseEntity<StoreDTO> put(
        @PathVariable("storeId") String storeId,
        @RequestBody StoreDTO storeDTO
    );
    /**
     * Returns the store with given ID
     * @param storeId
     * @return
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreDTO> get(
        @PathVariable("storeId") String storeId);

    /**
     * Returns list of stores page wise, per page will contain given number of stores
     * @param page
     * @param itemPerPage
     * @return
     */
    @GetMapping("/stores")
    ResponseEntity<Resources<StoreDTO>> getAllStores(
        @RequestParam(name = "id") int page,
        @RequestParam(name = "item-count") int itemPerPage);
}