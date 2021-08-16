package com.kgcorner.topspin.service;


import com.kgcorner.topspin.dtos.CategoryDTO;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.ProductDTO;
import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.dtos.factory.ProductFactory;
import com.kgcorner.topspin.persistence.ProductPersistenceLayer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Service
public class ProductService  extends AbstractProductOfferService {

    @Autowired
    private ProductPersistenceLayer productPersistenceLayer;

    @Autowired
    private ProductFactory productFactory;

    public ProductDTO createProduct(String productId) {
        Product product = productPersistenceLayer.getProduct(productId);
        return new ProductDTO(product);
    }

    public ProductDTO createProduct(String title, String description, double price, double discountedPrice,
                                    String currency, String smallImageUrl, String mediumImageUrl, String largeImageUrl,
                                    String categoryId, String storeId, String brand, String url) {
        CategoryDTO categoryDTO = getCategoryDTO(categoryId);
        StoreDTO storeDTO = getStoreDTO(storeId);
        Product product = createProduct(title, description, price, discountedPrice, currency, smallImageUrl,
            mediumImageUrl, largeImageUrl, brand, url, categoryDTO, storeDTO);
        product = productPersistenceLayer.createProduct(product);
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(String productId, String title, String description, double price,
                                    double discountedPrice, String currency, String smallImageUrl,
                                    String mediumImageUrl, String largeImageUrl, String categoryId,
                                    String storeId, String brand, String url) {
        Product existingProduct = productPersistenceLayer.getProduct(productId);
        CategoryDTO categoryDTO = getCategoryDTO(categoryId);
        StoreDTO storeDTO = getStoreDTO(storeId);
        Product updatedProduct = createProduct(title, description, price, discountedPrice, currency,
            smallImageUrl, mediumImageUrl, largeImageUrl, brand, url, categoryDTO, storeDTO);
        BeanUtils.copyProperties(updatedProduct, existingProduct, "productId");
        return new ProductDTO(productPersistenceLayer.updateProduct(existingProduct));
    }

    public void deleteProduct(String productId) {
        productPersistenceLayer.deleteProduct(productId);
    }



    private Product createProduct(String title, String description, double price, double discountedPrice,
                                  String currency, String smallImageUrl, String mediumImageUrl,
                                  String largeImageUrl, String brand, String url, CategoryDTO categoryDTO,
                                  StoreDTO storeDTO) {
        var category = getCategory(categoryDTO);
        var store = getStore(storeDTO);
        return productFactory.createProduct(title, description, price, discountedPrice,
            currency, smallImageUrl, mediumImageUrl,
            largeImageUrl, category, store, brand, String.format(storeDTO.getSurferPlaceHolder(), url));
    }
}