package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/08/21
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractUser {
    private String id;
    private String userName;
    private String name;
    private String email;
    private String contact;
    private String others;
    private String gender;
    private boolean active;
    private TransactionSummary transactionSummary = new TransactionSummary();
    public static class TransactionSummary {
        private final int totalAmount;
        private final int totalPendingAmount ;
        private final int totalRedeemedAmount;
        private final int totalPendingRedeemAmount ;
        private final int totalRedeemableAmountLeft ;

        public TransactionSummary() {
            totalAmount = 0;
            totalPendingAmount = 0;
            totalRedeemedAmount = 0;
            totalPendingRedeemAmount = 0;
            totalRedeemableAmountLeft = 0;
        }

        public TransactionSummary(int totalAmount, int totalPendingAmount, int totalRedeemedAmount,
                                  int totalPendingRedeemAmount, int totalRedeemableAmountLeft) {
            this.totalAmount = totalAmount;
            this.totalPendingAmount = totalPendingAmount;
            this.totalRedeemedAmount = totalRedeemedAmount;
            this.totalPendingRedeemAmount = totalPendingRedeemAmount;
            this.totalRedeemableAmountLeft = totalRedeemableAmountLeft;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public int getTotalPendingAmount() {
            return totalPendingAmount;
        }

        public int getTotalRedeemedAmount() {
            return totalRedeemedAmount;
        }

        public int getTotalPendingRedeemAmount() {
            return totalPendingRedeemAmount;
        }

        public int getTotalRedeemableAmountLeft() {
            return totalRedeemableAmountLeft;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TransactionSummary that = (TransactionSummary) o;
            return totalAmount == that.totalAmount &&
                totalPendingAmount == that.totalPendingAmount &&
                totalRedeemedAmount == that.totalRedeemedAmount &&
                totalPendingRedeemAmount == that.totalPendingRedeemAmount &&
                totalRedeemableAmountLeft == that.totalRedeemableAmountLeft;
        }

        @Override
        public int hashCode() {
            return Objects.hash(totalAmount, totalPendingAmount,
                totalRedeemedAmount,
                totalPendingRedeemAmount, totalRedeemableAmountLeft);
        }
    }
}