package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoCategoryDao;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.model.CategoryModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

@Repository
public class MongoCategoryPersistenceLayer implements CategoryPersistenceLayer {

    @Autowired
    private MongoCategoryDao<CategoryModel> categoryDao;

    @Override
    public AbstractCategory createCategory(AbstractCategory category) {
        Assert.notNull(category);
        Assert.isTrue(!Strings.isNullOrEmpty(category.getName())," Category's name can't be null");
        Assert.isTrue(!Strings.isNullOrEmpty(category.getDescription())," Category's description can't be null");
        CategoryModel model = new CategoryModel();
        BeanUtils.copyProperties(category, model);
        return categoryDao.create(model);
    }

    @Override
    public List<AbstractCategory> getAllCategories(int page, int itemCount) {
        List<AbstractCategory> categories = new ArrayList<>();
        List<CategoryModel> categoryModels = categoryDao.getAll(page, itemCount,CategoryModel.class);
        for(CategoryModel category : categoryModels) {
            categories.add(category);
        }
        return categories;
    }

    @Override
    public AbstractCategory getCategory(String categoryId) {
        Assert.isTrue(!Strings.isNullOrEmpty(categoryId)," CategoryId can't be null");
        return categoryDao.getById(categoryId, CategoryModel.class);
    }

    @Override
    public AbstractCategory getCategoryParent(String childCategoryId) {
        AbstractCategory category = getCategory(childCategoryId);
        Assert.notNull(category, "Can't find category with id:" + childCategoryId);
        return category.getParent();
    }

    @Override
    public List<? extends AbstractCategory> getAllChildren(String parentCategoryId) {
        CategoryModel category = (CategoryModel) getCategory(parentCategoryId);
        Assert.notNull(category, "Can't find category with id:" + parentCategoryId);
        return category.getChildren();
    }

    @Override
    public AbstractCategory updateCategory(AbstractCategory updatedCategory, String categoryId) {
        Assert.isTrue(updatedCategory instanceof CategoryModel, "Invalid category object");
        CategoryModel oldCategory = (CategoryModel) getCategory(categoryId);
        Assert.notNull(oldCategory, "Can't find category with id:" + categoryId);
        BeanUtils.copyProperties(updatedCategory, oldCategory);
        return categoryDao.update(oldCategory);
    }

    @Override
    public void deleteCategory(String categoryId) {
        AbstractCategory category = getCategory(categoryId);
        Assert.notNull(category, "Can't find category with id:" + categoryId);
        categoryDao.remove((CategoryModel) category);
    }
}