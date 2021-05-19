package com.kgcorner.topspin.service;


import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.ProductDTO;
import com.kgcorner.topspin.dtos.factory.CategoryFactory;
import com.kgcorner.topspin.dtos.factory.ProductFactory;
import com.kgcorner.topspin.dtos.factory.StoreFactory;
import com.kgcorner.topspin.model.CategoryResponse;
import com.kgcorner.topspin.model.StoreResponse;
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

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private CategoryFactory categoryFactory;

    @Autowired
    private StoreFactory storeFactory;

    public ProductDTO getProduct(String productId) {
        Product product = productPersistenceLayer.getProduct(productId);
        return new ProductDTO(product);
    }

    public ProductDTO createProduct(String title, String description, double price, double discountedPrice,
                                    String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                                    String categoryId, String storeId, String brand, String url) {
        CategoryResponse categoryDTO;
        StoreResponse storeDTO;
        try {
            categoryDTO = categoryClient.getCategory(categoryId);
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find category with id " + categoryId, x);
        }
        try {
            storeDTO = storeClient.get(storeId);
        } catch (Exception x) {
            throw new IllegalArgumentException("Failed to find store with id " + storeId, x);
        }
        var category = categoryFactory.createCategory(categoryDTO.getCategoryId(),
            categoryDTO.getName(), categoryDTO.getDescription());
        var store = storeFactory.createStore(storeDTO.getStoreId(), storeDTO.getName(), storeDTO.getDescription());
        Product product = productFactory.createProduct(title, description, price, discountedPrice,
            currency, smallImageUrl, mediumImageUrl,
            largeImageUrl, category, store, brand, String.format(storeDTO.getSurferPlaceHolder(), url));
        product = productPersistenceLayer.createProduct(product);
        return new ProductDTO(product);
    }
}