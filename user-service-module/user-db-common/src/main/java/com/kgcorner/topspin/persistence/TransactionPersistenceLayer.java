package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.AbstractTransaction;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */

public interface TransactionPersistenceLayer {

    /**
     * Creates and returns created transaction
     * @param transaction transaction to be created
     * @return created transaction
     */
    AbstractTransaction createTransaction(AbstractTransaction transaction);

    /**
     * Updates and returns updated transaction
     * @param transaction transaction to be updated
     * @return updated transaction
     */
    AbstractTransaction updateTransaction(AbstractTransaction transaction, String transactionId);

    /**
     * fetches transaction
     * @param transactionId id of the transaction
     * @return fetched transaction
     */
    AbstractTransaction getTransaction(String transactionId);

    /**
     * Deletes transaction
     * @param transactionId
     */
    void deleteTransaction(String transactionId);

    /**
     * Returns list of transactions
     * @param page page number
     * @param max max items per page
     * @return list of transaction in given page
     */
    List<AbstractTransaction> getTransactions(int page, int max);

    /**
     * Returns list of transactions
     * @param userId's returns user's transaction
     * @param page page number
     * @param max max items per page
     * @return list of transaction in given page
     */
    List<AbstractTransaction> getUsersTransactions(String userId, int page, int max);
}