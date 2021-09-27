package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.StoreRef;
import com.kgcorner.topspin.model.StoreReferenceModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        String query = "select STORE.ID, STORE.NAME, STORE.DESCRIPTION, STORE.MAX_CASHBACK, count(OFFERS.ID) " +
            "count from STORE inner join OFFERS on STORE.ID = OFFERS.STORE_ID group by (STORE.ID)";
        Object o = storeDao.runSelectNativeQuery(query);
        List<Object[]> tuples = (List<Object[]>) o;
        List<StoreRef> stores = new ArrayList<>();
        for(Object row : tuples) {
            Object[] values = (Object[]) row;
            StoreRef storeRef = new StoreRef();
            storeRef.setId(values[0].toString());
            storeRef.setName(values[1].toString());
            storeRef.setDescription(values[2].toString());
            storeRef.setMaxCashback(values[3].toString());
            storeRef.setOffersCount(Integer.parseInt(values[4].toString()));
            stores.add(storeRef);
        }
        return stores;
    }
}