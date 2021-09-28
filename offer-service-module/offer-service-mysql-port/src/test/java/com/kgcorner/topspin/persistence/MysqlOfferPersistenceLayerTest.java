package com.kgcorner.topspin.persistence;

import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlOfferDao;
import com.kgcorner.topspin.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/09/21
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
        offer.setOfferId(id);
        when(offerDao.get(id, OfferModel.class)).thenReturn(offer);
        assertEquals(id, persistenceLayer.getOffer(id).getOfferId());
    }

    @Test
    public void createOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setOfferId(id);
        persistenceLayer.createOffer(offer);
        Mockito.verify(offerDao).create(any(OfferModel.class));
    }

    @Test
    public void updateOffer() {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setOfferId(id);
        when(offerDao.get(id, OfferModel.class)).thenReturn(offer);
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
    public void deleteOffer() throws Exception {
        String id = "id";
        OfferModel offer = new OfferModel();
        offer.setOfferId(id);
        when(offerDao.get(id, OfferModel.class)).thenReturn(offer);
        persistenceLayer.deleteOffer(id);
        verifyPrivate(offerDao, times(1)).invoke("remove", offer);
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
        CategoryRef category = new CategoryReferenceModel();
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
        CategoryRef category = mock(CategoryRef.class);
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
        StoreRef store = new StoreReferenceModel();
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
        StoreRef store = mock(StoreRef.class);
        operations.add(new Operation(store, CategoryReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        persistenceLayer.getAllOfferFromStore(store, page, itemPerPage);
    }

    @Test
    public void testGetAllWithStoreAndCategory() {
        CategoryReferenceModel categoryReferenceModel = new CategoryReferenceModel();
        StoreReferenceModel storeReferenceModel = new StoreReferenceModel();
        int page = 1;
        int count = 100;
        boolean onlyFeatured = true;
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        operands.add(new Operation(onlyFeatured, Operation.TYPES.BOOLEAN, "featured", Operation.OPERATORS.EQ));
        operands.add(new Operation(true, Operation.TYPES.BOOLEAN, "banner", Operation.OPERATORS.EQ));
        operands.add(new Operation(storeReferenceModel, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        operands.add(new Operation(categoryReferenceModel, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        List<OfferModel> offerModels = new ArrayList<>();
        int size = 10;
        for(int i = 0; i<size; i++) {
            offerModels.add(new OfferModel());
        }
        when(offerDao.getAll(anyList(), anyInt(), anyInt(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operandsInMethod = invocationOnMock.getArgument(0);
                int pageInMethod = invocationOnMock.getArgument(1);
                int countInMethod = invocationOnMock.getArgument(2);

                boolean paramMatching = true;
                paramMatching = paramMatching && operandsInMethod.size() == operands.size();
                if(!paramMatching) {
                    System.out.println("Operand not matching");
                }
                paramMatching = paramMatching && pageInMethod == page;
                paramMatching = paramMatching && countInMethod == countInMethod;
                if(paramMatching) {
                    return offerModels;
                }
                return null;
            }
        });
        //when(offerDao.getAll().thenReturn(offerModels);
        List<AbstractOffer> allOffers = persistenceLayer.getAll(page, count, onlyFeatured,
            storeReferenceModel, categoryReferenceModel, false);
        assertEquals(size, allOffers.size());
        assertEquals(offerModels, allOffers);
    }

    @Test
    public void testGetAllWithStoreAndCategoryWithoutFeatured() {
        CategoryReferenceModel categoryReferenceModel = new CategoryReferenceModel();
        StoreReferenceModel storeReferenceModel = new StoreReferenceModel();
        int page = 1;
        int count = 100;
        boolean onlyFeatured = false;
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        operands.add(new Operation(true, Operation.TYPES.BOOLEAN, "banner", Operation.OPERATORS.EQ));
        operands.add(new Operation(storeReferenceModel, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        operands.add(new Operation(categoryReferenceModel, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        List<OfferModel> offerModels = new ArrayList<>();
        int size = 10;
        for(int i = 0; i<size; i++) {
            offerModels.add(new OfferModel());
        }
        when(offerDao.getAll(anyList(), anyInt(), anyInt(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operandsInMethod = invocationOnMock.getArgument(0);
                int pageInMethod = invocationOnMock.getArgument(1);
                int countInMethod = invocationOnMock.getArgument(2);

                boolean paramMatching = true;
                paramMatching = paramMatching && operandsInMethod.size() == operands.size();
                if(!paramMatching) {
                    System.out.println("Operand not matching");
                }
                paramMatching = paramMatching && pageInMethod == page;
                paramMatching = paramMatching && countInMethod == countInMethod;
                if(paramMatching) {
                    return offerModels;
                }
                return null;
            }
        });
        //when(offerDao.getAll().thenReturn(offerModels);
        List<AbstractOffer> allOffers = persistenceLayer.getAll(page, count, onlyFeatured,
            storeReferenceModel, categoryReferenceModel, false);
        assertEquals(size, allOffers.size());
        assertEquals(offerModels, allOffers);
    }

    @Test
    public void testGetAllWithStoreAndCategoryWithoutFeaturedInCludeBanner() {
        CategoryReferenceModel categoryReferenceModel = new CategoryReferenceModel();
        StoreReferenceModel storeReferenceModel = new StoreReferenceModel();
        int page = 1;
        int count = 100;
        boolean onlyFeatured = false;
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        operands.add(new Operation(storeReferenceModel, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        operands.add(new Operation(categoryReferenceModel, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        List<OfferModel> offerModels = new ArrayList<>();
        int size = 10;
        for(int i = 0; i<size; i++) {
            offerModels.add(new OfferModel());
        }
        when(offerDao.getAll(anyList(), anyInt(), anyInt(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operandsInMethod = invocationOnMock.getArgument(0);
                int pageInMethod = invocationOnMock.getArgument(1);
                int countInMethod = invocationOnMock.getArgument(2);

                boolean paramMatching = true;
                paramMatching = paramMatching && operandsInMethod.size() == operands.size();
                if(!paramMatching) {
                    System.out.println("Operand not matching");
                }
                paramMatching = paramMatching && pageInMethod == page;
                paramMatching = paramMatching && countInMethod == countInMethod;
                if(paramMatching) {
                    return offerModels;
                }
                return null;
            }
        });
        //when(offerDao.getAll().thenReturn(offerModels);
        List<AbstractOffer> allOffers = persistenceLayer.getAll(page, count, onlyFeatured,
            storeReferenceModel, categoryReferenceModel, true);
        assertEquals(size, allOffers.size());
        assertEquals(offerModels, allOffers);
    }

    @Test
    public void testGetAllWithOutStoreAndCategory() {
        int page = 1;
        int count = 100;
        boolean onlyFeatured = true;
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        operands.add(new Operation(onlyFeatured, Operation.TYPES.BOOLEAN, "featured", Operation.OPERATORS.EQ));
        operands.add(new Operation(true, Operation.TYPES.BOOLEAN, "banner", Operation.OPERATORS.EQ));
        List<OfferModel> offerModels = new ArrayList<>();
        int size = 10;
        for(int i = 0; i<size; i++) {
            offerModels.add(new OfferModel());
        }
        when(offerDao.getAll(anyList(), anyInt(), anyInt(), any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Operation> operandsInMethod = invocationOnMock.getArgument(0);
                int pageInMethod = invocationOnMock.getArgument(1);
                int countInMethod = invocationOnMock.getArgument(2);

                boolean paramMatching = true;
                paramMatching = paramMatching && operandsInMethod.size() == operands.size();
                if(!paramMatching) {
                    System.out.println("Operand not matching");
                }
                paramMatching = paramMatching && pageInMethod == page;
                paramMatching = paramMatching && countInMethod == countInMethod;
                if(paramMatching) {
                    return offerModels;
                }
                return null;
            }
        });
        //when(offerDao.getAll().thenReturn(offerModels);
        List<AbstractOffer> allOffers = persistenceLayer.getAll(page, count, onlyFeatured,
            null, null, false);
        assertEquals(size, allOffers.size());
        assertEquals(offerModels, allOffers);
    }

    @Test
    public void testGetBanners() {
        persistenceLayer.getBanners();
        Mockito.verify(offerDao).getAll(anyList(), any());
    }
}