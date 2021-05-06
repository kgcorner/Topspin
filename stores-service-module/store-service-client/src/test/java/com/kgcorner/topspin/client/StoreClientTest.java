package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.StoreResponse;
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
        StoreResponse StoreResponse = new StoreResponse();
        PowerMockito.when(resourceClient.createStore("name", "description", "link",
            "affiliateId","surferPlaceHolder", "placeholder")).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(StoreResponse);
        Assert.assertEquals(StoreResponse, client.createStore("name", "description", "link",
            "affiliateId","surferPlaceHolder", "placeholder"));
    }

    @Test
    public void put() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        StoreResponse StoreResponse = new StoreResponse();
        String id = "id";
        String name = "newName";
        String description = "Description";
        String link = "link";
        String affiliateId = "affiliateId";
        String placeHolder = "placeHolder";
        String surferPlaceHolder = "surferPlaceHolder";
        PowerMockito.when(resourceClient.put(id, name, description, link, affiliateId,
            surferPlaceHolder, placeHolder)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(StoreResponse);
        Assert.assertEquals(StoreResponse, client.put(id, name, description, link, affiliateId,
            surferPlaceHolder, placeHolder ));
    }

    @Test
    public void get() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        StoreResponse StoreResponse = new StoreResponse();
        String id = "id";
        PowerMockito.when(resourceClient.get(id)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(StoreResponse);
        Assert.assertEquals(StoreResponse, client.get(id));
    }

    @Test
    public void getAllStores() {
        int page = 0, itemCount = 10;
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        Resources resources = PowerMockito.mock(Resources.class);
        PowerMockito.when(resourceClient.getAllStores(page, itemCount)).thenReturn(entity);
        PowerMockito.when(entity.getBody()).thenReturn(resources);
        List<StoreResponse> StoreResponseS = new ArrayList<>();
        PowerMockito.when(resources.getContent()).thenReturn(StoreResponseS);
        Assert.assertEquals(StoreResponseS, client.getAllStores(page, itemCount));
    }
}