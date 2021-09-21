package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.model.AbstractOffer;
import com.kgcorner.topspin.model.CategoryRef;
import com.kgcorner.topspin.model.StoreRef;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

public interface OfferPersistenceLayer {
    /**
     * Returns an offer by id
     * @param offerId
     * @return
     */
    AbstractOffer getOffer(String offerId);

    /**
     * Creates an offer
     * @param offer
     * @return
     */
    AbstractOffer createOffer(AbstractOffer offer);

    /**
     * Updates an offer
     * @param offer
     * @return
     */
    AbstractOffer updateOffer(AbstractOffer offer);


    /**
     * Returns all offers page wise
     * @param page
     * @param itemPerPage
     * @param onlyFeatured
     * @param store
     * @param category
     * @return
     */
    List<AbstractOffer> getAll(int page, int itemPerPage, boolean onlyFeatured, StoreRef store, CategoryRef category);

    /**
     * Returns all offers page wise
     * @param page
     * @param itemPerPage
     * @return
     */
    List<AbstractOffer> getAll(int page, int itemPerPage);
    /**
     * Deletes offer by Id
     * @param offerId
     */
    void deleteOffer(String offerId);

    /**
     * Returns offer per page from a category
     * @param category
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<AbstractOffer> getAllOfferFromCategory(CategoryRef category, int page, int itemsPerPage);

    /**
     * Returns all offer from a store
     * @param store
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<AbstractOffer> getAllOfferFromStore(StoreRef store, int page, int itemsPerPage);

    /**
     * Returns all active offers for which {@link AbstractOffer#isBanner()} is true
     * @return
     */
    List<AbstractOffer> getBanners();
}