package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.Store;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductFactory {
    Product createProduct(String title, String description, double price, double discountedPrice,
                          String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                          Category category, Store store, String brand, String url);
}