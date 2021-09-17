package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.CategoryRef;
import com.kgcorner.topspin.model.CategoryReferenceModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
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
    public CategoryRef createCategory(CategoryRef category) {
        Assert.notNull(category);
        CategoryReferenceModel categoryReferenceModel = new CategoryReferenceModel();
        BeanUtils.copyProperties(category, categoryReferenceModel);
        return categoryDao.create(categoryReferenceModel);
    }

    @Override
    public CategoryRef getCategory(String id) {
        if(Strings.isNullOrEmpty(id))
            throw new IllegalArgumentException("id can't be null");
        return categoryDao.get(id, CategoryReferenceModel.class);
    }
}