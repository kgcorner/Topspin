package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

public class TransactionModelTest {

    @Test
    public void testTransactionModel() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId("id");
        assertEquals("id", transactionModel.getId());
    }

}