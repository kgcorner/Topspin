package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.model.CategoryRef;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductOfferCategoryPersistenceLayer {
    CategoryRef createCategory(CategoryRef category);
    CategoryRef getCategory(String id);
}