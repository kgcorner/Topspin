package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */

public class StoreResourceTest {

    private StoreService storeService;
    private StoreResource storeResource;

    @Before
    public void setUp() throws Exception {
        storeResource = new StoreResource();
        storeService = mock(StoreService.class);
        Whitebox.setInternalState(storeResource, "storeService", storeService);
    }

    @Test
    public void createStore() {
        StoreDTO storeDTO = new StoreDTO();
        String storeId = "storeId";
        storeDTO.setStoreId(storeId);
        PowerMockito.when(storeService.createStore(storeDTO)).thenReturn(storeDTO);
        ResponseEntity<StoreDTO> storeResponse = storeResource.createStore(storeDTO);
        assertEquals(HttpStatus.CREATED, storeResponse.getStatusCode());
        assertEquals(storeResponse.getBody(), storeDTO);
        assertTrue(storeDTO.getLinks().get(0).getHref().contains(storeId));
    }

    @Test
    public void put() {
        StoreDTO storeDTO = new StoreDTO();
        String storeId = "storeId";
        storeDTO.setStoreId(storeId);
        PowerMockito.when(storeService.updateStore(storeDTO, storeId)).thenReturn(storeDTO);
        ResponseEntity<StoreDTO> storeResponse = storeResource.updateStore(storeId, storeDTO);
        assertEquals(HttpStatus.OK, storeResponse.getStatusCode());
        assertEquals(storeResponse.getBody(), storeDTO);
        assertTrue(storeDTO.getLinks().get(0).getHref().contains(storeId));
    }

    @Test
    public void get() {
        StoreDTO storeDTO = new StoreDTO();
        String storeId = "storeId";
        storeDTO.setStoreId(storeId);
        PowerMockito.when(storeService.getStore(storeId)).thenReturn(storeDTO);
        ResponseEntity<StoreDTO> storeResponse = storeResource.getStore(storeId);
        assertEquals(HttpStatus.OK, storeResponse.getStatusCode());
        assertEquals(storeResponse.getBody(), storeDTO);
        assertTrue(storeDTO.getLinks().get(0).getHref().contains(storeId));
    }

    @Test
    public void getAllStores() {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        int page = 1;
        int size = 10;

        for (int i = 0; i < size; i++) {
            StoreDTO dto = new StoreDTO();
            dto.setStoreId("storeId");
            dto.setName("Store");
            storeDTOS.add(dto);
        }
        when(storeService.getAllStores(page, size)).thenReturn(storeDTOS);
        ResponseEntity<Resources<StoreDTO>> allStores = storeResource.getAllStores(page, size);
        assertEquals(HttpStatus.OK, allStores.getStatusCode());
        Resources<StoreDTO> resources = new Resources<>(storeDTOS);
        assertEquals(((Resources<StoreDTO>) allStores.getBody()).getContent().size(), size);

    }

    @Test
    public void deleteStore() {
        String storeId = "storeId";
        storeResource.deleteStore(storeId);
        Mockito.verify(storeService).deleteStore(storeId);
    }

    @Test
    public void uploadBannerAndLogo() {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String storeId = "storeId";
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeId);
        when(storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo)).thenReturn(storeDTO);
        assertNotNull(storeResource.uploadBannerAndLogo(storeId, banner, logo, thumbnail));
    }
}