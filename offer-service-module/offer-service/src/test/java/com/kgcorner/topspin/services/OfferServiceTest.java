package com.kgcorner.topspin.services;

import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.model.*;
import com.kgcorner.topspin.persistence.OfferPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferCategoryPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferStorePersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/09/21
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtils.class, AwsServices.class})
public class OfferServiceTest {

    private OfferPersistenceLayer offerPersistenceLayer;
    private ProductOfferCategoryPersistenceLayer categoryPersistenceLayer;
    private ProductOfferStorePersistenceLayer storePersistenceLayer;
    private StoreClient storeClient;
    private CategoryClient categoryClient;
    private String s3BucketName;
    private String bucketUrl;
    private OfferService offerService;


    @Before
    public void setup() {
        offerService = new OfferService();
        offerPersistenceLayer = mock(OfferPersistenceLayer.class);
        categoryPersistenceLayer = mock(ProductOfferCategoryPersistenceLayer.class);
        storePersistenceLayer = mock(ProductOfferStorePersistenceLayer.class);
        storeClient = mock(StoreClient.class);
        categoryClient = mock(CategoryClient.class);
        s3BucketName = "topspin-bucket";
        bucketUrl = "topspin-url";
        Whitebox.setInternalState(offerService, "offerPersistenceLayer", offerPersistenceLayer);
        Whitebox.setInternalState(offerService, "categoryPersistenceLayer", categoryPersistenceLayer);
        Whitebox.setInternalState(offerService, "storePersistenceLayer", storePersistenceLayer);
        Whitebox.setInternalState(offerService, "storeClient", storeClient);
        Whitebox.setInternalState(offerService, "categoryClient", categoryClient);
        Whitebox.setInternalState(offerService, "s3BucketName", s3BucketName);
        Whitebox.setInternalState(offerService, "bucketUrl", bucketUrl);
        mockStatic(BeanUtils.class);
    }

    @Test
    public void createOfferWithExistingCategoryAndStore() throws Exception {
        String categoryId = "catId";
        String storeId = "storeId";
        String offerId = "createdOfferId";
        CategoryRef categoryRef = new CategoryRef();
        categoryRef.setId(categoryId);
        StoreRef storeRef = new StoreRef();
        storeRef.setId(storeId);
        when(categoryPersistenceLayer.getCategory(categoryId)).thenReturn(categoryRef);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(storeRef);
        AbstractOffer createdOffer = mock(AbstractOffer.class);
        when(createdOffer.getOfferId()).thenReturn(offerId);
        OfferDTO offerDto = new OfferDTO();
        offerDto.setStore(storeRef);
        offerDto.setCategory(categoryRef);
        when(offerPersistenceLayer.createOffer(offerDto)).thenReturn(createdOffer);
        doNothing().when(BeanUtils.class, "copyProperties", createdOffer, offerDto);
        OfferDTO offer = offerService.createOffer(offerDto);
        assertEquals(offerDto, offer);
    }

    @Test
    public void createOfferWithNewCategoryAndStore() throws Exception {
        String categoryId = "catId";
        String storeId = "storeId";
        String offerId = "createdOfferId";
        CategoryRef categoryRef = new CategoryRef();
        categoryRef.setId(categoryId);
        StoreRef storeRef = new StoreRef();
        storeRef.setId(storeId);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeId);

        when(categoryPersistenceLayer.getCategory(categoryId)).thenReturn(null);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(null);
        when(storeClient.get(storeId)).thenReturn(storeDTO);
        when(categoryClient.getCategory(categoryId)).thenReturn(categoryDTO);

        when(categoryPersistenceLayer.createCategory(any(CategoryRef.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                CategoryRef categoryRef1 = invocationOnMock.getArgument(0);
                if(categoryRef1.getId().equals(categoryId)) {
                    return categoryRef1;
                }
                return null;
            }
        });

        when(storePersistenceLayer.createStore(any(StoreRef.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                StoreRef storeRef1 = invocationOnMock.getArgument(0);
                if(storeRef1.getId().equals(storeId)) {
                    return storeRef1;
                }
                return null;
            }
        });

