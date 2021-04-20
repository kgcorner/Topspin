package com.kgcorner.topspin.model.factory;

import com.kgcorner.topspin.model.Store;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class StoreFactoryImplTest {

    private StoreFactoryImpl storeFactory;

    @Before
    public void before() {
        storeFactory = new StoreFactoryImpl();
    }

    @Test
    public void createStore() {
        Assert.assertNotNull(storeFactory.createStore());
    }

    @Test
    public void testCreateStore() {
        Store store = storeFactory.createStore("name", "link", "logo", true, true,
        "placeHolder", "surferPlaceHolder", "searchUrl", "fixedUrls",
            "pidRegex", "gender", "affiliateId", "maxCashback",
            "description", "longDescription", "bannerImage",
            "thumbnailImage", "largeImage", "tagLine",  true);
        Assert.assertNotNull(store);
        Assert.assertEquals("name",store.getName());
        Assert.assertEquals("link",store.getLink());
        Assert.assertEquals("logo",store.getLogo());
        Assert.assertEquals("placeHolder",store.getPlaceHolder());
        Assert.assertEquals("surferPlaceHolder",store.getSurferPlaceHolder());
        Assert.assertEquals("searchUrl",store.getSearchUrl());
        Assert.assertEquals("fixedUrls",store.getFixedUrls());
        Assert.assertEquals("pidRegex",store.getPidRegex());
        Assert.assertEquals("gender",store.getGender());
        Assert.assertEquals("affiliateId",store.getAffiliateId());
        Assert.assertEquals("description",store.getDescription());
        Assert.assertEquals("longDescription",store.getLongDescription());
        Assert.assertEquals("bannerImage",store.getBannerImage());
        Assert.assertEquals("thumbnailImage",store.getThumbnailImage());
        Assert.assertTrue(store.isOpenOut());
        Assert.assertTrue(store.isActive());
        Assert.assertTrue(store.isAffiliated());
    }

    @Test
    public void testCreateStore1() {
        Store  store = storeFactory.createStore("name","link", true, "description",
            "placeHolder", "surferPlaceHolder", "affiliateId");
        Assert.assertNotNull(store);
        Assert.assertEquals("name",store.getName());
        Assert.assertEquals("link",store.getLink());
        Assert.assertEquals("placeHolder",store.getPlaceHolder());
        Assert.assertEquals("surferPlaceHolder",store.getSurferPlaceHolder());
        Assert.assertEquals("affiliateId",store.getAffiliateId());
        Assert.assertEquals("description",store.getDescription());
        Assert.assertTrue(store.isAffiliated());
    }
}