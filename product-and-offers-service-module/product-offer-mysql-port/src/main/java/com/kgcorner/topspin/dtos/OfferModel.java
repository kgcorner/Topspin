package com.kgcorner.topspin.dtos;


import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */
@Entity
@Table(name="OFFERS")
public class OfferModel extends AbstractOffer {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name ="THUMBNAILS")
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


    @Override
    public List<String> getThumbnails() {
        return Arrays.asList(thumbnails.split(","));
    }

    @Override
    public void setThumbnails(List<String> thumbnails) {
        StringBuilder sb = new StringBuilder();
        for(String t : thumbnails) {
            sb.append(t+",");
        }
        int len = sb.length();
        if(len > 1)
            this.thumbnails = sb.substring(0, len -1);
        else
            this.thumbnails = "";
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
    @JoinColumn(name ="CATEGORY", nullable = false)
    @Override
    public Category getCategory() {
        return super.getCategory();
    }

    @Override
    public void setCategory(Category category) {
        super.setCategory(category);
    }

    @ManyToOne
    @JoinColumn(name ="STORE", nullable = false)
    @Override
    public Store getStore() {
        return super.getStore();
    }

    @Override
    public void setStore(Store store) {
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

    @Override
    public void setFeatured(boolean featured) {
        super.setFeatured(featured);
    }

    @Override
    public String getOfferId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}