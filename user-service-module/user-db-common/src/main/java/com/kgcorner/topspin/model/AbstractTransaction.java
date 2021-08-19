package com.kgcorner.topspin.model;


import com.kgcorner.utils.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */
@NoArgsConstructor
@Data
public abstract class AbstractTransaction {
    public enum TRANSACTION_KIND {
        CREDIT,
        DEBIT
    }

    public enum TRANSACTION_ACTION {
        CASHBACK,
        REDEEM,
        BONUS,
        REFERRAL
    }

    public enum TRANSACTION_STATE {
        /**
         * When Transaction approved but not eligible for Redeeming, Applicable only on {@link TRANSACTION_KIND#CREDIT}
         */
        CREDIT_PENDING,
        /**
         * When Redeem request is initiated but amount is not released, Applicable only on {@link TRANSACTION_KIND#DEBIT}
         */
        REDEEM_PENDING,
        /**
         * When Transaction is completed, and Amount is eligible to be redeemed.
         * Applicable only on {@link TRANSACTION_KIND#CREDIT}
         */
        CREDIT_COMPLETED,
        /**
         * When Redeemed amount is released.
         * Applicable only on {@link TRANSACTION_KIND#DEBIT}
         */
        REDEEM_COMPLETED,
        /**
         * When credit transaction is cancelled
         */
        CREDIT_CANCELLED,
        /**
         * When redeem trsansaction is cancelled
         */
        REDEEM_CANCELLED,
        /**
         * When transaction is on hold for further clarification
         */
        HOLD
    }

    public static  class Item {
        private String name;
        private String link;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    private String id;
    private Date date;
    /**
     * What kind of transaction it is
     */
    private TRANSACTION_KIND transactionKind;
    /**
     * What action was taken in order to initiate this transaction
     */
    private TRANSACTION_ACTION transactionAction;

    /**
     * Id of the User for whom the transaction is created
     */
    private String userId;

    /**
     * Current state of the transaction
     */
    private TRANSACTION_STATE transactionState;

    /**
     * Total amount involved in the transaction
     */
    private double totalAmount;
    /**
     * Moderator remarks, if any
     */
    private List<String> moderatorRemark = new ArrayList<>();

    /**
     * Items involved in the transactions, eg: products
     */
    private List<Item> items = new ArrayList<>();

    /**
     * Total amount awarded to user. This will be applicable only for {@link TRANSACTION_KIND#CREDIT} transactions
     */
    private double awardedAmount;

    /**
     * Total amount requested to be redeemed.  This will be applicable only for {@link TRANSACTION_KIND#DEBIT}
     * transactions
     */
    private double redeemedAmount;


    /**
     * Throws {@link IllegalArgumentException} if object is invalid
     */
    public void validate() {
        if(date == null)
            throw new IllegalArgumentException("Date can't be null");
        if(totalAmount <= 0)
            throw new IllegalArgumentException("Amount can't be 0 or less");
        if(transactionKind == null)
            throw new IllegalArgumentException("transaction kind can't be null");
        if(transactionState == null)
            throw new IllegalArgumentException("transaction state can't be null");
        if(transactionAction == null)
            throw new IllegalArgumentException("Transaction action can't be null");

        if(awardedAmount > 0 && transactionKind != TRANSACTION_KIND.CREDIT)
            throw new IllegalArgumentException("Only credit transaction can have awarded amount");
        if(redeemedAmount >0 && transactionKind != TRANSACTION_KIND.DEBIT)
            throw new IllegalArgumentException("Only debit transaction can have redeemed amount");
        if(transactionKind == TRANSACTION_KIND.CREDIT && (transactionAction == TRANSACTION_ACTION.REDEEM)) {
            throw new IllegalArgumentException("Only debit transaction can have redeemed action");
        }

        if(transactionKind == TRANSACTION_KIND.CREDIT &&
            (transactionState == TRANSACTION_STATE.REDEEM_CANCELLED ||
                transactionState == TRANSACTION_STATE.REDEEM_COMPLETED||
                transactionState == TRANSACTION_STATE.REDEEM_PENDING)) {
            throw new IllegalArgumentException("Only debit transaction can have redeemable state");
        }

        if(transactionKind == TRANSACTION_KIND.DEBIT && (transactionAction != TRANSACTION_ACTION.REDEEM)) {
            throw new IllegalArgumentException("Debit transaction can have only redeemed action");
        }

        if(transactionKind == TRANSACTION_KIND.DEBIT &&
            (transactionState == TRANSACTION_STATE.CREDIT_CANCELLED ||
                transactionState == TRANSACTION_STATE.CREDIT_COMPLETED||
                transactionState == TRANSACTION_STATE.CREDIT_PENDING)) {
            throw new IllegalArgumentException("Only credit transaction can have creditable state");
        }

        if(totalAmount <= 0 && transactionAction == TRANSACTION_ACTION.CASHBACK)
            throw new IllegalArgumentException("Total amount can'te be 0 or less");
        if(Strings.isNullOrEmpty(userId))
            throw new IllegalArgumentException("User id can't be null");
    }
}