package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class StoreModelTest {
    private StoreModel storeModel;

    @Before
    public void setUp() throws Exception {
        storeModel = new StoreModel();
    }

    @Test
    public void setStoreId() {
        String storeId = "storeID";
        storeModel.setStoreId(storeId);
        Assert.assertEquals(storeId, storeModel.getStoreId());
    }

    @Test
    public void setName() {
        String name = "Amazon";
        storeModel.setName(name);
        Assert.assertEquals(name, storeModel.getName());
    }

    @Test
    public void setLink() {
        String link = "link";
        storeModel.setLink(link);
        Assert.assertEquals(link, storeModel.getLink());
    }

    @Test
    public void setLogo() {
        String logo = "logo";
        storeModel.setLogo(logo);
        Assert.assertEquals(logo, storeModel.getLogo());
    }

    @Test
    public void setOpenOut() {
        boolean openOut = true;
        storeModel.setOpenOut(openOut);
        Assert.assertTrue(storeModel.isOpenOut());
    }

    @Test
    public void setAffiliated() {
        boolean affiliated =true;
        storeModel.setAffiliated(affiliated);
        Assert.assertTrue(storeModel.isAffiliated());
    }

    @Test
    public void setPlaceHolder() {
        String placeHolder = "placeHolder";
        storeModel.setPlaceHolder(placeHolder);
        Assert.assertEquals(placeHolder, storeModel.getPlaceHolder());
    }

    @Test
    public void setSurferPlaceHolder() {
        String SurferPlaceHolder = "SurferPlaceHolder";
        storeModel.setSurferPlaceHolder(SurferPlaceHolder);
        Assert.assertEquals(SurferPlaceHolder, storeModel.getSurferPlaceHolder());
    }

    @Test
    public void setSearchUrl() {
        String searchUrl = "searchUrl";
        storeModel.setSearchUrl(searchUrl);
        Assert.assertEquals(searchUrl, storeModel.getSearchUrl());
    }

    @Test
    public void setFixedUrls() {
        String fixedUrl = "fixedUrl";
        storeModel.setFixedUrls(fixedUrl);
        Assert.assertEquals(fixedUrl, storeModel.getFixedUrls());
    }

    @Test
    public void setPidRegex() {
        String regex = "regex";
        storeModel.setPidRegex(regex);
        Assert.assertEquals(regex, storeModel.getPidRegex());
    }

    @Test
    public void setGender() {
        String gender = "Male";
        storeModel.setGender(gender);
        Assert.assertEquals(gender, storeModel.getGender());
    }

    @Test
    public void setAffiliateId() {
        String affiliateId = "affiliateId";
        storeModel.setAffiliateId(affiliateId);
        Assert.assertEquals(affiliateId, storeModel.getAffiliateId());
    }

    @Test
    public void setMaxCashback() {
        String cashback = "50%";
        storeModel.setMaxCashback(cashback);
        Assert.assertEquals(cashback, storeModel.getMaxCashback());
    }

    @Test
    public void setDescription() {
        String description = "description";
        storeModel.setDescription(description);
        Assert.assertEquals(description, storeModel.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        storeModel.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, storeModel.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        storeModel.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, storeModel.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbNailImage = "thumbNailImage";
        storeModel.setThumbnailImage(thumbNailImage);
        storeModel.setThumbnailImage(thumbNailImage);
        Assert.assertEquals(thumbNailImage, storeModel.getThumbnailImage());
    }

    @Test
    public void setLargeImage() {
        String largeImage = "largeImage";
        storeModel.setLargeImage(largeImage);
        Assert.assertEquals(largeImage, storeModel.getLargeImage());
    }

    @Test
    public void setTagLine() {
        String tagLine = "tagLine";
        storeModel.setTagLine(tagLine);
        Assert.assertEquals(tagLine, storeModel.getTagLine());
    }

    @Test
    public void setActive() {
        boolean active = true;
        storeModel.setActive(active);
        Assert.assertTrue(storeModel.isActive());
    }

    @Test
    public void setCategories() {
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categoryModels.add(new CategoryModel());
        }
        storeModel.setCategories(categoryModels);
        Assert.assertEquals(categoryModels, storeModel.getCategories());
    }
}