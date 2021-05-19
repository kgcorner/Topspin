package com.kgcorner.topspin.dtos;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class ProductDTOTest {

    @Test
    public void testProductDTO() {
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

        Category category = PowerMockito.mock(Category.class);

        String categoryPathAsString = " cat1";

        Store store = PowerMockito.mock(Store.class);

        int viewCount = 500;

        String id = "productId";
        String storeName = "storeName";
        DemoProductDTO demoProductDTO = new DemoProductDTO();
        demoProductDTO.setId(id);
        demoProductDTO.setBrand(brand);
        demoProductDTO.setCategory(category);
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
        demoProductDTO.setStore(store);
        demoProductDTO.setWasPrice(wasPrice);
        demoProductDTO.setId(id);
        ProductDTO productDTO = new ProductDTO(demoProductDTO);
        assertEquals(productName, productDTO.getProductName());

        assertEquals(productDescription, productDTO.getProductDescription());

        assertEquals(productPrice, productDTO.getProductPrice(),0.0);

        assertEquals(productPriceCurrency, productDTO.getProductPriceCurrency());

        assertEquals( wasPrice, productDTO.getWasPrice(),0.0);

        assertEquals( discountedPrice, productDTO.getDiscountedPrice(),0.0);

        assertEquals(productUrl, productDTO.getProductUrl());

        assertEquals(pid, productDTO.getPid());

        assertEquals(mid, productDTO.getMid());

        assertEquals(productSmallImageUrl, productDTO.getProductSmallImageUrl());

        assertEquals(productMediumImageUrl, productDTO.getProductMediumImageUrl());

        assertEquals(productLargeImageUrl, productDTO.getProductLargeImageUrl());

        assertEquals(mpn, productDTO.getMpn());

        assertEquals(stockAvailability, productDTO.getStockAvailability());

        assertEquals(brand, productDTO.getBrand());

        assertEquals(location, productDTO.getLocation());

        assertEquals(color, productDTO.getColor());

        assertEquals(custom1, productDTO.getCustom1());

        assertEquals(custom2, productDTO.getCustom2());

        assertEquals(custom3, productDTO.getCustom3());

        assertEquals(custom4, productDTO.getCustom4());

        assertEquals(custom5, productDTO.getCustom5());

        assertEquals(category, productDTO.getCategory());

        assertEquals(categoryPathAsString, productDTO.getCategoryPathAsString());

        assertEquals(store, productDTO.getStore());

        assertEquals(viewCount, productDTO.getViewCount());

        assertEquals(storeName, productDTO.getStoreName());
        assertEquals(id, productDTO.getProductId());
        assertEquals(productSku, productDTO.getProductSku());

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