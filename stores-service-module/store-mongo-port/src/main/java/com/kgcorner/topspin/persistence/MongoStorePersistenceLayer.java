package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoStoreDao;
import com.kgcorner.topspin.model.Store;
import com.kgcorner.topspin.model.StoreModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@Repository
public class MongoStorePersistenceLayer implements StorePersistenceLayer {

    @Autowired
    private MongoStoreDao<StoreModel> storeDao;

    @Override
    public Store createStore(Store store) {
        Assert.notNull(store);
        Assert.notNull(store.getName(), "Name can't be null");
        Assert.notNull(store.getLink(), "Link can't be null");
        Assert.notNull(store.getAffiliateId(), "affiliateId can't be null");
        Assert.notNull(store.getPlaceHolder(), "placeholder can't be null");
        Assert.notNull(store.getSurferPlaceHolder(), "surfer place holder can't be null");
        return storeDao.create((StoreModel) store);
    }

    @Override
    public List<Store> getAllStores(int page, int itemCount) {
        List<Store> stores = new ArrayList<>();
        List<StoreModel> all = storeDao.getAll(page, itemCount, StoreModel.class);
        for(StoreModel model : all) {
            stores.add(model);
        }
        return stores;
    }

    @Override
    public Store getStore(String storeId) {
        Assert.notNull(storeId, "storeID can't be null");
        return storeDao.getById(storeId, StoreModel.class);
    }

    @Override
    public void updateStore(Store updatedStore, String storeId) {
        Assert.isTrue(updatedStore instanceof StoreModel, "not instance of StoreModel");
        Store store = getStore(storeId);
        Assert.notNull(store, "No such store found");
        ((StoreModel)updatedStore).setStoreId(store.getStoreId());
        BeanUtils.copyProperties(updatedStore, store);
        storeDao.update((StoreModel) store);
    }

    @Override
    public void deleteStore(String storeId) {
        Store store = getStore(storeId);
        Assert.notNull(store, "No such store found");
        storeDao.remove((StoreModel) store);
    }
}