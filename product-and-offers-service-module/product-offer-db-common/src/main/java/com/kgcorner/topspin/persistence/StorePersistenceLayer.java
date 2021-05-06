package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dtos.Store;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface StorePersistenceLayer {
    Store createStore(Store store);
    Store getId(String id);
}