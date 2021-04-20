package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ControllerLinkBuilder.class)
public class StoreResourceTest {

    private StoreResource storeResource;
    private StoreService storeService;

    @Before
    public void setUp() throws Exception {
        storeResource = new StoreResource();
        storeService = PowerMockito.mock(StoreService.class);
        Whitebox.setInternalState(storeResource, "storeService", storeService);
    }

    @Test
    public void createStore() {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("name");
        storeDTO.setStoreId("id");
        PowerMockito.when(storeService.createStore("name", "description", "link",
            "affiliateId","surferPlaceHolder", "placeholder")).thenReturn(storeDTO);
        ResponseEntity<StoreDTO> storeResponse = storeResource.createStore("name", "description", "link",
            "affiliateId", "surferPlaceHolder", "placeholder");
        Assert.assertEquals(storeDTO.getName(), storeResponse.getBody().getName());
    }

    @Test
    public void put() {
        String id = "id";
        String name = "newName";
        String description = "Description";
        String link = "link";
        String affiliateId = "affiliateId";
        String placeHolder = "placeHolder";
        String surferPlaceHolder = "surferPlaceHolder";


        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("newName");
        storeDTO.setStoreId("id");
        PowerMockito.when(storeService.updateStore(id, name, description, link, affiliateId,
            surferPlaceHolder, placeHolder)).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO.getName(), storeResource.put(id, name, affiliateId, link,
            surferPlaceHolder, placeHolder, description).getBody().getName());
    }

    @Test
    public void get() {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("newName");
        storeDTO.setStoreId("id");
        PowerMockito.when(storeService.getStore("id")).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO.getName(), storeResource.get("id").getBody().getName());
    }
}