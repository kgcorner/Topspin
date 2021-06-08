package com.kgcorner.topspin.dtos;


import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public abstract class AbstractOffer implements Offer {

    private String title;
    private String description;
    private String thumbnails;
    private String url;
    private String surferPlaceholderUrl;
    private Category category;
    private Store store;
    private Date lastDate;
    private String maxDiscount;
    private boolean featured;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getThumbnails() {
        return thumbnails;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getSurferPlaceholderUrl() {
        return surferPlaceholderUrl;
    }

    public void setSurferPlaceholderUrl(String surferPlaceholderUrl) {
        this.surferPlaceholderUrl = surferPlaceholderUrl;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(String maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}