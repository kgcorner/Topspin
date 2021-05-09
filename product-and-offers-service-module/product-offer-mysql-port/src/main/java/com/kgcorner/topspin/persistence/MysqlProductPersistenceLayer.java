package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlProductDao;
import com.kgcorner.topspin.dtos.Product;
import com.kgcorner.topspin.dtos.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
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
        List<Product> products = new ArrayList<>();
        List<ProductModel> models = productDao.getAll(page, itemPerPage, ProductModel.class);
        for(Product p : models) {
            products.add(p);
        }
        return products;
    }

    @Override
    public List<Product> getAllFromCategory(String categoryId, int page, int itemPerPage) {
        String hql = "from product where category=:category";
        Query query = this.productDao.getEntityManager().createQuery(hql);
        query.setParameter("category", categoryId);
        int first = page * itemPerPage;
        query.setFirstResult(first).setMaxResults(itemPerPage);
        return query.getResultList();
    }

    @Override
    public List<Product> getAllFromStore(String storeId, int page, int itemPerPage) {
        String hql = "from product where store=:store";
        Query query = this.productDao.getEntityManager().createQuery(hql);
        query.setParameter("store", storeId);
        int first = page * itemPerPage;
        query.setFirstResult(first).setMaxResults(itemPerPage);
        return query.getResultList();
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = getProduct(productId);
        if(product == null)
            throw new IllegalArgumentException("No such product exists");
        productDao.remove((ProductModel) product);
    }
}