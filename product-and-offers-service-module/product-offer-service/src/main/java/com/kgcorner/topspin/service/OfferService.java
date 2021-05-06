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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Service
public class OfferService {

    @Autowired
    private OfferFactory offerFactory;

    @Autowired
    private CategoryFactory categoryFactory;

    @Autowired
    private StoreFactory storeFactory;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private OfferPersistenceLayer offerPersistenceLayer;

    public OfferDTO createOffer(String title, String description, Date lastDate, String categoryId,
                                String storeId, String url, String maxDiscount, String thumbnails, boolean featured) {
        CategoryResponse categoryDTO;
        StoreResponse storeDTO;
        try {
            categoryDTO = categoryClient.getCategory(categoryId);
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find category with id " + categoryId, x);
        }
        try {
            storeDTO = storeClient.get(storeId);
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find store with id " + storeId, x);
        }
        Category category = categoryFactory.createCategory(categoryDTO.getCategoryId(),
            categoryDTO.getName(), categoryDTO.getDescription());
        Store store = storeFactory.createStore(storeDTO.getStoreId(), storeDTO.getName(), storeDTO.getDescription());
        Offer offer = offerFactory.createOffer(title, description, lastDate,category, store, url,
            maxDiscount, thumbnails, String.format(storeDTO.getSurferPlaceHolder(), url), featured);

        offer = offerPersistenceLayer.createOffer(offer);
        return new OfferDTO((AbstractOffer) offer);
    }

    public OfferDTO getOffer(String offerId) {
        Offer offer = offerPersistenceLayer.getOffer(offerId);
        return new OfferDTO((AbstractOffer) offer);
    }
}