package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.CategoryRef;
import com.kgcorner.topspin.model.StoreRef;
import org.junit.Test;
import org.springframework.hateoas.Link;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class OfferDTOTest {
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
        CategoryRef category = mock(CategoryRef.class);
        StoreRef store = mock(StoreRef.class);
        OfferDTO offerDTO = new OfferDTO();
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
        offerDTO.addLink("abc.com", Link.REL_SELF);
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
        assertEquals(1, offerDTO.getLinks().size());
    }

}