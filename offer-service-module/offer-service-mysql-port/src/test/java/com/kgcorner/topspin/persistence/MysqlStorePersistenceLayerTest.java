package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MysqlDao;
import com.kgcorner.topspin.model.StoreRef;
import com.kgcorner.topspin.model.StoreReferenceModel;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;


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
}