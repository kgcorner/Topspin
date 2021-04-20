package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.model.Store;
import com.kgcorner.topspin.model.StoreModel;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@Component
public class StoreFactoryImpl implements StoreFactory {
    @Override
    public Store createStore() {
        return new StoreModel();
    }

    @Override
    public Store createStore(String name, String link, String logo, boolean openOut, boolean affiliated,
                             String placeHolder, String surferPlaceHolder, String searchUrl, String fixedUrls,
                             String pidRegex, String gender, String affiliateId, String maxCashback,
                             String description, String longDescription, String bannerImage,
                             String thumbnailImage, String largeImage, String tagLine, boolean active) {
        StoreModel store = new StoreModel();
        store.setName(name);
        store.setActive(active);
        store.setAffiliated(affiliated);
        store.setAffiliateId(affiliateId);
        store.setBannerImage(bannerImage);
        store.setDescription(description);
        store.setFixedUrls(fixedUrls);
        store.setGender(gender);
        store.setLargeImage(largeImage);
        store.setLink(link);
        store.setCategories(Collections.emptyList());
        store.setLogo(logo);
        store.setLongDescription(longDescription);
        store.setSurferPlaceHolder(surferPlaceHolder);
        store.setPlaceHolder(placeHolder);
        store.setSearchUrl(searchUrl);
        store.setTagLine(tagLine);
        store.setPidRegex(pidRegex);
        store.setOpenOut(openOut);
        store.setThumbnailImage(thumbnailImage);
        return store;
    }

    @Override
    public Store createStore(String name, String link, boolean affiliated, String description,
                             String placeHolder, String surferPlaceHolder, String affiliateId) {
        StoreModel store = new StoreModel();
        store.setName(name);
        store.setLink(link);
        store.setAffiliated(affiliated);
        store.setDescription(description);
        store.setPlaceHolder(placeHolder);
        store.setAffiliateId(affiliateId);
        store.setSurferPlaceHolder(surferPlaceHolder);
        return store;
    }
}