        AbstractOffer createdOffer = mock(AbstractOffer.class);
        when(createdOffer.getOfferId()).thenReturn(offerId);
        OfferDTO offerDto = new OfferDTO();
        offerDto.setStore(storeRef);
        offerDto.setCategory(categoryRef);
        when(offerPersistenceLayer.createOffer(offerDto)).thenReturn(createdOffer);
        doNothing().when(BeanUtils.class, "copyProperties", createdOffer, offerDto);
        OfferDTO offer = offerService.createOffer(offerDto);
        assertEquals(offerDto, offer);
    }

    @Test
    public void updateOffer() throws Exception {
        OfferDTO offerDTO = new OfferDTO();
        AbstractOffer updatedOffer = mock(AbstractOffer.class);
        when(offerPersistenceLayer.updateOffer(offerDTO)).thenReturn(updatedOffer);
        OfferDTO updatedOfferDto = offerService.updateOffer(offerDTO);
        doNothing().when(BeanUtils.class, "copyProperties", updatedOffer, offerDTO);
        assertEquals(offerDTO, updatedOfferDto);
    }

    @Test
    public void getAllOffersWithStoreAndCategory() {
        int page = 1;
        int count = 100;
        boolean onlyFeatured = true;
        String storeId = "storeId";
        String categoryId = "categoryId";
        StoreRef storeRef = new StoreRef();
        CategoryRef categoryRef = new CategoryRef();
        when(storePersistenceLayer.getStore(storeId)).thenReturn(storeRef);
        when(categoryPersistenceLayer.getCategory(categoryId)).thenReturn(categoryRef);
        List<AbstractOffer> offerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            offerList.add(new OfferDTO());
        }
        when(offerPersistenceLayer.getAll(page, count, onlyFeatured, storeRef, categoryRef, false))
            .thenReturn(offerList);

        List<OfferDTO> allOffers = offerService.getAllOffers(page, count, onlyFeatured, storeId, categoryId, false);
        assertEquals(offerList, allOffers);
    }

    @Test
    public void getAllOffersWithoutStoreAndCategory() {
        int page = 1;
        int count = 100;
        boolean onlyFeatured = true;
        List<AbstractOffer> offerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            offerList.add(new OfferDTO());
        }
        when(offerPersistenceLayer.getAll(page, count, onlyFeatured, null, null, false)).thenReturn(offerList);
        List<OfferDTO> allOffers = offerService.getAllOffers(page, count, onlyFeatured, null, null, false);
        assertEquals(offerList, allOffers);
    }

    @Test
    public void deleteOffer() {
        String offerId = "offerId";
        AbstractOffer offer = new OfferDTO();
        when(offerPersistenceLayer.getOffer(offerId)).thenReturn(offer);
        offerService.deleteOffer(offerId);
        Mockito.verify(offerPersistenceLayer).deleteOffer(offerId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOfferWithnullId() {
        offerService.deleteOffer(null);
    }

    @Test
    public void getOffer() {
        String offerId = "offerId";
        AbstractOffer offer = new OfferDTO();
        when(offerPersistenceLayer.getOffer(offerId)).thenReturn(offer);
        assertNotNull(offerService.getOffer(offerId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOfferWithEmptyId() {
        offerService.getOffer(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOfferWithNonExistingId() {
        offerService.getOffer("awain");
    }

//    @Test
//    public void getOffersOfCategory() {
//        String categoryId = "categoryId";
//        int page = 0;
//        int count = 10;
//        CategoryRef categoryRef = new CategoryRef();
//        when(categoryPersistenceLayer.getCategory(categoryId)).thenReturn(categoryRef);
//        List<AbstractOffer> abstractOffers = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            abstractOffers.add(new OfferDTO());
//        }
//
//        when(offerPersistenceLayer.getAllOfferFromCategory(categoryRef, page, count)).thenReturn(abstractOffers);
//        List<OfferDTO> offersInCategory = offerService.getOffersOfCategory(categoryId, page, count);
//        assertEquals(count, offersInCategory.size());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getOffersOfCategoryWithEmptyId() {
//        offerService.getOffersOfCategory("", 0,10);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void getOffersOfCategoryWithNonExistingCategory() {
//        offerService.getOffersOfCategory("some id", 0,10);
//    }

    @Test
    public void getOffersOfStore() {
        String storeId = "storeId";
        int page = 0;
        int count = 10;
        StoreRef storeRef = new StoreRef();
        when(storePersistenceLayer.getStore(storeId)).thenReturn(storeRef);
        List<AbstractOffer> abstractOffers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            abstractOffers.add(new OfferDTO());
        }
        when(offerPersistenceLayer.getAllOfferFromStore(storeRef, page, count)).thenReturn(abstractOffers);
        List<OfferDTO> offersInStore = offerService.getOffersOfStores(storeId, page, count);
        assertEquals(count, offersInStore.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOffersOfStoreWithEmptyId() {
        offerService.getOffersOfStores("", 0,10);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOffersOfStoreWithNonExistingStore() {
        offerService.getOffersOfStores("some id", 0,10);
    }

    @Test
    public void uploadImage() throws IOException {
        String offerId = "offerId";
        AbstractOffer offer = new OfferDTO();
        InputStream inputStream = mock(InputStream.class);
        long fileSize = 1000;
        String origName = "name";
        when(offerPersistenceLayer.getOffer(offerId)).thenReturn(offer);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(inputStream);
        when(multipartFile.getOriginalFilename()).thenReturn(origName);
        when(multipartFile.getSize()).thenReturn(fileSize);
        when(offerPersistenceLayer.updateOffer(offer)).thenReturn(offer);
        assertNotNull(offerService.uploadImage(offerId, multipartFile));
    }

    @Test
    public void uploadImageWithThumbnailImage() throws IOException {
        String offerId = "offerId";
        AbstractOffer offer = new OfferDTO();
        offer.setThumbnails("thumbnail1");
        InputStream inputStream = mock(InputStream.class);
        long fileSize = 1000;
        String origName = "name";
        when(offerPersistenceLayer.getOffer(offerId)).thenReturn(offer);
        AwsServices awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(inputStream);
        when(multipartFile.getOriginalFilename()).thenReturn(origName);
        when(multipartFile.getSize()).thenReturn(fileSize);
        when(offerPersistenceLayer.updateOffer(offer)).thenReturn(offer);
        assertNotNull(offerService.uploadImage(offerId, multipartFile));
    }

    @Test
    public void getAllBanners() {
        List<AbstractOffer> banners = new ArrayList<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            banners.add(new OfferDTO());
        }
        when(offerPersistenceLayer.getBanners()).thenReturn(banners);
        List<OfferDTO> allBanners = offerService.getBanners();
        assertEquals(size, allBanners.size());
    }

    @Test
    public void getStores() {
        List<StoreRef> stores = new ArrayList<>();
        for(int i = 0; i<9; i++) {
            stores.add(new StoreRef());
        }
        when(storePersistenceLayer.getStoresWithOfferCount()).thenReturn(stores);
        List<StoreRef> result = offerService.getStores();
        assertEquals(stores, result);
    }
}