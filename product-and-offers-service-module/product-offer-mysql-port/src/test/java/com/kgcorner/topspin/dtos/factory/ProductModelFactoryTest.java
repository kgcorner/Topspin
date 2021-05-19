package com.kgcorner.topspin.dtos.factory;

import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.Store;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class ProductModelFactoryTest {

    @Test
    public void testProductModelFactory() {

        String productName = "name";
        String productDescription = "description";
        double productPrice = 15.0;
        String productPriceCurrency = "INR";
        double discountedPrice = 5.5;
        String productSmallImageUrl = "smallUrl";
        String productMediumImageUrl = "mediumUrl";
        String productLargeImageUrl = "largeUrl";
        String brand = "Brand";
        Category category = PowerMockito.mock(Category.class);
        Store store = PowerMockito.mock(Store.class);
        Product product = new ProductModelFactory().createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl
        ,productLargeImageUrl, category, store, brand);

        assertEquals(productName, product.getProductName());

        assertEquals(productDescription, product.getProductDescription());

        assertEquals(productPrice, product.getProductPrice(),0.0);

        assertEquals(productPriceCurrency, product.getProductPriceCurrency());

        assertEquals( discountedPrice, product.getDiscountedPrice(),0.0);

        assertEquals(productSmallImageUrl, product.getProductSmallImageUrl());

        assertEquals(productMediumImageUrl, product.getProductMediumImageUrl());

        assertEquals(productLargeImageUrl, product.getProductLargeImageUrl());

        assertEquals(brand, product.getBrand());

        assertEquals(category, product.getCategory());

        assertEquals(store, product.getStore());
    }
}