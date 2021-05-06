package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dtos.Offer;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface OfferPersistenceLayer {

    /**
     * Returns an offer by id
     * @param offerId
     * @return
     */
    Offer getOffer(String offerId);

    /**
     * Creates an offer
     * @param offer
     * @return
     */
    Offer createOffer(Offer offer);

    /**
     * Updates an offer
     * @param offer
     * @return
     */
    Offer updateOffer(Offer offer);


    /**
     * Returns all offers page wise
     * @param page
     * @param itemPerPage
     * @return
     */
    List<Offer> getAll(int page, int itemPerPage);

    /**
     * Deletes offer by Id
     * @param offerId
     */
    void deleteOffer(String offerId);

    /**
     * Returns offer per page from a category
     * @param categoryId
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<Offer> getAllOfferFromCategory(String categoryId, int page, int itemsPerPage);

    /**
     * Returns all offer from a store
     * @param storeId
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<Offer> getAllOfferFromStore(String storeId, int page, int itemsPerPage);
}