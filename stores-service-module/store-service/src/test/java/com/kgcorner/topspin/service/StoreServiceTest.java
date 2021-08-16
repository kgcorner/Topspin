package com.kgcorner.topspin.service;

import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.AbstractStore;
import com.kgcorner.topspin.persistence.StorePersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */

public class StoreServiceTest {
    private StoreService storeService;
    private StorePersistenceLayer persistenceLayer;

    @Before
    public void setUp() throws Exception {
        persistenceLayer = mock(StorePersistenceLayer.class);
        storeService = new StoreService();
        Whitebox.setInternalState(storeService, "storePersistenceLayer", persistenceLayer);
    }

    @Test
    public void createStore() {
        String name = "amazon";
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName(name);
        AbstractStore createdStore = mock(AbstractStore.class);
        when(createdStore.getName()).thenReturn(name);

        when(persistenceLayer.createStore(storeDTO)).thenReturn(createdStore);
        StoreDTO store = storeService.createStore(storeDTO);
        assertEquals(name, store.getName());
    }

    @Test
    public void updateStore() {
        String storeId = "storeID";
        String name = "amazon";
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeId);
        storeDTO.setName(name);
        AbstractStore updatedStore = mock(AbstractStore.class);
        when(updatedStore.getName()).thenReturn(name);
        when(persistenceLayer.updateStore(storeDTO, storeId)).thenReturn(updatedStore);
        StoreDTO store = storeService.updateStore(storeDTO, storeId);
        assertEquals(name, store.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStoreWithWrongId() {
        String storeId = "storeID";
        String name = "amazon";
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeId);
        storeDTO.setName(name);
        AbstractStore updatedStore = mock(AbstractStore.class);
        when(updatedStore.getName()).thenReturn(name);
        when(persistenceLayer.updateStore(storeDTO, storeId)).thenThrow(new IllegalArgumentException());
        storeService.updateStore(storeDTO, storeId);
    }

    @Test
    public void getStore() {
        String storeId = "storeID";
        String name = "amazon";
        AbstractStore foundStore = mock(AbstractStore.class);
        when(foundStore.getName()).thenReturn(name);
        when(persistenceLayer.getStore(storeId)).thenReturn(foundStore);
        StoreDTO store = storeService.getStore(storeId);
        assertEquals(name, store.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStoreNoStore() {
        String storeId = "storeID";
        when(persistenceLayer.getStore(storeId)).thenReturn(null);
        storeService.getStore(storeId);
    }

    @Test
    public void getAllStores() {
        String name = "amazon";
        int page = 1;
        int size = 10;
        List<AbstractStore> stores = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            AbstractStore foundStore = mock(AbstractStore.class);
            when(foundStore.getName()).thenReturn(name);
            stores.add(foundStore);
        }
        when(persistenceLayer.getAllStores(page, size)).thenReturn(stores);
        List<StoreDTO> allStores = storeService.getAllStores(page, size);
        assertEquals(size, allStores.size());
        assertEquals(name, allStores.get(1).getName());
    }
}