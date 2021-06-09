package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.dtos.Store;
import com.kgcorner.topspin.dtos.StoreReferenceModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 07/06/21
 */
@Transactional
@Repository
public class MysqlStorePersistenceLayer implements ProductOfferStorePersistenceLayer {

    @Autowired
    private MysqlDao<StoreReferenceModel> storeDao;

    @Override
    public Store createStore(Store store) {
        Assert.notNull(store);
        return storeDao.create((StoreReferenceModel) store);
    }

    @Override
    public Store getStore(String id) {
        if(Strings.isNullOrEmpty(id))
            throw new IllegalArgumentException("Id can't be null");
        return storeDao.get(id, StoreReferenceModel.class);
    }
}