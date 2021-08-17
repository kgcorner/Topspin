package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoStoreDao;
import com.kgcorner.topspin.model.AbstractStore;
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
    public AbstractStore createStore(AbstractStore abstractStore) {
        Assert.notNull(abstractStore);
        Assert.notNull(abstractStore.getName(), "Name can't be null");
        Assert.notNull(abstractStore.getLink(), "Link can't be null");
        Assert.notNull(abstractStore.getAffiliateId(), "affiliateId can't be null");
        Assert.notNull(abstractStore.getPlaceHolder(), "placeholder can't be null");
        Assert.notNull(abstractStore.getSurferPlaceHolder(), "surfer place holder can't be null");
        StoreModel storeModel = new StoreModel();
        BeanUtils.copyProperties(abstractStore, storeModel);
        return storeDao.create(storeModel);
    }

    @Override
    public List<AbstractStore> getAllStores(int page, int itemCount) {
        List<AbstractStore> abstractStores = new ArrayList<>();
        List<StoreModel> all = storeDao.getAll(page, itemCount, StoreModel.class);
        for(StoreModel model : all) {
            abstractStores.add(model);
        }
        return abstractStores;
    }

    @Override
    public AbstractStore getStore(String storeId) {
        Assert.notNull(storeId, "storeID can't be null");
        return storeDao.getById(storeId, StoreModel.class);
    }

    @Override
    public AbstractStore updateStore(AbstractStore updatedAbstractStore, String storeId) {
        AbstractStore storeModel = getStore(storeId);
        Assert.notNull(storeModel, "No such store found");
        BeanUtils.copyProperties(updatedAbstractStore, storeModel, "storeId");

        return storeDao.update((StoreModel) storeModel);
    }

    @Override
    public void deleteStore(String storeId) {
        AbstractStore abstractStore = getStore(storeId);
        Assert.notNull(abstractStore, "No such store found");
        storeDao.remove((StoreModel) abstractStore);
    }
}