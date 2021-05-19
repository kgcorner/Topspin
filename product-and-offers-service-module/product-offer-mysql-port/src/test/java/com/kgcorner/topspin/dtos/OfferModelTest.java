package com.kgcorner.topspin.dtos;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class OfferModelTest {

    @Test
    public void testOfferModel() {
        String title = "title";
        String description = "description";
        List<String> thumbnails = new ArrayList<>();
        thumbnails.add("image");
        String url = "url";
        String surferPlaceholderUrl = "surferPlaceHolder";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String id = "id";
        boolean featured = true;
        Category category = mock(Category.class);
        Store store = mock(Store.class);
        OfferModel demoOfferDTO = new OfferModel();
        demoOfferDTO.setCategory(category);
        demoOfferDTO.setDescription(description);
        demoOfferDTO.setFeatured(featured);
        demoOfferDTO.setLastDate(lastDate);
        demoOfferDTO.setMaxDiscount(maxDiscount);
        demoOfferDTO.setStore(store);
        demoOfferDTO.setSurferPlaceholderUrl(surferPlaceholderUrl);
        demoOfferDTO.setThumbnails(thumbnails);
        demoOfferDTO.setTitle(title);
        demoOfferDTO.setUrl(url);
        demoOfferDTO.setId(id);
        assertEquals(title, demoOfferDTO.getTitle());
        assertEquals(description, demoOfferDTO.getDescription());
        assertEquals(url, demoOfferDTO.getUrl());
        assertEquals(thumbnails.size(), demoOfferDTO.getThumbnails().size());
        assertEquals(surferPlaceholderUrl, demoOfferDTO.getSurferPlaceholderUrl());
        assertEquals(lastDate, demoOfferDTO.getLastDate());
        assertEquals(maxDiscount, demoOfferDTO.getMaxDiscount());
        assertEquals(featured, demoOfferDTO.isFeatured());
        assertEquals(category, demoOfferDTO.getCategory());
        assertEquals(store, demoOfferDTO.getStore());
        assertEquals(id, demoOfferDTO.getOfferId());
    }
}