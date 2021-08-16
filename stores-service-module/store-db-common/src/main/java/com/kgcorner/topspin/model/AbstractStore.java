package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStore<T extends AbstractCategory>{
    private String name;
    private String link;
    private String logo;
    private boolean openOut;
    private boolean affiliated;
    private String placeHolder;
    private String surferPlaceHolder;
    private String searchUrl;
    private String fixedUrls;
    private String pidRegex;
    private String gender;
    private String affiliateId;
    private String maxCashback;
    private String description;
    private String longDescription;
    private String bannerImage;
    private String thumbnailImage;
    private String largeImage;
    private String tagLine;
    private boolean active;
    private String storeId;
    private List<T> categories;
}