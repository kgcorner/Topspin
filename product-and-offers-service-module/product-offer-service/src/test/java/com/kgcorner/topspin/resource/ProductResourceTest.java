package com.kgcorner.topspin.resource;

import com.kgcorner.topspin.dtos.ProductDTO;
import com.kgcorner.topspin.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/08/21
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductResource.class)
public class ProductResourceTest {

    private ProductService productService;
    private ProductResource productResource;
    private ProductResource spy;


    @Before
    public void setup() {
        this.productResource = new ProductResource();
        spy = PowerMockito.spy(this.productResource);
        this.productService = mock(ProductService.class);
        Whitebox.setInternalState(this.spy, "productService", this.productService);
    }

    @Test
    public void createProduct() throws Exception {
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
        String productUrl = "url";
        ProductDTO productDTO = mock(ProductDTO.class);
        when(productService.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl, productLargeImageUrl,
            categoryId, storeId, brand, productUrl)).thenReturn(productDTO);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getProductDTOResponseEntity", productDTO,
            HttpStatus.CREATED);
        ResponseEntity<ProductDTO> responseEntity = spy.createProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl, productLargeImageUrl,
            categoryId, storeId, brand, productUrl);
        assertEquals(responseEntity, mockedResponseEntity);
    }

    @Test
    public void getProduct() throws Exception {
        String productId = "id";
        ProductDTO productDTO = mock(ProductDTO.class);
        when(productService.getProduct(productId)).thenReturn(productDTO);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getProductDTOResponseEntity", productDTO, HttpStatus.OK);
        ResponseEntity<ProductDTO> responseEntity = spy.getProduct(productId);
        assertEquals(responseEntity, mockedResponseEntity);
    }

    @Test
    public void updateProduct() throws Exception {
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
        String productUrl = "url";
        String productId = "id";
        ProductDTO productDTO = mock(ProductDTO.class);
        when(productService.updateProduct(productId, productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl, productLargeImageUrl,
            categoryId, storeId, brand, productUrl)).thenReturn(productDTO);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getProductDTOResponseEntity", productDTO, HttpStatus.OK);
        ResponseEntity<ProductDTO> responseEntity = spy.updateProduct(productName, productDescription, productPrice,
            discountedPrice, productPriceCurrency, productSmallImageUrl, productMediumImageUrl, productLargeImageUrl,
            categoryId, storeId, brand, productUrl, productId);
        assertEquals(responseEntity, mockedResponseEntity);
    }

    @Test
    public void deleteProduct() {
        String productId = "id";
        doNothing().when(productService).deleteProduct(productId);
        ResponseEntity<Object> responseEntity = spy.deleteProduct(productId);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}