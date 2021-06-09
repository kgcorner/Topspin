package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Store;
import com.kgcorner.topspin.dtos.StoreReferenceModel;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Component
public class StoreModelFactory implements StoreFactory {
    @Override
    public Store createStore(String id, String name, String description) {
        StoreReferenceModel storeReferenceModel = new StoreReferenceModel();
        storeReferenceModel.setDescription(description);
        storeReferenceModel.setId(id);
        storeReferenceModel.setName(name);
        return storeReferenceModel;
    }
}