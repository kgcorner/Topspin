package com.kgcorner.topspin.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */
@Entity
@Table(name="OFFERS")
public class OfferModel extends AbstractOffer implements Serializable {
    private String id;

    private String thumbnails;

    @Column(name ="TITLE")
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Column(name ="DESCRIPTION")
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }


    @Column(name ="THUMBNAILS")
    public String getThumbnails() {
        return thumbnails;
    }

    @Column(name ="THUMBNAILS")
    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    @Column(name ="URL")
    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
    }

    @Column(name ="SURFER_PLACEHOLDER")
    @Override
    public String getSurferPlaceholderUrl() {
        return super.getSurferPlaceholderUrl();
    }

    @Override
    public void setSurferPlaceholderUrl(String surferPlaceholderUrl) {
        super.setSurferPlaceholderUrl(surferPlaceholderUrl);
    }

    @ManyToOne
    @JoinColumn(name ="CATEGORY_ID", nullable = false)
    @Override
    public CategoryReferenceModel getCategory() {
        return (CategoryReferenceModel) super.getCategory();
    }

    @Override
    public void setCategory(CategoryRef category) {
        super.setCategory(category);
    }

    @ManyToOne
    @JoinColumn(name ="STORE_ID", nullable = false)
    @Override
    public StoreReferenceModel getStore() {
        return (StoreReferenceModel) super.getStore();
    }

    @Override
    public void setStore(StoreRef store) {
        super.setStore(store);
    }

    @Column(name ="LAST_DATE", nullable = false)
    @Override
    public Date getLastDate() {
        return super.getLastDate();
    }

    @Override
    public void setLastDate(Date lastDate) {
        super.setLastDate(lastDate);
    }

    @Column(name ="MAX_DISCOUNT", nullable = false)
    @Override
    public String getMaxDiscount() {
        return super.getMaxDiscount();
    }

    @Override
    public void setMaxDiscount(String maxDiscount) {
        super.setMaxDiscount(maxDiscount);
    }

    @Column(name ="FEATURED", nullable = false)
    @Override
    public boolean isFeatured() {
        return super.isFeatured();
    }

    @Column(name ="BANNER", nullable = false)
    @Override
    public boolean isBanner() {
        return super.isBanner();
    }

    @Override
    public void setBanner(boolean banner) {
        super.setBanner(banner);
    }

    @Override
    public void setFeatured(boolean featured) {
        super.setFeatured(featured);
    }

    @Id
    @Column(name = "ID")
    @Override
    public String getOfferId() {
        return id;
    }

    public void setOfferId(String id) {
        this.id = id;
    }
}