package com.kgcorner.topspin.dtos.factory;

import com.kgcorner.topspin.dtos.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class OfferModelFactoryTest {

    @Test
    public void testOfferModelFactory() {
        String title = "title";
        String description = "description";
        String thumbnails = "image";
        String url = "url";
        String surferPlaceholderUrl = "surferPlaceHolder";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        Category category = new CategoryReferenceModel();
        Store store = new StoreReferenceModel();

        Offer offer = new OfferModelFactory().createOffer(title, description, lastDate, category, store, url,
            maxDiscount, thumbnails, surferPlaceholderUrl, true);
        assertEquals(title, offer.getTitle());
        assertEquals(description, offer.getDescription());
        assertEquals(thumbnails, offer.getThumbnails().get(0));
        assertEquals(url, offer.getUrl());
        assertEquals(surferPlaceholderUrl, offer.getSurferPlaceholderUrl());
        assertEquals(lastDate, offer.getLastDate());
        assertNotNull(offer.getOfferId());
        assertEquals(store, offer.getStore());
        assertEquals(category, offer.getCategory());
        assertTrue(offer.isFeatured());

    }

}