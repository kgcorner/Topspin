package com.kgcorner.topspin.client;


import com.kgcorner.topspin.model.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/10/21
 */

@Component
public class LocalStoreClient implements StoreClient {

    @Autowired
    private StoreService storeService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO) {
        return storeService.createStore(storeDTO);
    }

    @Override
    public StoreDTO put(String storeId, StoreDTO storeDTO) {
        return storeService.updateStore(storeDTO, storeId);
    }

    @Override
    public StoreDTO get(String storeId) {
        return storeService.getStore(storeId);
    }

    @Override
    public List<StoreDTO> getAllStores(int page, int perPageItems) {
        return storeService.getAllStores(page, perPageItems);
    }
}