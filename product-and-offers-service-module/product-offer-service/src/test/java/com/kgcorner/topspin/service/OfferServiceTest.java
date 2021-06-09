package com.kgcorner.topspin.service;

import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.*;
import com.kgcorner.topspin.dtos.factory.CategoryFactory;
import com.kgcorner.topspin.dtos.factory.OfferFactory;
import com.kgcorner.topspin.dtos.factory.StoreFactory;
import com.kgcorner.topspin.model.CategoryResponse;
import com.kgcorner.topspin.model.StoreResponse;
import com.kgcorner.topspin.persistence.OfferPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferCategoryPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferStorePersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class OfferServiceTest {

    private OfferService offerService;

    private OfferFactory offerFactory;


    private CategoryFactory categoryFactory;


    private StoreFactory storeFactory;


    private StoreClient storeClient;


    private CategoryClient categoryClient;


    private OfferPersistenceLayer offerPersistenceLayer;

    private ProductOfferCategoryPersistenceLayer categoryPersistenceLayer;

    private ProductOfferStorePersistenceLayer storePersistenceLayer;

    @Before
    public void setUp() throws Exception {
        offerService = new OfferService();
        offerFactory = mock(OfferFactory.class);
        categoryFactory = mock(CategoryFactory.class);
        storeFactory = mock(StoreFactory.class);
        storeClient = mock(StoreClient.class);
        categoryClient = mock(CategoryClient.class);
        offerPersistenceLayer = mock(OfferPersistenceLayer.class);
        categoryPersistenceLayer = mock(ProductOfferCategoryPersistenceLayer.class);
        storePersistenceLayer = mock(ProductOfferStorePersistenceLayer.class);
        Whitebox.setInternalState(offerService, "offerFactory", offerFactory);
        Whitebox.setInternalState(offerService, "categoryFactory", categoryFactory);
        Whitebox.setInternalState(offerService, "storeFactory", storeFactory);
        Whitebox.setInternalState(offerService, "storeClient", storeClient);
        Whitebox.setInternalState(offerService, "categoryClient", categoryClient);
        Whitebox.setInternalState(offerService, "offerPersistenceLayer", offerPersistenceLayer);
        Whitebox.setInternalState(offerService, "categoryPersistenceLayer", categoryPersistenceLayer);
        Whitebox.setInternalState(offerService, "storePersistenceLayer", storePersistenceLayer);
    }

    @Test
    public void createOffer() {
        String offerId = "offerId";
        String title = "title";
        String description = "description";
        String thumbnails = "image";
        String url = "url";
        String surferPlaceholderUrl = "surferPlaceHolder, %s";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String storeId = "storeId";
        String categoryId = "categoryId";
        Offer abstractOffer = mock(AbstractOffer.class);
        Store store = mock(Store.class);
        Category category = mock(Category.class);

        StoreResponse storeResponse = mock(StoreResponse.class);
        CategoryResponse categoryResponse = mock(CategoryResponse.class);
        when(storeResponse.getStoreId()).thenReturn(storeId);
        when(storeResponse.getName()).thenReturn(title);
        when(storeResponse.getDescription()).thenReturn(description);
        when(storeResponse.getSurferPlaceHolder()).thenReturn(surferPlaceholderUrl);

        when(categoryResponse.getCategoryId()).thenReturn(categoryId);
        when(categoryResponse.getName()).thenReturn(title);
        when(categoryResponse.getDescription()).thenReturn(description);

        when(categoryFactory.createCategory(categoryId, title, description)).thenReturn(category);
        when(storeFactory.createStore(storeId, title, description)).thenReturn(store);
        when(storeClient.get(storeId)).thenReturn(storeResponse);
        when(categoryClient.getCategory(categoryId)).thenReturn(categoryResponse);
        when(offerFactory.createOffer(title, description, lastDate, category, store,
            url, maxDiscount, thumbnails, String.format(surferPlaceholderUrl, url), true))
            .thenReturn(abstractOffer);
        when(categoryPersistenceLayer.getCategory(categoryId)).thenReturn(category);
        when(storePersistenceLayer.getStore(storeId)).thenReturn(store);
        when(abstractOffer.getOfferId()).thenReturn(offerId);
        when(offerPersistenceLayer.createOffer(abstractOffer)).thenReturn(abstractOffer);
        OfferDTO offerDTO = offerService.createOffer(title, description, lastDate, categoryId, storeId,
            url, maxDiscount, thumbnails, true);
        assertEquals(offerId, offerDTO.getOfferId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOfferNoStore() {
        String title = "title";
        String description = "description";
        String thumbnails = "image";
        String url = "url";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String storeId = "storeId";
        String categoryId = "categoryId";
        when(storeClient.get(storeId)).thenThrow(RuntimeException.class);
        offerService.createOffer(title, description, lastDate, categoryId, storeId,
            url, maxDiscount, thumbnails, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCategoryNoStore() {
        String title = "title";
        String description = "description";
        String thumbnails = "image";
        String url = "url";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String storeId = "storeId";
        String categoryId = "categoryId";
        when(categoryClient.getCategory(categoryId)).thenThrow(RuntimeException.class);
        offerService.createOffer(title, description, lastDate, categoryId, storeId,
            url, maxDiscount, thumbnails, true);
    }

    @Test
    public void getOffer() {
        String offerId = "offerId";
        Offer offer = mock(AbstractOffer.class);
        when(offerPersistenceLayer.getOffer(offerId)).thenReturn(offer);
        when(offer.getOfferId()).thenReturn(offerId);
        OfferDTO offerDTO = offerService.getOffer(offerId);
        assertEquals(offerId, offerDTO.getOfferId());
    }
}