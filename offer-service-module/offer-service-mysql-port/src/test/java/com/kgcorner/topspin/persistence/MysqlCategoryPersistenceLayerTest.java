package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.CategoryRef;
import com.kgcorner.topspin.model.CategoryReferenceModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/09/21
 */

public class MysqlCategoryPersistenceLayerTest {

    private MysqlCategoryPersistenceLayer categoryPersistenceLayer;
    private MysqlDao<CategoryReferenceModel> categoryDao;

    @Before
    public void setUp() throws Exception {
        categoryPersistenceLayer = new MysqlCategoryPersistenceLayer();
        categoryDao = mock(MysqlDao.class);
        Whitebox.setInternalState(categoryPersistenceLayer, "categoryDao", categoryDao);
    }

    @Test
    public void createCategory() {
        CategoryRef categoryRef = new CategoryRef();
        categoryPersistenceLayer.createCategory(categoryRef);
        Mockito.verify(categoryDao).create(any(CategoryReferenceModel.class));
    }

    @Test
    public void getCategory() {
        String id = "id";
        categoryPersistenceLayer.getCategory(id);
        Mockito.verify(categoryDao).get(id, CategoryReferenceModel.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCategoryWithNullId() {
        String id = "";
        categoryPersistenceLayer.getCategory(id);
    }
}