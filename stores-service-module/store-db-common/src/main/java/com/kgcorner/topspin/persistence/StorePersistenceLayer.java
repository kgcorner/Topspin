package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.AbstractStore;

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
    AbstractStore createStore(AbstractStore AbstractStore);

    /**
     * Returns all Stores
     * @param page
     * @param itemCount
     * @return
     */
    List<AbstractStore> getAllStores(int page, int itemCount);

    /**
     * return Store by given ID
     * @param StoreId
     * @return
     */
    AbstractStore getStore(String StoreId);

    /**
     * Update given Store, identified by id
     * @param store
     * @param StoreId
     */
    AbstractStore updateStore(AbstractStore store, String StoreId);

    /**
     * Deletes given store
     * @param storeId
     */
    void deleteStore(String storeId);
}