package com.kgcorner.topspin.dtos;


import com.kgcorner.topspin.model.Store;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public class StoreDTO extends ResourceSupport implements Store {

    private List<CategoryDTO> categories;
    private final Store store;

    public StoreDTO(Store store) {
        this.store = store;
        this.categories = new ArrayList<>();
    }

    @Override
    public String getStoreId() {
        return store.getStoreId();
    }

    @Override
    public String getName() {
        return store.getName();
    }

    @Override
    public String getDescription() {
        return store.getDescription();
    }

    @Override
    public String getLongDescription() {
        return store.getLongDescription();
    }

    @Override
    public String getBannerImage() {
        return store.getBannerImage();
    }

    @Override
    public String getThumbnailImage() {
        return store.getThumbnailImage();
    }

    @Override
    public String getLargeImage() {
        return store.getLargeImage();
    }

    @Override
    public String getTagLine() {
        return store.getTagLine();
    }

    @Override
    public String getMaxCashback() {
        return store.getMaxCashback();
    }

    @Override
    public String getPlaceHolder() {
        return store.getPlaceHolder();
    }

    @Override
    public String getSurferPlaceHolder() {
        return store.getSurferPlaceHolder();
    }

    @Override
    public String getSearchUrl() {
        return store.getSearchUrl();
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public boolean isAffiliated() {
        return store.isAffiliated();
    }

    @Override
    public boolean isActive() {
        return store.isActive();
    }

    @Override
    public boolean isOpenOut() {
        return store.isOpenOut();
    }

    @Override
    public String getLink() {
        return store.getLink();
    }

    @Override
    public String getAffiliateId() {
        return store.getAffiliateId();
    }

    @Override
    public String getLogo() {
        return store.getLogo();
    }

    @Override
    public String getFixedUrls() {
        return store.getFixedUrls();
    }

    @Override
    public String getPidRegex() {
        return store.getPidRegex();
    }

    @Override
    public String getGender() {
        return store.getGender();
    }

    public void addCategories(CategoryDTO category) {
        this.categories.add(category);
    }
}