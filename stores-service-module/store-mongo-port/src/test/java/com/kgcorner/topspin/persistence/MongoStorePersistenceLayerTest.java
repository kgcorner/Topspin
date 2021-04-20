package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MongoStoreDao;
import com.kgcorner.topspin.model.Store;
import com.kgcorner.topspin.model.StoreModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class MongoStorePersistenceLayerTest {

    private MongoStorePersistenceLayer mongoStorePersistenceLayer;
    private MongoStoreDao<StoreModel> mongoStoreDao;

    @Before
    public void setUp() throws Exception {
        mongoStorePersistenceLayer = new MongoStorePersistenceLayer();
        mongoStoreDao = PowerMockito.mock(MongoStoreDao.class);
        Whitebox.setInternalState(mongoStorePersistenceLayer, "storeDao", mongoStoreDao);
    }

    @Test
    public void createStore() {
        StoreModel store = new StoreModel();
        store.setName("name");
        store.setLink("Link");
        store.setAffiliateId("affiliateId");
        store.setPlaceHolder("placeholder");
        store.setSurferPlaceHolder("SurferPlaceholder");
        PowerMockito.when(mongoStoreDao.create(store)).thenReturn(store);
        Store createdStore = mongoStorePersistenceLayer.createStore(store);
        Assert.assertNotNull(createdStore);
        Assert.assertEquals(store, createdStore);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStoreNoLink() {
        StoreModel store = new StoreModel();
        store.setName("name");
        store.setAffiliateId("affiliateId");
        store.setPlaceHolder("placeholder");
        store.setSurferPlaceHolder("SurferPlaceholder");
        PowerMockito.when(mongoStoreDao.create(store)).thenReturn(store);
        Store createdStore = mongoStorePersistenceLayer.createStore(store);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStoreNoAffiliateId() {
        StoreModel store = new StoreModel();
        store.setLink("Link");
        store.setName("name");
        store.setPlaceHolder("placeholder");
        store.setSurferPlaceHolder("SurferPlaceholder");
        PowerMockito.when(mongoStoreDao.create(store)).thenReturn(store);
        Store createdStore = mongoStorePersistenceLayer.createStore(store);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStoreNoPlaceHolder() {
        StoreModel store = new StoreModel();
        store.setLink("Link");
        store.setAffiliateId("affiliateId");
        store.setName("name");
        store.setSurferPlaceHolder("SurferPlaceholder");
        PowerMockito.when(mongoStoreDao.create(store)).thenReturn(store);
        Store createdStore = mongoStorePersistenceLayer.createStore(store);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStoreNoSurferPlaceHolder() {
        StoreModel store = new StoreModel();
        store.setLink("Link");
        store.setAffiliateId("affiliateId");
        store.setPlaceHolder("placeholder");
        store.setName("name");
        PowerMockito.when(mongoStoreDao.create(store)).thenReturn(store);
        Store createdStore = mongoStorePersistenceLayer.createStore(store);
    }

    @Test
    public void getAllStores() {
        List<StoreModel> stores = new ArrayList<>();
        stores.add(new StoreModel());
        PowerMockito.when(mongoStoreDao.getAll(1,10, StoreModel.class)).thenReturn(stores);
        Assert.assertEquals(stores, mongoStorePersistenceLayer.getAllStores(1, 10));
    }

    @Test
    public void getStore() {
        StoreModel model = new StoreModel();
        PowerMockito.when(mongoStoreDao.getById("id", StoreModel.class)).thenReturn(model);
        Assert.assertEquals(model, mongoStorePersistenceLayer.getStore("id"));
    }

    @Test
    public void updateStore() {
        StoreModel store = new StoreModel();
        PowerMockito.when(mongoStoreDao.getById("id", StoreModel.class)).thenReturn(store);
        PowerMockito.when(mongoStoreDao.update(store)).thenReturn(store);
        mongoStorePersistenceLayer.updateStore(store, "id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStoreWhenNoStoreExists() {
        PowerMockito.when(mongoStoreDao.getById("id", StoreModel.class)).thenReturn(null);
        mongoStorePersistenceLayer.updateStore(new StoreModel(), "id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStoreWithNullId() {
        StoreModel store = new StoreModel();
        PowerMockito.when(mongoStoreDao.update(store)).thenReturn(store);
        mongoStorePersistenceLayer.updateStore(store, null);
    }

    @Test
    public void deleteStore() {
        StoreModel store = new StoreModel();
        PowerMockito.when(mongoStoreDao.getById("id", StoreModel.class)).thenReturn(store);
        PowerMockito.doNothing().when(mongoStoreDao).remove(store);
        mongoStorePersistenceLayer.deleteStore("id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteStoreWhenNoStore() {
        PowerMockito.when(mongoStoreDao.getById("id", StoreModel.class)).thenReturn(null);
        mongoStorePersistenceLayer.deleteStore("id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteStoreWhenNoId() {
        mongoStorePersistenceLayer.deleteStore("id");
    }
}