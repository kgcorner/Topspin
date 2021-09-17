package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AbstractOffer {
    private String title;
    private String description;
    private String thumbnails;
    private String url;
    private String surferPlaceholderUrl;
    private CategoryRef category;
    private StoreRef store;
    private Date lastDate;
    private String maxDiscount;
    private boolean featured;
    private String offerId;
}