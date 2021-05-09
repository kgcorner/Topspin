package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.ProductDTO;
import com.kgcorner.topspin.dtos.factory.ProductFactory;
import com.kgcorner.topspin.persistence.ProductPersistenceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Service
public class ProductService {

    @Autowired
    private ProductPersistenceLayer productPersistenceLayer;

    @Autowired
    private ProductFactory productFactory;

    public ProductDTO getProduct(String productId) {
        Product product = productPersistenceLayer.getProduct(productId);
        return new ProductDTO(product);
    }

    public ProductDTO createProduct(String title, String description, double price, double discountedPrice,
                                    String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                                    String categoryId, String storeId, String brand) {
        Product product = productFactory.createProduct(title, description, price, discountedPrice,
            currency, smallImageUrl, mediumImageUrl,
            largeImageUrl, categoryId, storeId, brand);
        product = productPersistenceLayer.createProduct(product);
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(String productId, String title, String description, double price,
                                    double discountedPrice,
                                    String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                                    String categoryId, String storeId, String brand ) {
        Product product = productPersistenceLayer.getProduct(productId);
        //productFactory
        return null;
    }
}