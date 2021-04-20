package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.model.Store;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public interface StoreFactory {
    /**
     * Create instance of the Store
     * @return
     */
    Store createStore();


    Store createStore(String name, String link, String logo, boolean openOut,
                      boolean affiliated, String placeHolder, String surferPlaceHolder,
                      String searchUrl, String fixedUrls, String pidRegex, String gender,
                      String affiliateId, String maxCashback, String description,
                      String longDescription, String bannerImage, String thumbnailImage,
                      String largeImage, String tagLine, boolean active);

    Store createStore(String name, String link, boolean affiliated, String description,
                      String placeHolder, String surferPlaceHolder, String affiliateId);
}