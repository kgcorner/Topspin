package com.kgcorner.topspin.persistence;


import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlProductDao;
import com.kgcorner.topspin.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Repository
public class MysqlProductPersistenceLayer implements ProductPersistenceLayer {

    @Autowired
    private MysqlProductDao<ProductModel> productDao;

    @Override
    public Product getProduct(String productId) {
        return productDao.get(productId, ProductModel.class);
    }

    @Override
    public Product createProduct(Product product) {
        return productDao.create((ProductModel) product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDao.update((ProductModel) product);
    }

    @Override
    public List<Product> getAll(int page, int itemPerPage) {
        List<ProductModel> models = productDao.getAll(page, itemPerPage, ProductModel.class);
        return createProductList(models);
    }

    @Override
    public List<Product> getAllFromCategory(Category category, int page, int itemPerPage) {
        if(!(category instanceof CategoryReferenceModel)) {
            throw new IllegalArgumentException("Unexpected Category type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        List<ProductModel> models = productDao.getAll(operations, page, itemPerPage,
            null, ProductModel.class);
        return createProductList(models);
    }

    @Override
    public List<Product> getAllFromStore(Store store, int page, int itemPerPage) {
        if(!(store instanceof StoreReferenceModel)) {
            throw new IllegalArgumentException("Unexpected store type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        List<ProductModel> models = productDao.getAll(operations, page, itemPerPage,
            Collections.emptyList(), ProductModel.class);
        return createProductList(models);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = getProduct(productId);
        if(product == null)
            throw new IllegalArgumentException("No such product exists");
        productDao.remove((ProductModel) product);
    }

    private List<Product> createProductList(List<ProductModel> models) {
        List<Product> products = new ArrayList<>();
        for(Product p : models) {
            products.add(p);
        }
        return products;
    }
}