package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Offer;
import com.kgcorner.topspin.dtos.OfferModel;
import com.kgcorner.topspin.dtos.Store;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Component
public class OfferModelFactory implements OfferFactory {

    @Override
    public Offer createOffer(String title, String description, Date lastDate, Category category,
                             Store store, String url, String maxDiscount, String thumbnails,
                             String surferPlaceHolder,
                             boolean featured) {
        OfferModel offer = new OfferModel();
        offer.setCategory(category);
        offer.setStore(store);
        offer.setDescription(description);
        offer.setThumbnails(thumbnails);
        offer.setFeatured(featured);
        offer.setLastDate(lastDate);
        offer.setMaxDiscount(maxDiscount);
        offer.setSurferPlaceholderUrl(surferPlaceHolder);
        offer.setUrl(url);
        offer.setTitle(title);
        offer.setOfferId(generateId());
        return offer;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}