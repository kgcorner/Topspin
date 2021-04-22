package com.kgcorner.topspin.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/04/21
 */

public abstract class AbstractCategory implements Category {

    private String name;
    private String description;
    private String longDescription;
    private String bannerImage;
    private String thumbnailImage;
    private String largeImage;
    private Category parent;
    private String tagline;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLongDescription() {
        return longDescription;
    }

    @Override
    public String getBannerImage() {
        return bannerImage;
    }

    @Override
    public String getThumbNailImage() {
        return thumbnailImage;
    }

    @Override
    public String getLargeImage() {
        return largeImage;
    }

    @Override
    public Category getParent() {
        return parent;
    }

    @Override
    public String getTagLine() {
        return tagline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}