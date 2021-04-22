package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.Store;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public interface StorePersistenceLayer {

    /**
     * Creates a Store with given values and return instance of the created Store
     * @return
     */
    Store createStore(Store Store);

    /**
     * Returns all Stores
     * @param page
     * @param itemCount
     * @return
     */
    List<Store> getAllStores(int page, int itemCount);

    /**
     * return Store by given ID
     * @param StoreId
     * @return
     */
    Store getStore(String StoreId);

    /**
     * Update given Store, identified by id
     * @param updatedStore
     * @param StoreId
     */
    void updateStore(Store updatedStore, String StoreId);

    /**
     * Deletes given store
     * @param storeId
     */
    void deleteStore(String storeId);
}