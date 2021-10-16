package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.StoreDTO;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/10/21
 */

public interface StoreClient {

    /**
     * Creates a store
     */
    StoreDTO createStore(StoreDTO storeDTO);

    /**
     *  Updates a store
     */
    StoreDTO put(
        String storeId,
        StoreDTO storeDTO);

    /**
     * Returns a store
     */
    StoreDTO get(String storeId);

    /**
     * returns all stores page by page
     */
    List<StoreDTO> getAllStores(int page, int perPageItems);
}