package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.dtos.Category;
import com.kgcorner.topspin.dtos.CategoryReferenceModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 07/06/21
 */

@Transactional
@Repository
public class MysqlCategoryPersistenceLayer implements ProductOfferCategoryPersistenceLayer {

    @Autowired
    private MysqlDao<CategoryReferenceModel> categoryDao;

    @Override
    public Category createCategory(Category category) {
        Assert.notNull(category);
        return categoryDao.create((CategoryReferenceModel) category);
    }

    @Override
    public Category getCategory(String id) {
        if(Strings.isNullOrEmpty(id))
            throw new IllegalArgumentException("id can't be null");
        return categoryDao.get(id, CategoryReferenceModel.class);
    }
}