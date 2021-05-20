package com.kgcorner.topspin.persistence;

import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlOfferDao;
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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/05/21
 */

public class MysqlOfferPersistenceLayerTest {

    private MysqlOfferDao<OfferModel> offerDao;
    private MysqlOfferPersistenceLayer persistenceLayer;

    @Before
    public void setUp() throws Exception {
        persistenceLayer = new MysqlOfferPersistenceLayer();
        offerDao = mock(MysqlOfferDao.class);
        Whitebox.setInternalState(persistenceLayer, "offerDao", offerDao);
    }

    @Test
    public void getOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setId(id);
        when(offerDao.get(id, OfferModel.class)).thenReturn(offer);
        assertEquals(id, persistenceLayer.getOffer(id).getOfferId());
    }

    @Test
    public void createOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setId(id);
        when(offerDao.create(offer)).thenReturn(offer);
        assertEquals(id, persistenceLayer.createOffer(offer).getOfferId());
    }

    @Test
    public void updateOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setId(id);
        when(offerDao.update(offer)).thenReturn(offer);
        assertEquals(id, persistenceLayer.updateOffer(offer).getOfferId());
    }

    @Test
    public void getAll() {
        List<OfferModel> models = new ArrayList<>();
        models.add(new OfferModel());
        int page = 10;
        int count = 10;
        when(offerDao.getAll(page, count, OfferModel.class)).thenReturn(models);
        assertEquals(models.size(), persistenceLayer.getAll(page, count).size());
    }

    @Test
    public void deleteOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setId(id);
        when(offerDao.get(id, OfferModel.class)).thenReturn(offer);
        persistenceLayer.deleteOffer(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOfferNoOffer() {
        String id = "id";
        when(offerDao.get(id, OfferModel.class)).thenReturn(null);
        persistenceLayer.deleteOffer(id);
    }

    @Test
    public void getAllOfferFromCategory() {
        List<OfferModel> models = new ArrayList<>();
        models.add(new OfferModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Category category = new CategoryReferenceModel();
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        when(offerDao.getAll(any(List.class), anyInt(), anyInt(),
            any(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operations1 = invocationOnMock.getArgument(0);
                int p = invocationOnMock.getArgument(1);
                int count = invocationOnMock.getArgument(2);
                Class modelType = invocationOnMock.getArgument(4);
                if(p == page && count == itemPerPage && modelType == OfferModel.class
                    && operations1.get(0).getName().equals("category"))
                    return models;
                else
                    return null;
            }
        });
        assertEquals(models.size(), persistenceLayer.getAllOfferFromCategory(category, page, itemPerPage).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllOfferFromCategoryWrongCategoryType() {
        List<OfferModel> models = new ArrayList<>();
        models.add(new OfferModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Category category = mock(Category.class);
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        persistenceLayer.getAllOfferFromCategory(category, page, itemPerPage);
    }

    @Test
    public void getAllOfferFromStore() {
        List<OfferModel> models = new ArrayList<>();
        models.add(new OfferModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Store store = new StoreReferenceModel();
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        when(offerDao.getAll(any(List.class), anyInt(), anyInt(),
            any(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operations1 = invocationOnMock.getArgument(0);
                int p = invocationOnMock.getArgument(1);
                int count = invocationOnMock.getArgument(2);
                Class modelType = invocationOnMock.getArgument(4);
                if(p == page && count == itemPerPage && modelType == OfferModel.class
                    && operations1.get(0).getName().equals("store"))
                    return models;
                else
                    return null;
            }
        });
        assertEquals(models.size(), persistenceLayer.getAllOfferFromStore(store, page, itemPerPage).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllOfferFromStoreWrongStoreType() {
        List<OfferModel> models = new ArrayList<>();
        models.add(new OfferModel());
        int page = 10;
        int itemPerPage = 10;
        List<Operation> operations = new ArrayList<>();
        Store store = mock(Store.class);
        operations.add(new Operation(store, CategoryReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        persistenceLayer.getAllOfferFromStore(store, page, itemPerPage);
    }
}