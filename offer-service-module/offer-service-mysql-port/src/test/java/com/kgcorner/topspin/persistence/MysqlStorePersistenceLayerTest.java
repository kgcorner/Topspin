package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.StoreRef;
import com.kgcorner.topspin.model.StoreReferenceModel;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/09/21
 */

public class MysqlStorePersistenceLayerTest {

    private MysqlStorePersistenceLayer storePersistenceLayer;
    private MysqlDao<StoreReferenceModel> storeDao;

    @Before
    public void setUp() throws Exception {
        storePersistenceLayer = new MysqlStorePersistenceLayer();
        storeDao = mock(MysqlDao.class);
        Whitebox.setInternalState(storePersistenceLayer,"storeDao", storeDao);
    }

    @Test
    public void createStore() {
        StoreRef storeRef = new StoreRef();
        storePersistenceLayer.createStore(storeRef);
        verify(storeDao).create(any(StoreReferenceModel.class));
    }

    @Test
    public void getStore() {
        String storeId = "id";
        storePersistenceLayer.getStore(storeId);
        verify(storeDao).get(storeId, StoreReferenceModel.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStoreWithNullId() {
        String storeId = "";
        storePersistenceLayer.getStore(storeId);
    }

    @Test
    public void getStoresWithOfferCount() {
        List<Object[]> results = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Object[] value = new Object[5];
            value[0] = i+"";
            value[1] = "name" + i;
            value[2] = "description" + i;
            value[3] = i + "%";
            value[4] = i;
            results.add(value);
        }
        when(storeDao.runSelectNativeQuery(anyString())).thenReturn(results);
        List<StoreRef> storesWithOfferCount = storePersistenceLayer.getStoresWithOfferCount();
        assertNotNull(storesWithOfferCount);
        assertEquals(results.size(), storesWithOfferCount.size());
        for (int i = 0; i < storesWithOfferCount.size(); i++) {
            StoreRef storeRef = storesWithOfferCount.get(i);
            assertEquals(i+"", storeRef.getId());
            assertEquals("name" + i, storeRef.getName());
            assertEquals("description" + i, storeRef.getDescription());
            assertEquals(i+"%", storeRef.getMaxCashback());
            assertEquals(i, storeRef.getOffersCount());
        }
    }
}