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
    protected String title;
    protected String description;
    protected String thumbnails;
    protected String url;
    protected String surferPlaceholderUrl;
    protected CategoryRef category;
    protected StoreRef store;
    protected Date lastDate;
    protected String maxDiscount;
    protected boolean featured;
    protected boolean banner;
    protected String offerId;
    protected  Date createdOn;
}