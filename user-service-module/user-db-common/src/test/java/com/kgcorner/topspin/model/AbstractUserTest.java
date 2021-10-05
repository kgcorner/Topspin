package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 05/10/21
 */

public class AbstractUserTest {
    @Test
    public void testTransactionSummary() {
        AbstractUser.TransactionSummary summary = new AbstractUser.TransactionSummary();
        int totalAmount = 200;
        int totalPendingAmount = 100 ;
        int totalRedeemedAmount = 0;
        int totalPendingRedeemAmount = 0;
        int totalRedeemableAmountLeft = 100;
        summary.setTotalAmount(totalAmount);
        summary.setTotalPendingAmount(totalPendingAmount);
        summary.setTotalPendingRedeemAmount(totalPendingRedeemAmount);
        summary.setTotalRedeemableAmountLeft(totalRedeemableAmountLeft);
        summary.setTotalRedeemedAmount(totalRedeemedAmount);

        assertEquals(totalAmount, summary.getTotalAmount());
        assertEquals(totalPendingAmount, summary.getTotalPendingAmount());
        assertEquals(totalRedeemedAmount, summary.getTotalRedeemedAmount());
        assertEquals(totalPendingRedeemAmount, summary.getTotalPendingRedeemAmount());
        assertEquals(totalRedeemableAmountLeft, summary.getTotalRedeemableAmountLeft());
    }
}