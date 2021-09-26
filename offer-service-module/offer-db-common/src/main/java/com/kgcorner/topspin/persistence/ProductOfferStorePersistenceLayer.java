package com.kgcorner.topspin.persistence;



import com.kgcorner.topspin.model.StoreRef;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductOfferStorePersistenceLayer {
    StoreRef createStore(StoreRef store);
    StoreRef getStore(String id);
    List<StoreRef> getStoresWithOfferCount();
}