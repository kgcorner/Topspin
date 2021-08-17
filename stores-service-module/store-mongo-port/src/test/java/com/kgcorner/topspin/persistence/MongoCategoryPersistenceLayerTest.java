package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MongoCategoryDao;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.model.CategoryModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/01/21
 */

public class MongoCategoryPersistenceLayerTest {

    private MongoCategoryPersistenceLayer persistenceLayer;
    private MongoCategoryDao<CategoryModel> categoryDao;

    @Before
    public void steup() {
        persistenceLayer = new MongoCategoryPersistenceLayer();
        categoryDao = PowerMockito.mock(MongoCategoryDao.class);
        Whitebox.setInternalState(persistenceLayer, "categoryDao", categoryDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCategoryWithNullCategory() {
        persistenceLayer.createCategory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCategoryWithNullCategoryName() {
        CategoryModel model = new CategoryModel();
        persistenceLayer.createCategory(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCategoryWithNullCategoryDescription() {
        CategoryModel model = new CategoryModel();
        model.setName("name");
        persistenceLayer.createCategory(model);
    }

    @Test
    public void createCategory() {
        CategoryModel model = new CategoryModel();
        model.setName("name");
        model.setDescription("description");
        when(categoryDao.create(model)).thenReturn(model);
        AbstractCategory categoryModel = persistenceLayer.createCategory(model);
        Assert.assertNotNull(categoryModel);
        Assert.assertEquals("name", categoryModel.getName());
        Assert.assertEquals("description", categoryModel.getDescription());
    }

    @Test
    public void getAllCategories() {
        int page = 0;
        int itemCount = 2;
        List<CategoryModel> models = new ArrayList<>();
        models.add(new CategoryModel());
        models.add(new CategoryModel());
        when(categoryDao.getAll(page, itemCount, CategoryModel.class)).thenReturn(models);
        List<? extends AbstractCategory> categories = persistenceLayer.getAllCategories(page, itemCount);
        Assert.assertNotNull(categories);
        Assert.assertEquals(2, categories.size());
    }


    @Test
    public void getCategory() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        AbstractCategory categoryResult = persistenceLayer.getCategory(id);
        Assert.assertNotNull(categoryResult);
        Assert.assertEquals(model, categoryResult);
    }

    @Test
    public void getCategoryParent() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        model.setParent(new CategoryModel());
        when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        AbstractCategory parent = persistenceLayer.getCategoryParent(id);
        Assert.assertNotNull(parent);
        Assert.assertEquals(model.getParent(), parent);
    }

    @Test
    public void getAllChildren() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        CategoryModel child = new CategoryModel();
        List<CategoryModel> children = new ArrayList<>();
        children.add(child);
        model.setChildren(children);
        when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        List<? extends AbstractCategory> allChildren = persistenceLayer.getAllChildren(id);
        Assert.assertNotNull(children);
        Assert.assertEquals(model.getChildren().size(), children.size());
    }

    @Test
    public void updateCategory() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        CategoryModel updatedModel = new CategoryModel();
        updatedModel.setName("name");
        when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        when(categoryDao.update(model)).thenReturn(model);
        persistenceLayer.updateCategory(updatedModel, id);
        Assert.assertEquals(updatedModel.getName(), model.getName());
    }

    @Test
    public void deleteCategory() {
        String categoryId = "categoryId";
        CategoryModel categoryModel = new CategoryModel();
        when(categoryDao.getById(categoryId, CategoryModel.class)).thenReturn(categoryModel);
        persistenceLayer.deleteCategory(categoryId);
        Mockito.verify(categoryDao).remove(categoryModel);
    }
}