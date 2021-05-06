package com.kgcorner.topspin.dtos;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public class OfferDTO extends ResourceSupport implements Offer {

    private AbstractOffer offer;

    public OfferDTO(AbstractOffer offer) {
        offer = offer;
    }

    @Override
    public String getTitle() {
        return offer.getTitle();
    }

    @Override
    public String getDescription() {
        return offer.getDescription();
    }

    @Override
    public List<String> getThumbnails() {
        return offer.getThumbnails();
    }

    @Override
    public String getUrl() {
        return offer.getUrl();
    }

    @Override
    public String getSurferPlaceholderUrl() {
        return offer.getSurferPlaceholderUrl();
    }

    @Override
    public Category getCategory() {
        return offer.getCategory();
    }

    @Override
    public Store getStore() {
        return offer.getStore();
    }

    @Override
    public Date getLastDate() {
        return offer.getLastDate();
    }

    @Override
    public String getMaxDiscount() {
        return offer.getMaxDiscount();
    }

    @Override
    public boolean isFeatured() {
        return offer.isFeatured();
    }

    @Override
    public String getOfferId() {
        return offer.getOfferId();
    }
}