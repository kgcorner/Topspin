package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.StoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
    public StoreResponse createStore(
        String name,
        String description,
        String link,
        String affiliateId,
        String surferPlaceHolder,
        String placeholder
    ) {
        ResponseEntity<StoreResponse> store = storeResourceClient.createStore(name, description, link,
            affiliateId, surferPlaceHolder, placeholder);
        return store.getBody();
    }

    /**
     *  @see StoreResourceClient#put(String, String, String, String, String, String, String)
     */
    public StoreResponse put(
        String storeId,
        String name,
        String affiliateId,
        String link,
        String surferPlaceHolder,
        String placeholder,
        String description) {
        ResponseEntity<StoreResponse> updatedStoreResource = storeResourceClient.put(storeId, name, affiliateId, link,
            surferPlaceHolder, placeholder, description);
        return updatedStoreResource.getBody();
    }

    /**
     * @see StoreResourceClient#get(String)
     */
    public StoreResponse get(String storeId) {
        return storeResourceClient.get(storeId).getBody();
    }

    /**
     * @see StoreClient#getAllStores(int, int)
     * @return
     */
    public List<StoreResponse> getAllStores(int page, int perPageItems) {
        ResponseEntity<Resources<StoreResponse>> allStoresResourcesBody = storeResourceClient.getAllStores(page,
            perPageItems);
        Resources<StoreResponse> allStoresResources = allStoresResourcesBody.getBody();
        if(allStoresResources != null)
            return new ArrayList<>(allStoresResources.getContent());
        else
            return Collections.emptyList();
    }
    
}