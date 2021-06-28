package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.AbstractStore;
import com.kgcorner.topspin.model.Category;
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

import java.util.ArrayList;
import java.util.List;


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
    private DemoStore demoStore;

    @Before
    public void setUp() throws Exception {
        storeResource = new StoreResource();
        demoStore = new DemoStore();
        storeService = PowerMockito.mock(StoreService.class);
        Whitebox.setInternalState(storeResource, "storeService", storeService);
    }

    @Test
    public void createStore() {
        StoreDTO storeDTO = new StoreDTO(demoStore);
        demoStore.setName("name");
        demoStore.setStoreId("id");
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


        StoreDTO storeDTO = new StoreDTO(demoStore);
        demoStore.setName("newName");
        demoStore.setStoreId("id");
        PowerMockito.when(storeService.updateStore(id, name, description, link, affiliateId,
            surferPlaceHolder, placeHolder)).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO.getName(), storeResource.put(id, name, affiliateId, link,
            surferPlaceHolder, placeHolder, description).getBody().getName());
    }

    @Test
    public void get() {
        StoreDTO storeDTO = new StoreDTO(demoStore);
        demoStore.setName("newName");
        demoStore.setStoreId("id");
        PowerMockito.when(storeService.getStore("id")).thenReturn(storeDTO);
        Assert.assertEquals(storeDTO.getName(), storeResource.get("id").getBody().getName());
    }

    class DemoStore extends AbstractStore {
        private String storeId;
        private List<Category> categories = new ArrayList<>();

        public void setStoreId(String id) {
            this.storeId = id;
        }

        @Override
        public String getStoreId() {
            return storeId;
        }

        @Override
        public List<Category> getCategories() {
            return categories;
        }
    }
}