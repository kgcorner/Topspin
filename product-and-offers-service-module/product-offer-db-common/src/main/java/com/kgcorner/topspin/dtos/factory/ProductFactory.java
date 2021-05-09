package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Product;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductFactory {
    Product createProduct(String title, String description, double price, double discountedPrice,
                          String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                          String categoryId, String storeId, String brand);
}