package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/04/21
 */

public class AbstractStoreTest {

    private DemoStore storeDTO;
    @Before
    public void setup() {
        storeDTO = new DemoStore();
    }

    @Test
    public void setStoreId() {
        String storeId = "storeID";
        storeDTO.setStoreId(storeId);
        Assert.assertEquals(storeId, storeDTO.getStoreId());
    }

    @Test
    public void setName() {
        String name = "Amazon";
        storeDTO.setName(name);
        Assert.assertEquals(name, storeDTO.getName());
    }

    @Test
    public void setLink() {
        String link = "link";
        storeDTO.setLink(link);
        Assert.assertEquals(link, storeDTO.getLink());
    }

    @Test
    public void setLogo() {
        String logo = "logo";
        storeDTO.setLogo(logo);
        Assert.assertEquals(logo, storeDTO.getLogo());
    }

    @Test
    public void setOpenOut() {
        boolean openOut = true;
        storeDTO.setOpenOut(openOut);
        Assert.assertTrue(storeDTO.isOpenOut());
    }

    @Test
    public void setAffiliated() {
        boolean affiliated =true;
        storeDTO.setAffiliated(affiliated);
        Assert.assertTrue(storeDTO.isAffiliated());
    }

    @Test
    public void setPlaceHolder() {
        String placeHolder = "placeHolder";
        storeDTO.setPlaceHolder(placeHolder);
        Assert.assertEquals(placeHolder, storeDTO.getPlaceHolder());
    }

    @Test
    public void setSurferPlaceHolder() {
        String SurferPlaceHolder = "SurferPlaceHolder";
        storeDTO.setSurferPlaceHolder(SurferPlaceHolder);
        Assert.assertEquals(SurferPlaceHolder, storeDTO.getSurferPlaceHolder());
    }

    @Test
    public void setSearchUrl() {
        String searchUrl = "searchUrl";
        storeDTO.setSearchUrl(searchUrl);
        Assert.assertEquals(searchUrl, storeDTO.getSearchUrl());
    }

    @Test
    public void setFixedUrls() {
        String fixedUrl = "fixedUrl";
        storeDTO.setFixedUrls(fixedUrl);
        Assert.assertEquals(fixedUrl, storeDTO.getFixedUrls());
    }

    @Test
    public void setPidRegex() {
        String regex = "regex";
        storeDTO.setPidRegex(regex);
        Assert.assertEquals(regex, storeDTO.getPidRegex());
    }

    @Test
    public void setGender() {
        String gender = "Male";
        storeDTO.setGender(gender);
        Assert.assertEquals(gender, storeDTO.getGender());
    }

    @Test
    public void setAffiliateId() {
        String affiliateId = "affiliateId";
        storeDTO.setAffiliateId(affiliateId);
        Assert.assertEquals(affiliateId, storeDTO.getAffiliateId());
    }

    @Test
    public void setMaxCashback() {
        String cashback = "50%";
        storeDTO.setMaxCashback(cashback);
        Assert.assertEquals(cashback, storeDTO.getMaxCashback());
    }

    @Test
    public void setDescription() {
        String description = "description";
        storeDTO.setDescription(description);
        Assert.assertEquals(description, storeDTO.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        storeDTO.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, storeDTO.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        storeDTO.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, storeDTO.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbNailImage = "thumbNailImage";
        storeDTO.setThumbnailImage(thumbNailImage);
        storeDTO.setThumbnailImage(thumbNailImage);
        Assert.assertEquals(thumbNailImage, storeDTO.getThumbnailImage());
    }

    @Test
    public void setLargeImage() {
        String largeImage = "largeImage";
        storeDTO.setLargeImage(largeImage);
        Assert.assertEquals(largeImage, storeDTO.getLargeImage());
    }

    @Test
    public void setTagLine() {
        String tagLine = "tagLine";
        storeDTO.setTagLine(tagLine);
        Assert.assertEquals(tagLine, storeDTO.getTagLine());
    }

    @Test
    public void setActive() {
        boolean active = true;
        storeDTO.setActive(active);
        Assert.assertTrue(storeDTO.isActive());
    }

    class DemoStore extends AbstractStore {
        private String storeId;
        private List<Category> categories = new ArrayList<>();

        public void setStoreId(String id) {
            this.storeId = id;
        }

        @Override
        public String getStoreId() {
            return storeId;
        }

        @Override
        public List<Category> getCategories() {
            return categories;
        }
    }
}