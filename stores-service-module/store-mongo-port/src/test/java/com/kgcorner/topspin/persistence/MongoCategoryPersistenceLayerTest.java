package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MongoCategoryDao;
import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.CategoryModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;


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
        PowerMockito.when(categoryDao.create(model)).thenReturn(model);
        Category categoryModel = persistenceLayer.createCategory(model);
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
        PowerMockito.when(categoryDao.getAll(page, itemCount, CategoryModel.class)).thenReturn(models);
        List<Category> categories = persistenceLayer.getAllCategories(page, itemCount);
        Assert.assertNotNull(categories);
        Assert.assertEquals(2, categories.size());
    }

    @Test
    public void getCategory() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        PowerMockito.when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        Category categoryResult = persistenceLayer.getCategory(id);
        Assert.assertNotNull(categoryResult);
        Assert.assertEquals(model, categoryResult);
    }

    @Test
    public void getCategoryParent() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        model.setParent(new CategoryModel());
        PowerMockito.when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        Category parent = persistenceLayer.getCategoryParent(id);
        Assert.assertNotNull(parent);
        Assert.assertEquals(model.getParent(), parent);
    }

    @Test
    public void getAllChildren() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        model.getChildren().add(new CategoryModel());
        PowerMockito.when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        List<Category> children = persistenceLayer.getAllChildren(id);
        Assert.assertNotNull(children);
        Assert.assertEquals(model.getChildren().size(), children.size());
    }

    @Test
    public void addAChild() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        PowerMockito.when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        PowerMockito.when(categoryDao.update(model)).thenReturn(model);
        persistenceLayer.addAChild(new CategoryModel(), id);
        Assert.assertEquals(1, model.getChildren().size());
    }

    @Test
    public void updateCategory() {
        String id = "categoryId";
        CategoryModel model = new CategoryModel();
        CategoryModel updatedModel = new CategoryModel();
        updatedModel.setName("name");
        PowerMockito.when(categoryDao.getById(id, CategoryModel.class)).thenReturn(model);
        PowerMockito.when(categoryDao.update(model)).thenReturn(model);
        persistenceLayer.updateCategory(updatedModel, id);
        Assert.assertEquals(updatedModel.getName(), model.getName());
    }
}