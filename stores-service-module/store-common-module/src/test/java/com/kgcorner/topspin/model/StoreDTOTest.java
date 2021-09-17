package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class StoreDTOTest {

    private StoreDTO store;

    @Before
    public void setUp() {
        store = new StoreDTO();
    }



    @Test
    public void setCategories() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTOS.add(categoryDTO);

        }
        store.setCategories(categoryDTOS);

        Assert.assertEquals(categoryDTOS, store.getCategories());
    }

    @Test
    public void setStoreId() {
        String storeId = "storeID";
        store.setStoreId(storeId);
        Assert.assertEquals(storeId, store.getStoreId());
    }

    @Test
    public void setName() {
        String name = "Amazon";
        store.setName(name);
        Assert.assertEquals(name, store.getName());
    }

    @Test
    public void setLink() {
        String link = "link";
        store.setLink(link);
        Assert.assertEquals(link, store.getLink());
    }

    @Test
    public void setLogo() {
        String logo = "logo";
        store.setLogo(logo);
        Assert.assertEquals(logo, store.getLogo());
    }

    @Test
    public void setOpenOut() {
        boolean openOut = true;
        store.setOpenOut(openOut);
        Assert.assertTrue(store.isOpenOut());
    }

    @Test
    public void setAffiliated() {
        boolean affiliated =true;
        store.setAffiliated(affiliated);
        Assert.assertTrue(store.isAffiliated());
    }

    @Test
    public void setPlaceHolder() {
        String placeHolder = "placeHolder";
        store.setPlaceHolder(placeHolder);
        Assert.assertEquals(placeHolder, store.getPlaceHolder());
    }

    @Test
    public void setSurferPlaceHolder() {
        String SurferPlaceHolder = "SurferPlaceHolder";
        store.setSurferPlaceHolder(SurferPlaceHolder);
        Assert.assertEquals(SurferPlaceHolder, store.getSurferPlaceHolder());
    }

    @Test
    public void setSearchUrl() {
        String searchUrl = "searchUrl";
        store.setSearchUrl(searchUrl);
        Assert.assertEquals(searchUrl, store.getSearchUrl());
    }

    @Test
    public void setFixedUrls() {
        String fixedUrl = "fixedUrl";
        store.setFixedUrls(fixedUrl);
        Assert.assertEquals(fixedUrl, store.getFixedUrls());
    }

    @Test
    public void setPidRegex() {
        String regex = "regex";
        store.setPidRegex(regex);
        Assert.assertEquals(regex, store.getPidRegex());
    }

    @Test
    public void setGender() {
        String gender = "Male";
        store.setGender(gender);
        Assert.assertEquals(gender, store.getGender());
    }

    @Test
    public void setAffiliateId() {
        String affiliateId = "affiliateId";
        store.setAffiliateId(affiliateId);
        Assert.assertEquals(affiliateId, store.getAffiliateId());
    }

    @Test
    public void setMaxCashback() {
        String cashback = "50%";
        store.setMaxCashback(cashback);
        Assert.assertEquals(cashback, store.getMaxCashback());
    }

    @Test
    public void setDescription() {
        String description = "description";
        store.setDescription(description);
        Assert.assertEquals(description, store.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        store.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, store.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        store.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, store.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbNailImage = "thumbNailImage";
        store.setThumbnailImage(thumbNailImage);
        store.setThumbnailImage(thumbNailImage);
        Assert.assertEquals(thumbNailImage, store.getThumbnailImage());
    }

    @Test
    public void setLargeImage() {
        String largeImage = "largeImage";
        store.setLargeImage(largeImage);
        Assert.assertEquals(largeImage, store.getLargeImage());
    }

    @Test
    public void setTagLine() {
        String tagLine = "tagLine";
        store.setTagLine(tagLine);
        Assert.assertEquals(tagLine, store.getTagLine());
    }

    @Test
    public void setActive() {
        boolean active = true;
        store.setActive(active);
        Assert.assertTrue(store.isActive());
    }

    @Test
    public void getLinks() {
        String link = "link";
        String rel = "self";
        store.addLink(link, rel);
        List<Link> links = store.getLinks();
        assertEquals(link, links.get(0).getHref());
        assertEquals(rel, links.get(0).getRel());
    }

}