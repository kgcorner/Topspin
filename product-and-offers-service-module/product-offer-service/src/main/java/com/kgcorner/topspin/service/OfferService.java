package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.*;
import com.kgcorner.topspin.dtos.factory.OfferFactory;
import com.kgcorner.topspin.persistence.OfferPersistenceLayer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Service
public class OfferService extends AbstractProductOfferService{

    @Autowired
    private OfferFactory offerFactory;

    @Autowired
    private OfferPersistenceLayer offerPersistenceLayer;



    public OfferDTO createOffer(String title, String description, Date lastDate, String categoryId,
                                String storeId, String url, String maxDiscount, String thumbnails, boolean featured) {
        CategoryDTO categoryDTO = getCategoryDTO(categoryId);
        StoreDTO storeDTO = getStoreDTO(storeId);
        Offer offer = createOffer(title, description, lastDate, url, maxDiscount, thumbnails, featured, categoryDTO, storeDTO);

        offer = offerPersistenceLayer.createOffer(offer);
        return new OfferDTO((AbstractOffer) offer);
    }



    public OfferDTO updateOffer(String offerId, String title, String description, Date lastDate, String categoryId,
                                String storeId, String url, String maxDiscount, String thumbnails, boolean featured) {

        Offer existingOffer = offerPersistenceLayer.getOffer(offerId);
        CategoryDTO categoryDTO = getCategoryDTO(categoryId);
        StoreDTO storeDTO = getStoreDTO(storeId);
        Offer offer = createOffer(title, description, lastDate, url, maxDiscount, thumbnails, featured, categoryDTO, storeDTO);
        BeanUtils.copyProperties(offer, existingOffer, "offerId");

        offer = offerPersistenceLayer.updateOffer(existingOffer);
        return new OfferDTO((AbstractOffer) offer);
    }

    public void deleteOffer(String offerId) {
        offerPersistenceLayer.deleteOffer(offerId);
    }

    public OfferDTO getOffer(String offerId) {
        var offer = offerPersistenceLayer.getOffer(offerId);
        return new OfferDTO((AbstractOffer) offer);
    }



    private Offer createOffer(String title, String description, Date lastDate, String url,
                              String maxDiscount, String thumbnails, boolean featured,
                              CategoryDTO categoryDTO, StoreDTO storeDTO) {
        var category = getCategory(categoryDTO);
        var store = getStore(storeDTO);
        return offerFactory.createOffer(title, description, lastDate, category, store, url,
            maxDiscount, thumbnails, String.format(storeDTO.getSurferPlaceHolder(), url), featured);
    }
}