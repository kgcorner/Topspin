package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.ProductModel;
import com.kgcorner.topspin.dtos.Store;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */
@Component
public class ProductModelFactory implements ProductFactory {
    @Override
    public Product createProduct(String title, String description, double price, double discountedPrice,
                                 String currency, String smallImageUrl, String mediumImageUrl,
                                 String largeImageUrl, Category category, Store store, String brand) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(generateProductId());
        productModel.setCategory(category);
        productModel.setBrand(brand);
        productModel.setDiscountedPrice(discountedPrice);
        productModel.setProductDescription(description);
        productModel.setProductName(title);
        productModel.setProductPrice(price);
        productModel.setProductPriceCurrency(currency);
        productModel.setProductSmallImageUrl(smallImageUrl);
        productModel.setProductMediumImageUrl(mediumImageUrl);
        productModel.setProductLargeImageUrl(largeImageUrl);
        productModel.setStore(store);
        return productModel;
    }

    private String generateProductId() {
        return UUID.randomUUID().toString();
    }
}