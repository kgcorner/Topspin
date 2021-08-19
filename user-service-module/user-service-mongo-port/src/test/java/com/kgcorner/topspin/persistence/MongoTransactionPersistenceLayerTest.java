package com.kgcorner.topspin.persistence;

import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MongoTransactionDao;
import com.kgcorner.topspin.model.TransactionModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */

public class MongoTransactionPersistenceLayerTest {
    private MongoTransactionDao<TransactionModel> transactionDao;
    private MongoTransactionPersistenceLayer persistenceLayer;

    @Before
    public void setUp() throws Exception {
        persistenceLayer = new MongoTransactionPersistenceLayer();
        transactionDao = PowerMockito.mock(MongoTransactionDao.class);
        Whitebox.setInternalState(persistenceLayer, "transactionDao", transactionDao);
    }

    @Test
    public void createTransaction() {
        TransactionModel transactionModel = mock(TransactionModel.class);
        doNothing().when(transactionModel).validate();
        when(transactionDao.create(any(TransactionModel.class))).thenReturn(transactionModel);
        assertEquals(transactionModel, persistenceLayer.createTransaction(transactionModel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTransactionWithInvalidTransaction() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAwardedAmount(100);
        when(transactionDao.create(transactionModel)).thenReturn(transactionModel);
        persistenceLayer.createTransaction(transactionModel);
    }

    @Test
    public void updateTransaction() {
        String transactiondId = "id";
        TransactionModel transactionModel = mock(TransactionModel.class);
        doNothing().when(transactionModel).validate();
        when(transactionDao.getById(transactiondId, TransactionModel.class)).thenReturn(transactionModel);
        when(transactionDao.update(transactionModel)).thenReturn(transactionModel);
        assertEquals(transactionModel, persistenceLayer.updateTransaction(transactionModel, transactiondId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTransactionWithNullId() {
        String transactiondId = null;
        TransactionModel transactionModel = new TransactionModel();
        persistenceLayer.updateTransaction(transactionModel, transactiondId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTransactionNonExistingTransaction() {
        String transactiondId = "id";
        TransactionModel transactionModel = mock(TransactionModel.class);
        doNothing().when(transactionModel).validate();
        when(transactionDao.getById(transactiondId, TransactionModel.class)).thenReturn(null);
        persistenceLayer.updateTransaction(transactionModel, transactiondId);
    }

    @Test
    public void getTransaction() {
        String transactiondId = "id";
        TransactionModel transactionModel = mock(TransactionModel.class);
        when(transactionDao.getById(transactiondId, TransactionModel.class)).thenReturn(transactionModel);
        assertEquals(transactionModel, persistenceLayer.getTransaction(transactiondId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTransactionWithNullId() {
        String transactiondId = null;
        persistenceLayer.getTransaction(transactiondId);
    }

    @Test
    public void deleteTransaction() {
        String transactionId = "id";
        TransactionModel transactionModel = mock(TransactionModel.class);
        when(transactionDao.getById(transactionId, TransactionModel.class)).thenReturn(transactionModel);
        persistenceLayer.deleteTransaction(transactionId);
        Mockito.verify(transactionDao).remove(transactionModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTransactionWithNonExistingTransaction() {
        String transactionId = "id";
        when(transactionDao.getById(transactionId, TransactionModel.class)).thenReturn(null);
        persistenceLayer.deleteTransaction(transactionId);
    }

    @Test
    public void getTransactions() {
        int page = 0;
        int size = 10;
        List<TransactionModel> transactionModels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            transactionModels.add(new TransactionModel());
        }
        when(transactionDao.getAll(page, size, TransactionModel.class)).thenReturn(transactionModels);
        assertEquals(transactionModels, persistenceLayer.getTransactions(page, size));
    }

    @Test
    public void getUsersTransactions() {
        int page = 0;
        int size = 10;
        String userId = "userId";
        List<TransactionModel> transactionModels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            transactionModels.add(new TransactionModel());
        }
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(userId, Operation.TYPES.STRING, "userId", Operation.OPERATORS.EQ);
        operations.add(operation);
        when(transactionDao.getAll(any(List.class), anyInt(), anyInt(), any())).thenReturn(transactionModels);
        assertEquals(transactionModels, persistenceLayer.getUsersTransactions(userId, page, size));
    }
}