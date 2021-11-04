package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.StoreDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 01/05/21
 */

public class StoreClientTest {

    private StoreResourceClient resourceClient;
    private StoreClient client;

    @Before
    public void setUp() throws Exception {
        resourceClient = PowerMockito.mock(StoreResourceClient.class);
        client = new StoreClient();
        Whitebox.setInternalState(client, "storeResourceClient", resourceClient);
    }

    @Test
    public void createStore() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        StoreDTO storeDTO = new StoreDTO();
        PowerMockito.when(resourceClient.createStore(storeDTO)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO, client.createStore(storeDTO));
    }

    @Test
    public void put() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        String id = "id";
        StoreDTO storeDTO = new StoreDTO();
        PowerMockito.when(resourceClient.put(id, storeDTO)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO, client.put(id, storeDTO ));
    }

    @Test
    public void get() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        StoreDTO StoreDTO = new StoreDTO();
        String id = "id";
        PowerMockito.when(resourceClient.get(id)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(StoreDTO);
        Assert.assertEquals(StoreDTO, client.get(id));
    }

    @Test
    public void getAllStores() {
        int page = 0, itemCount = 10;
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        Resources resources = PowerMockito.mock(Resources.class);
        PowerMockito.when(resourceClient.getAllStores(page, itemCount)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(resources);
        List<StoreDTO> StoreDTOS = new ArrayList<>();
        PowerMockito.when(resources.getContent()).thenReturn(StoreDTOS);
        Assert.assertEquals(StoreDTOS, client.getAllStores(page, itemCount));
    }
}