package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.StoreRef;
import com.kgcorner.topspin.model.StoreReferenceModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public StoreRef createStore(StoreRef store) {
        StoreReferenceModel storeReferenceModel = new StoreReferenceModel();
        BeanUtils.copyProperties(store, storeReferenceModel);
        return storeDao.create(storeReferenceModel);
    }

    @Override
    public StoreRef getStore(String id) {
        if(Strings.isNullOrEmpty(id))
            throw new IllegalArgumentException("Id can't be null");
        return storeDao.get(id, StoreReferenceModel.class);
    }

    @Override
    public List<StoreRef> getStoresWithOfferCount() {
        String query = "select STORE.*, count(OFFER.ID) from STORE inner join OFFER on STORE.ID = OFFER.STORE_ID group by (STORE.ID)";
        return null;
    }
}