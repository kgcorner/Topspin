package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/10/21
 */

public class LocalStoreClientTest {

    private LocalStoreClient localStoreClient;
    private StoreService storeService;

    @Before
    public void setUp() throws Exception {
        localStoreClient = new LocalStoreClient();
        storeService = mock(StoreService.class);
        Whitebox.setInternalState(localStoreClient, "storeService", storeService);
    }

    @Test
    public void createStore() {
        StoreDTO storeDTO = new StoreDTO();
        localStoreClient.createStore(storeDTO);
        verify(storeService).createStore(storeDTO);
    }

    @Test
    public void put() {
        String storeId = "storeId";
        StoreDTO storeDTO = new StoreDTO();
        localStoreClient.put(storeId, storeDTO);
        verify(storeService).updateStore(storeDTO, storeId);
    }

    @Test
    public void get() {
        String storeId = "storeId";
        localStoreClient.get(storeId);
        verify(storeService).getStore(storeId);
    }

    @Test
    public void getAllStores() {
        int page = 0;
        int count = 10;
        localStoreClient.getAllStores(page, count);
        verify(storeService).getAllStores(page, count);
    }
}