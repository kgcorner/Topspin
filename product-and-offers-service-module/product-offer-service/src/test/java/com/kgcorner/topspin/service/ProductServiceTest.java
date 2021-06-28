package com.kgcorner.topspin.service;

import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.*;
import com.kgcorner.topspin.dtos.factory.CategoryFactory;
import com.kgcorner.topspin.dtos.factory.ProductFactory;
import com.kgcorner.topspin.dtos.factory.StoreFactory;
import com.kgcorner.topspin.model.CategoryResponse;
import com.kgcorner.topspin.model.StoreResponse;
import com.kgcorner.topspin.persistence.ProductPersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class ProductServiceTest {

    private ProductService productService;
    private ProductFactory productFactory;
    private CategoryFactory categoryFactory;
    private StoreFactory storeFactory;
    private StoreClient storeClient;
    private CategoryClient categoryClient;
    private ProductPersistenceLayer productPersistenceLayer;


    @Before
    public void setUp() throws Exception {
        productService = new ProductService();
        productFactory = mock(ProductFactory.class);
        categoryFactory = mock(CategoryFactory.class);
        storeFactory = mock(StoreFactory.class);
        storeClient = mock(StoreClient.class);
        categoryClient = mock(CategoryClient.class);
        productPersistenceLayer = mock(ProductPersistenceLayer.class);
        Whitebox.setInternalState(productService, "productFactory", productFactory);
        Whitebox.setInternalState(productService, "categoryFactory", categoryFactory);
        Whitebox.setInternalState(productService, "storeFactory", storeFactory);
        Whitebox.setInternalState(productService, "storeClient", storeClient);
        Whitebox.setInternalState(productService, "categoryClient", categoryClient);
        Whitebox.setInternalState(productService, "productPersistenceLayer", productPersistenceLayer);
    }

    @Test
    public void getProduct() {
        String productId = "productId";
        Product product = mock(AbstractProduct.class);
        when(product.getProductId()).thenReturn(productId);
        when(productPersistenceLayer.getProduct(productId)).thenReturn(product);
        ProductDTO productDTO = productService.getProduct(productId);
        assertEquals(productId, productDTO.getProductId());
    }

    @Test
    public void createProduct() {
        String productName = "name";

        String productDescription = "description";

        double productPrice = 15.0;

        String productPriceCurrency = "INR";

        double discountedPrice = 5.5;
        String productSmallImageUrl = "smallUrl";

        String productMediumImageUrl = "mediumUrl";

        String productLargeImageUrl = "largeUrl";

        String brand = "Brand";

        String categoryId = "categoryId";

        String storeId = "storeId";

        Category category = PowerMockito.mock(Category.class);

        Store store = PowerMockito.mock(Store.class);
        String id = "productId";

        String surferPlaceholderUrl = "surferPlaceholderUrl%s";
        String url = "url";
        CategoryResponse categoryResponse = mock(CategoryResponse.class);
        StoreResponse storeResponse = mock(StoreResponse.class);
        Product product = mock(AbstractProduct.class);
        when(storeResponse.getStoreId()).thenReturn(storeId);
        when(storeResponse.getName()).thenReturn(productName);
        when(storeResponse.getDescription()).thenReturn(productDescription);
        when(storeResponse.getSurferPlaceHolder()).thenReturn(surferPlaceholderUrl);

        when(categoryResponse.getCategoryId()).thenReturn(categoryId);
        when(categoryResponse.getName()).thenReturn(productName);
        when(categoryResponse.getDescription()).thenReturn(productDescription);

        when(categoryClient.getCategory(categoryId)).thenReturn(categoryResponse);
        when(storeClient.get(storeId)).thenReturn(storeResponse);
        when(storeFactory.createStore(storeId, productName, productDescription)).thenReturn(store);
        when(categoryFactory.createCategory(categoryId, productName, productDescription)).thenReturn(category);
        when(productFactory.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl
            ,productLargeImageUrl, category, store, brand, String.format(surferPlaceholderUrl, url))
            ).thenReturn(product);
        when(productPersistenceLayer.createProduct(product)).thenReturn(product);
        when(product.getProductId()).thenReturn(id);
        ProductDTO productDTO = productService.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl
            ,productLargeImageUrl, categoryId, storeId, brand, url);
        assertNotNull(productDTO);
        assertEquals(id, productDTO.getProductId());
    }


    @Test(expected = IllegalArgumentException.class)
    public void createProductWithNoStore() {
        String productName = "name";

        String productDescription = "description";

        double productPrice = 15.0;

        String productPriceCurrency = "INR";

        double discountedPrice = 5.5;
        String productSmallImageUrl = "smallUrl";

        String productMediumImageUrl = "mediumUrl";

        String productLargeImageUrl = "largeUrl";

        String brand = "Brand";

        String categoryId = "categoryId";

        String storeId = "storeId";
        String url = "url";
        CategoryResponse categoryResponse = mock(CategoryResponse.class);


        when(categoryClient.getCategory(categoryId)).thenReturn(categoryResponse);
        when(storeClient.get(storeId)).thenThrow(RuntimeException.class);
        productService.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl
            ,productLargeImageUrl, categoryId, storeId, brand, url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProductWithNoCategory() {
        String productName = "name";

        String productDescription = "description";

        double productPrice = 15.0;

        String productPriceCurrency = "INR";

        double discountedPrice = 5.5;
        String productSmallImageUrl = "smallUrl";

        String productMediumImageUrl = "mediumUrl";

        String productLargeImageUrl = "largeUrl";

        String brand = "Brand";

        String categoryId = "categoryId";

        String storeId = "storeId";
        String url = "url";


        when(categoryClient.getCategory(categoryId)).thenThrow(RuntimeException.class);
        productService.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl
            ,productLargeImageUrl, categoryId, storeId, brand, url);
    }
}