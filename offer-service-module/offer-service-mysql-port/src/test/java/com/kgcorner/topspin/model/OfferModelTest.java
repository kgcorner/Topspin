package com.kgcorner.topspin.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class OfferModelTest {
    @Test
    public void testOfferDTO() {
        String title = "title";
        String description = "description";
        String thumbnails = "t";
        String url = "url";
        String surferPlaceholderUrl = "surferPlaceHolder";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String id = "id";
        boolean featured = true;
        CategoryRef category = new CategoryReferenceModel();
        StoreRef store = new StoreReferenceModel();
        OfferModel offerDTO = new OfferModel();
        offerDTO.setCategory(category);
        offerDTO.setDescription(description);
        offerDTO.setFeatured(featured);
        offerDTO.setLastDate(lastDate);
        offerDTO.setMaxDiscount(maxDiscount);
        offerDTO.setStore(store);
        offerDTO.setSurferPlaceholderUrl(surferPlaceholderUrl);
        offerDTO.setThumbnails(thumbnails);
        offerDTO.setTitle(title);
        offerDTO.setUrl(url);
        offerDTO.setOfferId(id);
        assertEquals(title, offerDTO.getTitle());
        assertEquals(description, offerDTO.getDescription());
        assertEquals(url, offerDTO.getUrl());
        assertEquals(thumbnails, offerDTO.getThumbnails());
        assertEquals(surferPlaceholderUrl, offerDTO.getSurferPlaceholderUrl());
        assertEquals(lastDate, offerDTO.getLastDate());
        assertEquals(maxDiscount, offerDTO.getMaxDiscount());
        assertEquals(featured, offerDTO.isFeatured());
        assertEquals(category, offerDTO.getCategory());
        assertEquals(store, offerDTO.getStore());
        assertEquals(id, offerDTO.getOfferId());
    }
}