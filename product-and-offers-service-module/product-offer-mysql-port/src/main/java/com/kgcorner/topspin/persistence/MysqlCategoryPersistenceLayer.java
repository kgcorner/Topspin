package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlCategoryDao;
import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.CategoryReferenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Repository
public class MysqlCategoryPersistenceLayer implements CategoryPersistenceLayer {

    @Autowired
    private MysqlCategoryDao<CategoryReferenceModel> categoryDao;


    @Override
    public Category createCategory(Category category) {
        return categoryDao.create((CategoryReferenceModel)category);
    }

    @Override
    public Category getCategory(String id) {
        return categoryDao.getById(id, CategoryReferenceModel.class);
    }
}