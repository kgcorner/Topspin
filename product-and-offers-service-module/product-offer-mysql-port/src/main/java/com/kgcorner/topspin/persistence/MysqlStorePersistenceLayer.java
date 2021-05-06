package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlStoreDao;
import com.kgcorner.topspin.dtos.Store;
import com.kgcorner.topspin.dtos.StoreReferenceModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public class MysqlStorePersistenceLayer implements StorePersistenceLayer {

    @Autowired
    private MysqlStoreDao<StoreReferenceModel> storeDao;

    @Override
    public Store createStore(Store store) {
        return storeDao.create((StoreReferenceModel)store);
    }

    @Override
    public Store getId(String id) {
        return storeDao.getById(id, StoreReferenceModel.class);
    }
}