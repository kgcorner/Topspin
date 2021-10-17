package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.StoreDTO;
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
public class RemoteStoreClient implements StoreClient {

    @Autowired
    private StoreResourceClient storeResourceClient;

    /**
     * @see StoreResourceClient#createStore(StoreDTO)
     */
    public StoreDTO createStore(
        StoreDTO storeDTO
    ) {
        ResponseEntity<StoreDTO> store = storeResourceClient.createStore(storeDTO);
        return store.getBody();
    }

    /**
     *  @see StoreResourceClient#put(String, StoreDTO)
     */
    public StoreDTO put(
        String storeId,
        StoreDTO storeDTO) {
        ResponseEntity<StoreDTO> updatedStoreResource = storeResourceClient.put(storeId, storeDTO);
        return updatedStoreResource.getBody();
    }

    /**
     * @see StoreResourceClient#get(String)
     */
    public StoreDTO get(String storeId) {
        return storeResourceClient.get(storeId).getBody();
    }

    /**
     * @see RemoteStoreClient#getAllStores(int, int)
     * @return
     */
    public List<StoreDTO> getAllStores(int page, int perPageItems) {
        ResponseEntity<Resources<StoreDTO>> allStoresResourcesBody = storeResourceClient.getAllStores(page,
            perPageItems);
        Resources<StoreDTO> allStoresResources = allStoresResourcesBody.getBody();
        if(allStoresResources != null)
            return new ArrayList<>(allStoresResources.getContent());
        else
            return Collections.emptyList();
    }
    
}