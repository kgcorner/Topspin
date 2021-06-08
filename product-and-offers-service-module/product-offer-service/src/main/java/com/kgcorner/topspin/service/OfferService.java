package com.kgcorner.topspin.service;


import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.AbstractOffer;
import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.dtos.Store;
import com.kgcorner.topspin.dtos.factory.CategoryFactory;
import com.kgcorner.topspin.dtos.factory.OfferFactory;
import com.kgcorner.topspin.dtos.factory.StoreFactory;
import com.kgcorner.topspin.model.CategoryResponse;
import com.kgcorner.topspin.model.StoreResponse;
import com.kgcorner.topspin.persistence.OfferPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferCategoryPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferStorePersistenceLayer;
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

    @Autowired
    private ProductOfferCategoryPersistenceLayer categoryPersistenceLayer;

    @Autowired
    private ProductOfferStorePersistenceLayer storePersistenceLayer;

    public OfferDTO createOffer(String title, String description, Date lastDate, String categoryId,
                                String storeId, String url, String maxDiscount, String thumbnails, boolean featured) {
        CategoryResponse categoryDTO;
        StoreResponse storeDTO;
        try {
            categoryDTO = categoryClient.getCategory(categoryId);
            Category category = categoryPersistenceLayer.getCategory(categoryId);
            if(category == null) {
                category = categoryFactory.createCategory(categoryId, categoryDTO.getName(),
                    categoryDTO.getDescription());
                categoryPersistenceLayer.createCategory(category);
            }
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find category with id " + categoryId, x);
        }
        try {
            Store store = storePersistenceLayer.getStore(storeId);
            storeDTO = storeClient.get(storeId);
            if(store == null) {
                store = storeFactory.createStore(storeId, storeDTO.getName(), storeDTO.getDescription());
                storePersistenceLayer.createStore(store);
            }
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find store with id " + storeId, x);
        }
        var category = categoryFactory.createCategory(categoryDTO.getCategoryId(),
            categoryDTO.getName(), categoryDTO.getDescription());
        var store = storeFactory.createStore(storeDTO.getStoreId(), storeDTO.getName(), storeDTO.getDescription());
        var offer = offerFactory.createOffer(title, description, lastDate,category, store, url,
            maxDiscount, thumbnails, String.format(storeDTO.getSurferPlaceHolder(), url), featured);

        offer = offerPersistenceLayer.createOffer(offer);
        return new OfferDTO((AbstractOffer) offer);
    }

    public OfferDTO getOffer(String offerId) {
        var offer = offerPersistenceLayer.getOffer(offerId);
        return new OfferDTO((AbstractOffer) offer);
    }
}