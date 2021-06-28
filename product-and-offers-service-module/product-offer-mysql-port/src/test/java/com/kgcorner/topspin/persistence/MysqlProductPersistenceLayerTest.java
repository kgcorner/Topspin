package com.kgcorner.topspin.persistence;

import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlProductDao;
import com.kgcorner.topspin.dtos.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class MysqlProductPersistenceLayerTest {

    private MysqlProductDao<ProductModel> productDao;
    private MysqlProductPersistenceLayer productPersistenceLayer;

    @Before
    public void setUp() throws Exception {
        productPersistenceLayer = new MysqlProductPersistenceLayer();
        productDao = mock(MysqlProductDao.class);
        Whitebox.setInternalState(productPersistenceLayer, "productDao", productDao);
    }

    @Test
    public void getProduct() {
        String id = "productId";
        ProductModel model = new ProductModel();
        model.setProductId(id);
        when(productDao.get(id, ProductModel.class)).thenReturn(model);
        assertEquals(id, productPersistenceLayer.getProduct(id).getProductId());
    }

    @Test
    public void createProduct() {
        String id = "productId";
        ProductModel model = new ProductModel();
        model.setProductId(id);
        when(productDao.create(model)).thenReturn(model);
        assertEquals(id, productPersistenceLayer.createProduct(model).getProductId());
    }

    @Test
    public void updateProduct() {
        String id = "productId";
        ProductModel model = new ProductModel();
        model.setProductId(id);
        when(productDao.update(model)).thenReturn(model);
        assertEquals(id, productPersistenceLayer.updateProduct(model).getProductId());
    }

    @Test
    public void getAll() {
        List<ProductModel> models = new ArrayList<>();
        models.add(new ProductModel());
        int page = 10;
        int itemPerPage = 10;
        when(productDao.getAll(page, itemPerPage, ProductModel.class)).thenReturn(models);
        assertEquals(models.size(), productPersistenceLayer.getAll(page, itemPerPage).size());
    }

    @Test
    public void getAllFromCategory() {
        List<ProductModel> models = new ArrayList<>();
        models.add(new ProductModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Category category = new CategoryReferenceModel();
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        when(productDao.getAll(any(List.class), anyInt(), anyInt(),
            any(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operations1 = invocationOnMock.getArgument(0);
                int p = invocationOnMock.getArgument(1);
                int count = invocationOnMock.getArgument(2);
                Class modelType = invocationOnMock.getArgument(4);
                if(p == page && count == itemPerPage && modelType == ProductModel.class
                    && operations1.get(0).getName().equals("category"))
                    return models;
                else
                    return null;
            }
        });
        assertEquals(models.size(), productPersistenceLayer.getAllFromCategory(category, page, itemPerPage).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllFromCategoryWithMockedCategory() {
        List<ProductModel> models = new ArrayList<>();
        models.add(new ProductModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Category category = mock(Category.class);
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        productPersistenceLayer.getAllFromCategory(category, page, itemPerPage);
    }

    @Test
    public void getAllFromStore() {
        List<ProductModel> models = new ArrayList<>();
        models.add(new ProductModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Store store = new StoreReferenceModel();
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        when(productDao.getAll(any(List.class), anyInt(), anyInt(),
            any(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operations1 = invocationOnMock.getArgument(0);
                int p = invocationOnMock.getArgument(1);
                int count = invocationOnMock.getArgument(2);
                Class modelType = invocationOnMock.getArgument(4);
                if(p == page && count == itemPerPage && modelType == ProductModel.class
                    && operations1.get(0).getName().equals("store"))
                    return models;
                else
                    return null;
            }
        });
        assertEquals(models.size(), productPersistenceLayer.getAllFromStore(store, page, itemPerPage).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllFromStoreWithMockedStore() {
        List<ProductModel> models = new ArrayList<>();
        models.add(new ProductModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Store store = mock(Store.class);
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        productPersistenceLayer.getAllFromStore(store, page, itemPerPage);
    }

    @Test
    public void deleteProduct() throws Exception {
        String id = "productId";
        ProductModel model = new ProductModel();
        model.setProductId(id);
        when(productDao.get(id, ProductModel.class)).thenReturn(model);
        productPersistenceLayer.deleteProduct(id);
        verifyPrivate(productDao, times(1)).invoke("remove", model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteProductNoProduct() {
        String id = "productId";
        when(productDao.get(id, ProductModel.class)).thenReturn(null);
        productPersistenceLayer.deleteProduct(id);
    }
}