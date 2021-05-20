package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Offer;
import com.kgcorner.topspin.dtos.Store;

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
     * @param category
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<Offer> getAllOfferFromCategory(Category category, int page, int itemsPerPage);

    /**
     * Returns all offer from a store
     * @param store
     * @param page
     * @param itemsPerPage
     * @return
     */
    List<Offer> getAllOfferFromStore(Store store, int page, int itemsPerPage);
}