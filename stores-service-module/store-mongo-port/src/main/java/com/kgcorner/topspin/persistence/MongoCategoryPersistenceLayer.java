package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoCategoryDao;
import com.kgcorner.topspin.model.Category;
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
    public Category createCategory(Category category) {
        Assert.notNull(category);
        Assert.isTrue(category instanceof CategoryModel, "Invalid category object found");
        Assert.isTrue(!Strings.isNullOrEmpty(category.getName())," Category's name can't be null");
        Assert.isTrue(!Strings.isNullOrEmpty(category.getDescription())," Category's description can't be null");
        return categoryDao.create((CategoryModel) category);
    }

    @Override
    public List<Category> getAllCategories(int page, int itemCount) {
        List<Category> categories = new ArrayList<>();
        List<CategoryModel> categoryModels = categoryDao.getAll(page, itemCount,CategoryModel.class);
        for(Category category : categoryModels) {
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category getCategory(String categoryId) {
        Assert.isTrue(!Strings.isNullOrEmpty(categoryId)," CategoryId can't be null");
        return categoryDao.getById(categoryId, CategoryModel.class);
    }

    @Override
    public Category getCategoryParent(String childCategoryId) {
        Category category = getCategory(childCategoryId);
        Assert.notNull(category, "Can't find category with id:" + childCategoryId);
        return category.getParent();
    }

    @Override
    public List<Category> getAllChildren(String parentCategoryId) {
        CategoryModel category = (CategoryModel) getCategory(parentCategoryId);
        Assert.notNull(category, "Can't find category with id:" + parentCategoryId);
        return category.getChildren();
    }

    @Override
    public void addAChild(Category child, String parentId) {
        CategoryModel parentCategory = (CategoryModel) getCategory(parentId);
        Assert.notNull(parentCategory, "Can't find category with id:" + parentId);
        parentCategory.getChildren().add(child);
        categoryDao.update(parentCategory);
    }

    @Override
    public void updateCategory(Category updatedCategory, String categoryId) {
        Assert.isTrue(updatedCategory instanceof CategoryModel, "Invalid category object");
        CategoryModel oldCategory = (CategoryModel) getCategory(categoryId);
        Assert.notNull(oldCategory, "Can't find category with id:" + categoryId);
        ((CategoryModel)updatedCategory).setCategoryId(oldCategory.getCategoryId());
        BeanUtils.copyProperties(updatedCategory, oldCategory);
        categoryDao.update((CategoryModel)updatedCategory);
    }
}