package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public class StoreModel implements Store {

    @Id
    private String storeId;
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
    private List<CategoryModel> categories;

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isOpenOut() {
        return openOut;
    }

    public void setOpenOut(boolean openOut) {
        this.openOut = openOut;
    }

    public boolean isAffiliated() {
        return affiliated;
    }

    public void setAffiliated(boolean affiliated) {
        this.affiliated = affiliated;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getSurferPlaceHolder() {
        return surferPlaceHolder;
    }

    public void setSurferPlaceHolder(String surferPlaceHolder) {
        this.surferPlaceHolder = surferPlaceHolder;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getFixedUrls() {
        return fixedUrls;
    }

    public void setFixedUrls(String fixedUrls) {
        this.fixedUrls = fixedUrls;
    }

    public String getPidRegex() {
        return pidRegex;
    }

    public void setPidRegex(String pidRegex) {
        this.pidRegex = pidRegex;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getMaxCashback() {
        return maxCashback;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setMaxCashback(String maxCashback) {
        this.maxCashback = maxCashback;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Override
    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    @Override
    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    @Override
    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    @Override
    public String getTagLine() {
        return tagLine;
    }

    @Override
    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<? extends CategoryModel> categories) {
        this.categories = (List<CategoryModel>) categories;
    }
}