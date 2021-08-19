package com.kgcorner.topspin.persistence;


import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MongoTransactionDao;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.model.TransactionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */
@Repository
public class MongoTransactionPersistenceLayer implements TransactionPersistenceLayer {

    @Autowired
    private MongoTransactionDao<TransactionModel> transactionDao;

    @Override
    public AbstractTransaction createTransaction(AbstractTransaction transaction) {
        transaction.validate();
        TransactionModel model = new TransactionModel();
        BeanUtils.copyProperties(transaction, model);
        return transactionDao.create(model);
    }

    @Override
    public AbstractTransaction updateTransaction(AbstractTransaction transaction, String transactionId) {
        Assert.notNull(transactionId, "transaction id can't be null");
        transaction.validate();
        AbstractTransaction transactionModel = getTransaction(transactionId);
        Assert.notNull(transactionModel, "Can't find transaction");
        BeanUtils.copyProperties(transaction, transactionModel);
        transactionModel.setId(transactionId);
        return transactionDao.update((TransactionModel) transactionModel);
    }

    @Override
    public AbstractTransaction getTransaction(String transactionId) {
        Assert.notNull(transactionId," Transaction id can't be null");
        return transactionDao.getById(transactionId, TransactionModel.class);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        AbstractTransaction transaction = getTransaction(transactionId);
        Assert.notNull(transaction, "Can't find transaction");
        transactionDao.remove((TransactionModel) transaction);
    }

    @Override
    public List<AbstractTransaction> getTransactions(int page, int max) {
        if(max == 0)
            return Collections.emptyList();
        List<TransactionModel> all = transactionDao.getAll(page, max, TransactionModel.class);
        List<AbstractTransaction> transactions = new ArrayList<>();
        for (AbstractTransaction transaction: all) {
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<AbstractTransaction> getUsersTransactions(String userId, int page, int max) {
        Assert.notNull(userId," User id can't be null");
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(userId, Operation.TYPES.STRING, "userId", Operation.OPERATORS.EQ);
        operations.add(operation);
        List<TransactionModel> all = transactionDao.getAll(operations, page, max, TransactionModel.class);
        List<AbstractTransaction> transactions = new ArrayList<>();
        for (TransactionModel model : all) {
            transactions.add(model);
        }
        return transactions;
    }
}