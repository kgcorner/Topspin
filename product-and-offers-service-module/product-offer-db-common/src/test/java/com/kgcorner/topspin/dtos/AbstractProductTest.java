package com.kgcorner.topspin.dtos;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class AbstractProductTest {

    @Test
    public void testAbstractProductTest() {
        String productSku = "sku";

        String productName = "name";

        String productDescription = "description";

        double productPrice = 15.0;

        String productPriceCurrency = "INR";

        double wasPrice = 10.0;

        double discountedPrice = 5.5;

        String productUrl = "url";

        String pid = "pid";

        String mid = "mid";

        String productSmallImageUrl = "smallUrl";

        String productMediumImageUrl = "mediumUrl";

        String productLargeImageUrl = "largeUrl";

        String mpn = "mpn";

        String stockAvailability = "available";

        String brand = "Brand";

        String location = "location";

        String color = "colors";

        String custom1 = "c1";

        String custom2 = "c2";

        String custom3 = "c3";

        String custom4 = "c4";

        String custom5 = "c5";

        String categoryId = "cid";

        String categoryPathAsString = " cat1";

        String storeId = "sid";

        int viewCount = 500;

        String id = "productId";
        String storeName = "storeName";
        DemoProductDTO demoProductDTO = new DemoProductDTO();
        demoProductDTO.setId(id);
        demoProductDTO.setBrand(brand);
        demoProductDTO.setCategoryId(categoryId);
        demoProductDTO.setColor(color);
        demoProductDTO.setCategoryPathAsString(categoryPathAsString);
        demoProductDTO.setCustom1(custom1);
        demoProductDTO.setCustom2(custom2);
        demoProductDTO.setCustom3(custom3);
        demoProductDTO.setCustom4(custom4);
        demoProductDTO.setCustom5(custom5);
        demoProductDTO.setDiscountedPrice(discountedPrice);
        demoProductDTO.setMid(mid);
        demoProductDTO.setLocation(location);
        demoProductDTO.setMpn(mpn);
        demoProductDTO.setPid(pid);
        demoProductDTO.setProductDescription(productDescription);
        demoProductDTO.setProductLargeImageUrl(productLargeImageUrl);
        demoProductDTO.setProductMediumImageUrl(productMediumImageUrl);
        demoProductDTO.setProductSmallImageUrl(productSmallImageUrl);
        demoProductDTO.setProductName(productName);
        demoProductDTO.setProductPrice(productPrice);
        demoProductDTO.setDiscountedPrice(discountedPrice);
        demoProductDTO.setProductPriceCurrency(productPriceCurrency);
        demoProductDTO.setProductSku(productSku);
        demoProductDTO.setProductUrl(productUrl);
        demoProductDTO.setStockAvailability(stockAvailability);
        demoProductDTO.setStoreName(storeName);
        demoProductDTO.setViewCount(viewCount);
        demoProductDTO.setStoreId(storeId);
        demoProductDTO.setWasPrice(wasPrice);
        demoProductDTO.setId(id);
        assertEquals(productName, demoProductDTO.getProductName());

        assertEquals(productDescription, demoProductDTO.getProductDescription());

        assertEquals(productPrice, demoProductDTO.getProductPrice(),0.0);

        assertEquals(productPriceCurrency, demoProductDTO.getProductPriceCurrency());

        assertEquals( wasPrice, demoProductDTO.getWasPrice(),0.0);

        assertEquals( discountedPrice, demoProductDTO.getDiscountedPrice(),0.0);

        assertEquals(productUrl, demoProductDTO.getProductUrl());

        assertEquals(pid, demoProductDTO.getPid());

        assertEquals(mid, demoProductDTO.getMid());

        assertEquals(productSmallImageUrl, demoProductDTO.getProductSmallImageUrl());

        assertEquals(productMediumImageUrl, demoProductDTO.getProductMediumImageUrl());

        assertEquals(productLargeImageUrl, demoProductDTO.getProductLargeImageUrl());

        assertEquals(mpn, demoProductDTO.getMpn());

        assertEquals(stockAvailability, demoProductDTO.getStockAvailability());

        assertEquals(brand, demoProductDTO.getBrand());

        assertEquals(location, demoProductDTO.getLocation());

        assertEquals(color, demoProductDTO.getColor());

        assertEquals(custom1, demoProductDTO.getCustom1());

        assertEquals(custom2, demoProductDTO.getCustom2());

        assertEquals(custom3, demoProductDTO.getCustom3());

        assertEquals(custom4, demoProductDTO.getCustom4());

        assertEquals(custom5, demoProductDTO.getCustom5());

        assertEquals(categoryId, demoProductDTO.getCategoryId());

        assertEquals(categoryPathAsString, demoProductDTO.getCategoryPathAsString());

        assertEquals(storeId, demoProductDTO.getStoreId());

        assertEquals(viewCount, demoProductDTO.getViewCount());

        assertEquals(storeName, demoProductDTO.getStoreName());
        assertEquals(id, demoProductDTO.getProductId());
        assertEquals(productSku, demoProductDTO.getProductSku());

    }



    class DemoProductDTO extends AbstractProduct {
        private String id;

        @Override
        public String getProductId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}