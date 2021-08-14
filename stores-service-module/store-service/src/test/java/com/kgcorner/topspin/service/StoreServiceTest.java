package com.kgcorner.topspin.service;

import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.Store;
import com.kgcorner.topspin.model.factory.StoreFactory;
import com.kgcorner.topspin.persistence.StorePersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class StoreServiceTest {
    private StoreFactory storeFactory;
    private StoreService storeService;
    private StorePersistenceLayer storePersistenceLayer;

    @Before
    public void setUp() throws Exception {
        storeService = new StoreService();
        storeFactory = mock(StoreFactory.class);
        storePersistenceLayer = mock(StorePersistenceLayer.class);
        Whitebox.setInternalState(storeService, "storeFactory", storeFactory);
        Whitebox.setInternalState(storeService,"storePersistenceLayer", storePersistenceLayer);
    }

    @Test
    public void createStore() {
        String name = "amazon";
        String description = "store description";
        String link = "amazon.com";
        String affiliateId = "aff";
        String surferPlaceholder = "sLink";
        String placeholderLink = "pLink";
        String storeId = "StoreId";
        Store store = mock(Store.class);
        when(store.getStoreId()).thenReturn(storeId);
        when(storeFactory.createStore(name, link, true, description, placeholderLink,
            surferPlaceholder, affiliateId )).thenReturn(store);
        when(storePersistenceLayer.createStore(store)).thenReturn(store);
        StoreDTO storeDTO = storeService.createStore(name, description, link,
            affiliateId, surferPlaceholder, placeholderLink);
        assertNotNull(storeDTO);
        assertEquals(storeId, storeDTO.getStoreId());
    }

    @Test
    public void updateStore() {
        String name = "amazon";
        String description = "store description";
        String link = "amazon.com";
        String affiliateId = "aff";
        String surferPlaceholder = "sLink";
        String placeholderLink = "pLink";
        String storeId = "StoreId";
        Store store = mock(Store.class);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(store);
        when(store.getStoreId()).thenReturn(storeId);
        when(storeFactory.createStore(name, link, true, description, placeholderLink,
            surferPlaceholder, affiliateId )).thenReturn(store);
        StoreDTO storeDTO = storeService.updateStore(storeId, name, description, link,
            affiliateId, surferPlaceholder, placeholderLink);
        assertNotNull(storeDTO);
        assertEquals(storeId, storeDTO.getStoreId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStoreInvalidStore() {
        String name = "amazon";
        String description = "store description";
        String link = "amazon.com";
        String affiliateId = "aff";
        String surferPlaceholder = "sLink";
        String placeholderLink = "pLink";
        String storeId = "StoreId";
        Store store = mock(Store.class);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(null);
        storeService.updateStore(storeId, name, description, link,
            affiliateId, surferPlaceholder, placeholderLink);
    }

    @Test
    public void getStore() {
        String storeId = "Storeid";
        Store store = mock(Store.class);
        when(store.getStoreId()).thenReturn(storeId);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(store);
        StoreDTO storeDTO = storeService.getStore(storeId);
        assertNotNull(storeDTO);
        assertEquals(storeId, storeDTO.getStoreId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStoreNoStore() {
        String storeId = "Storeid";
        when(storePersistenceLayer.getStore(storeId)).thenReturn(null);
        storeService.getStore(storeId);
    }

    @Test
    public void getAllStores() {
        List<Store> storeList = new ArrayList<>();
        storeList.add(mock(Store.class));
        storeList.add(mock(Store.class));
        storeList.add(mock(Store.class));
        int size = 10;
        int page = 1;
        when(storePersistenceLayer.getAllStores(page, size)).thenReturn(storeList);
        List<StoreDTO> allStores = storeService.getAllStores(page, size);
        assertNotNull(allStores);
        assertEquals(storeList.size(), allStores.size());
    }

    @Test
    public void getAllStoresWithCategory() {
        List<Store> storeList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        Store mockedStore = mock(Store.class);
        Category category = mock(Category.class);
        categoryList.add(category);
        storeList.add(mockedStore);
        when(mockedStore.getCategories()).thenReturn(categoryList);
        int size = 10;
        int page = 1;
        when(storePersistenceLayer.getAllStores(page, size)).thenReturn(storeList);
        List<StoreDTO> allStores = storeService.getAllStores(page, size);
        assertNotNull(allStores);
        assertEquals(storeList.size(), allStores.size());
    }
}