package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.AbstractStore;
import com.kgcorner.topspin.model.Category;
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

public class StoreDTOTest {

    private StoreDTO storeDto;
    private DemoStore store;

    @Before
    public void setUp() {
        store = new DemoStore();
        storeDto = new StoreDTO(store);
    }



    @Test
    public void setCategories() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CategoryDTO categoryDTO = new CategoryDTO(null);
            categoryDTOS.add(categoryDTO);
            storeDto.addCategories(categoryDTO);
        }

        Assert.assertEquals(categoryDTOS, storeDto.getCategories());
    }

    @Test
    public void setStoreId() {
        String storeId = "storeID";
        store.setStoreId(storeId);
        Assert.assertEquals(storeId, storeDto.getStoreId());
    }

    @Test
    public void setName() {
        String name = "Amazon";
        store.setName(name);
        Assert.assertEquals(name, storeDto.getName());
    }

    @Test
    public void setLink() {
        String link = "link";
        store.setLink(link);
        Assert.assertEquals(link, storeDto.getLink());
    }

    @Test
    public void setLogo() {
        String logo = "logo";
        store.setLogo(logo);
        Assert.assertEquals(logo, storeDto.getLogo());
    }

    @Test
    public void setOpenOut() {
        boolean openOut = true;
        store.setOpenOut(openOut);
        Assert.assertTrue(storeDto.isOpenOut());
    }

    @Test
    public void setAffiliated() {
        boolean affiliated =true;
        store.setAffiliated(affiliated);
        Assert.assertTrue(storeDto.isAffiliated());
    }

    @Test
    public void setPlaceHolder() {
        String placeHolder = "placeHolder";
        store.setPlaceHolder(placeHolder);
        Assert.assertEquals(placeHolder, storeDto.getPlaceHolder());
    }

    @Test
    public void setSurferPlaceHolder() {
        String SurferPlaceHolder = "SurferPlaceHolder";
        store.setSurferPlaceHolder(SurferPlaceHolder);
        Assert.assertEquals(SurferPlaceHolder, storeDto.getSurferPlaceHolder());
    }

    @Test
    public void setSearchUrl() {
        String searchUrl = "searchUrl";
        store.setSearchUrl(searchUrl);
        Assert.assertEquals(searchUrl, storeDto.getSearchUrl());
    }

    @Test
    public void setFixedUrls() {
        String fixedUrl = "fixedUrl";
        store.setFixedUrls(fixedUrl);
        Assert.assertEquals(fixedUrl, storeDto.getFixedUrls());
    }

    @Test
    public void setPidRegex() {
        String regex = "regex";
        store.setPidRegex(regex);
        Assert.assertEquals(regex, storeDto.getPidRegex());
    }

    @Test
    public void setGender() {
        String gender = "Male";
        store.setGender(gender);
        Assert.assertEquals(gender, storeDto.getGender());
    }

    @Test
    public void setAffiliateId() {
        String affiliateId = "affiliateId";
        store.setAffiliateId(affiliateId);
        Assert.assertEquals(affiliateId, storeDto.getAffiliateId());
    }

    @Test
    public void setMaxCashback() {
        String cashback = "50%";
        store.setMaxCashback(cashback);
        Assert.assertEquals(cashback, storeDto.getMaxCashback());
    }

    @Test
    public void setDescription() {
        String description = "description";
        store.setDescription(description);
        Assert.assertEquals(description, storeDto.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        store.setLongDescription(longDescription);
        Assert.assertEquals(longDescription, storeDto.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        store.setBannerImage(bannerImage);
        Assert.assertEquals(bannerImage, storeDto.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbNailImage = "thumbNailImage";
        store.setThumbnailImage(thumbNailImage);
        store.setThumbnailImage(thumbNailImage);
        Assert.assertEquals(thumbNailImage, storeDto.getThumbnailImage());
    }

    @Test
    public void setLargeImage() {
        String largeImage = "largeImage";
        store.setLargeImage(largeImage);
        Assert.assertEquals(largeImage, storeDto.getLargeImage());
    }

    @Test
    public void setTagLine() {
        String tagLine = "tagLine";
        store.setTagLine(tagLine);
        Assert.assertEquals(tagLine, storeDto.getTagLine());
    }

    @Test
    public void setActive() {
        boolean active = true;
        store.setActive(active);
        Assert.assertTrue(storeDto.isActive());
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