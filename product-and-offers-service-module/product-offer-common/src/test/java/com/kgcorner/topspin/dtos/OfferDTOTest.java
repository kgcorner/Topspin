package com.kgcorner.topspin.dtos;

import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class OfferDTOTest {

    @Test
    public void testOfferDTO() {
        String title = "title";
        String description = "description";
        List<String> thumbnails = Collections.emptyList();
        String url = "url";
        String surferPlaceholderUrl = "surferPlaceHolder";
        Date lastDate = new Date();
        String maxDiscount = "maxDiscount";
        String id = "id";
        boolean featured = true;
        Category category = mock(Category.class);
        Store store = mock(Store.class);
        DemoOfferDTO demoOfferDTO = new DemoOfferDTO();
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
        demoOfferDTO.setOfferId(id);
        OfferDTO offerDTO = new OfferDTO(demoOfferDTO);
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

    class DemoOfferDTO extends AbstractOffer {

        private String id;

        @Override
        public String getOfferId() {
            return id;
        }

        public void setOfferId(String id) {
            this.id =  id;
        }
    }


}