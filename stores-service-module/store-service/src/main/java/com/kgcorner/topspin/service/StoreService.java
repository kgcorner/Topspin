package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.AbstractStore;
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
    private StorePersistenceLayer storePersistenceLayer;


    public StoreDTO createStore(StoreDTO storeDTO) {
        AbstractStore store = storePersistenceLayer.createStore(storeDTO);
        BeanUtils.copyProperties(store, storeDTO);
        return storeDTO;
    }

    public StoreDTO updateStore(StoreDTO storeDTO, String storeId) {
        AbstractStore store = storePersistenceLayer.updateStore(storeDTO, storeId);
        BeanUtils.copyProperties(store, storeDTO);
        return storeDTO;
    }

    public StoreDTO getStore(String storeId) {
        AbstractStore abstractStore = storePersistenceLayer.getStore(storeId);
        if(abstractStore == null)
            throw new IllegalArgumentException("No Such store");
        StoreDTO storeDTO = getStoreDTO(abstractStore);
        return storeDTO;
    }

    private StoreDTO getStoreDTO(AbstractStore abstractStore) {
        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(abstractStore, storeDTO);
        return storeDTO;
    }

    public List<StoreDTO> getAllStores(int page, int itemCount) {
        List<AbstractStore> allAbstractStores = storePersistenceLayer.getAllStores(page, itemCount);
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for(AbstractStore abstractStore : allAbstractStores) {
            StoreDTO storeDTO = getStoreDTO(abstractStore);
            storeDTOS.add(storeDTO);
        }
        return storeDTOS;
    }

    public void deleteStore(String storeId) {
        storePersistenceLayer.deleteStore(storeId);
    }
}