package com.kgcorner.topspin.service;

import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.AbstractStore;
import com.kgcorner.topspin.persistence.StorePersistenceLayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AwsServices.class})
public class StoreServiceTest {
    private StoreService storeService;
    private StorePersistenceLayer persistenceLayer;
    private String bucketUrl = "bucketUrl";
    private String s3BucketName = "s3BucketName";
    
    @Before
    public void setUp() throws Exception {
        persistenceLayer = mock(StorePersistenceLayer.class);
        storeService = new StoreService();
        Whitebox.setInternalState(storeService, "storePersistenceLayer", persistenceLayer);
        Whitebox.setInternalState(storeService, "s3BucketName", s3BucketName);
        Whitebox.setInternalState(storeService, "bucketUrl", bucketUrl);
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

    @Test
    public void updateBannerAndLogo() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);

        InputStream logoInputStream = mock(InputStream.class);
        InputStream bannerInputStream = mock(InputStream.class);
        InputStream thumbnailInputStream = mock(InputStream.class);

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("amazing store");

        String bannerFileName = "banner.jpg";
        String logoFileName = "logo.jpg";
        String thumbnailFileName = "thumbnail.jpg";
        String storeId = "storeId";

        when(banner.getOriginalFilename()).thenReturn(bannerFileName);
        when(logo.getOriginalFilename()).thenReturn(logoFileName);
        when(thumbnail.getOriginalFilename()).thenReturn(thumbnailFileName);

        when(banner.getInputStream()).thenReturn(bannerInputStream);
        when(logo.getInputStream()).thenReturn(logoInputStream);
        when(thumbnail.getInputStream()).thenReturn(thumbnailInputStream);
        when(persistenceLayer.getStore(storeId)).thenReturn(storeDTO);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        when(persistenceLayer.updateStore(any(StoreDTO.class), anyString())).thenReturn(storeDTO);
        StoreDTO storeDTO1 = storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
        Assert.assertNotNull(storeDTO1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidBanner() throws IOException {
        String storeId = "storeId";
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        when(banner.getOriginalFilename()).thenReturn(null);
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("amazing store");
        when(persistenceLayer.getStore(storeId)).thenReturn(storeDTO);
        storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidLogo() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String storeId = "storeId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn(null);
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("amazing store");
        when(persistenceLayer.getStore(storeId)).thenReturn(storeDTO);
        storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidThumbnail() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String storeId = "storeId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn("logo.jpg");
        when(thumbnail.getOriginalFilename()).thenReturn(null);
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("amazing store");
        when(persistenceLayer.getStore(storeId)).thenReturn(storeDTO);
        storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBannerAndLogoInValidInvalidStream() throws IOException {
        MultipartFile banner = mock(MultipartFile.class);
        MultipartFile logo = mock(MultipartFile.class);
        MultipartFile thumbnail = mock(MultipartFile.class);
        String storeId = "storeId";

        when(banner.getOriginalFilename()).thenReturn("banner.jpg");
        when(logo.getOriginalFilename()).thenReturn("logo.jpg");
        when(thumbnail.getOriginalFilename()).thenReturn("thumbmnail.jpg");
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("amazing store");
        when(persistenceLayer.getStore(storeId)).thenReturn(storeDTO);
        when(banner.getInputStream()).thenThrow(IOException.class);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
    }

    @Test
    public void deleteStore() {
        String storeId = "storeId";
        storeService.deleteStore(storeId);
        Mockito.verify(persistenceLayer).deleteStore(storeId);
    }
}