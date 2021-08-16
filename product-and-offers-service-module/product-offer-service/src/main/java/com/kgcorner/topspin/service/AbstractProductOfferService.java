package com.kgcorner.topspin.service;


import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.dtos.Store;
import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.dtos.factory.CategoryFactory;
import com.kgcorner.topspin.dtos.factory.StoreFactory;
import com.kgcorner.topspin.persistence.ProductOfferCategoryPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferStorePersistenceLayer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/08/21
 */

public abstract class AbstractProductOfferService {

    @Autowired
    protected CategoryFactory categoryFactory;

    @Autowired
    protected StoreFactory storeFactory;

    @Autowired
    protected StoreClient storeClient;

    @Autowired
    protected CategoryClient categoryClient;

    @Autowired
    protected ProductOfferCategoryPersistenceLayer categoryPersistenceLayer;

    @Autowired
    protected ProductOfferStorePersistenceLayer storePersistenceLayer;


    protected StoreDTO getStoreDTO(String storeId) {
        StoreDTO storeDTO;
        try {
            storeDTO = storeClient.get(storeId);
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find store with id " + storeId, x);
        }
        return storeDTO;
    }

    protected Store getStore(StoreDTO storeDTO) {
        Store store = null;
        try {
            store = storePersistenceLayer.getStore(storeDTO.getStoreId());
        }catch (IllegalArgumentException x) {
            store = storeFactory.createStore(storeDTO.getStoreId(), storeDTO.getName(), storeDTO.getDescription());
            storePersistenceLayer.createStore(store);
        }
        return store;
    }

    protected CategoryDTO getCategoryDTO(String categoryId) {
        CategoryDTO categoryDTO;
        try {
            categoryDTO = categoryClient.getCategory(categoryId);

        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find category with id " + categoryId, x);
        }
        return categoryDTO;
    }

    protected Category getCategory(CategoryDTO categoryDTO) {
        Category category = null;
        try {
            category = categoryPersistenceLayer.getCategory(categoryDTO.getCategoryId());
        } catch (IllegalArgumentException x) {
            category = categoryFactory.createCategory(categoryDTO.getCategoryId(), categoryDTO.getName(),
                categoryDTO.getDescription());
            categoryPersistenceLayer.createCategory(category);
        }
        return category;
    }
}