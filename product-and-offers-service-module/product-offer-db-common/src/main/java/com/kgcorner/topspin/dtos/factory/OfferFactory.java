package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Offer;
import com.kgcorner.topspin.dtos.Store;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface OfferFactory {
    Offer createOffer(String title, String description, Date lastDate, Category category,
                      Store store, String url, String maxDiscount, String thumbnails, String surferPlaceHolder,
                      boolean featured);
}