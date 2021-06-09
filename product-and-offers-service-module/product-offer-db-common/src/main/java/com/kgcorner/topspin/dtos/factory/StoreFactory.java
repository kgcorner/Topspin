package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Store;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface StoreFactory {
    Store createStore(String id, String name, String description);
}