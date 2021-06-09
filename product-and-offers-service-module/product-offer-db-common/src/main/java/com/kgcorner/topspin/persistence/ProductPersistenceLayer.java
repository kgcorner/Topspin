package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.Store;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface ProductPersistenceLayer {

    /**
     * Get product by Id
     * @param productId
     * @return
     */
    Product getProduct(String productId);

    /**
     * Create new Product
     * @param product
     * @return
     */
    Product createProduct(Product product);


    /**
     * Update existing product
     * @param product
     * @return
     */
    Product updateProduct(Product product);

    /**
     * Fetch all products pagewise
     * @param page
     * @param itemPerPage
     * @return
     */
    List<Product> getAll(int page, int itemPerPage);

    /**
     * Fetch product by category
     * @param category
     * @param page
     * @param itemPerPage
     * @return
     */
    List<Product> getAllFromCategory(Category category, int page, int itemPerPage);

    /**
     * Get all product from a given store
     * @param store
     * @param page
     * @param itemPerPage
     * @return
     */
    List<Product> getAllFromStore(Store store, int page, int itemPerPage);

    /**
     * Removes the product with given id
     * @param productId
     */
    void deleteProduct(String productId);
}