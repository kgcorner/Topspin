package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dtos.Store;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductOfferStorePersistenceLayer {
    Store createStore(Store store);
    Store getStore(String id);
}