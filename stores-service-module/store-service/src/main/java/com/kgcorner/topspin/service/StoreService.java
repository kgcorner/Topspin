package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.Store;
import com.kgcorner.topspin.model.factory.StoreFactory;
import com.kgcorner.topspin.persistence.StorePersistenceLayer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@Service
public class StoreService {

    @Autowired
    private StoreFactory storeFactory;

    @Autowired
    private StorePersistenceLayer storePersistenceLayer;


    public StoreDTO createStore(String name, String description, String link, String affiliateId,
                                String surferPlaceHolder, String placeholder) {
        Store store = storeFactory.createStore(name, link, true, description, placeholder,
            surferPlaceHolder, affiliateId);
        store = storePersistenceLayer.createStore(store);
        return getStoreDTO(store);
    }

    public StoreDTO updateStore(String storeId, String name, String description, String link, String affiliateId,
                                String surferPlaceHolder, String placeholder) {
        Store store = storePersistenceLayer.getStore(storeId);
        if(store == null)
            throw new IllegalArgumentException("No such store exists");
        Store newStore = storeFactory.createStore(name, link, true, description, placeholder,
            surferPlaceHolder, affiliateId);
        storePersistenceLayer.updateStore(newStore, storeId);
        store = storePersistenceLayer.getStore(storeId);
        return getStoreDTO(store);
    }

    public StoreDTO getStore(String storeId) {
        Store store = storePersistenceLayer.getStore(storeId);
        return getStoreDTO(store);
    }

    private StoreDTO getStoreDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(store, storeDTO);
        return storeDTO;
    }

    public List<StoreDTO> getAllStores(int page, int itemCount) {
        List<Store> allStores = storePersistenceLayer.getAllStores(page, itemCount);
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for(Store store : allStores) {
            storeDTOS.add(getStoreDTO(store));
        }
        return storeDTOS;
    }
}