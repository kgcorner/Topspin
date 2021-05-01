package com.kgcorner.topispin.client;


import com.kgcorner.topspin.dtos.StoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/04/21
 */
@Component
public class StoreClient {

    @Autowired
    private StoreResourceClient storeResourceClient;

    /**
     * @see StoreResourceClient#createStore(String, String, String, String, String, String)
     */
    public StoreDTO createStore(
        String name,
        String description,
        String link,
        String affiliateId,
        String surferPlaceHolder,
        String placeholder
    ) {
        ResponseEntity<StoreDTO> store = storeResourceClient.createStore(name, description, link,
            affiliateId, surferPlaceHolder, placeholder);
        return store.getBody();
    }

    /**
     *  @see StoreResourceClient#put(String, String, String, String, String, String, String)
     */
    public StoreDTO put(
        String storeId,
        String name,
        String affiliateId,
        String link,
        String surferPlaceHolder,
        String placeholder,
        String description) {
        ResponseEntity<StoreDTO> updatedStoreResource = storeResourceClient.put(storeId, name, affiliateId, link,
            surferPlaceHolder, placeholder, description);
        return updatedStoreResource.getBody();
    }

    /**
     * @see StoreResourceClient#get(String)
     */
    public StoreDTO get(String storeId) {
        return storeResourceClient.get(storeId).getBody();
    }

    /**
     * @see StoreClient#getAllStores(int, int)
     * @return
     */
    public List<StoreDTO> getAllStores(int page, int perPageItems) {
        ResponseEntity<Resources<StoreDTO>> allStoresResourcesBody = storeResourceClient.getAllStores(page,
            perPageItems);
        Resources<StoreDTO> allStoresResources = allStoresResourcesBody.getBody();
        return new ArrayList<>(allStoresResources.getContent());
    }
    
}