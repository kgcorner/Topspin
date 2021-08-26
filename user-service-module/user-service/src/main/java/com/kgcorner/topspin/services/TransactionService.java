package com.kgcorner.topspin.services;


import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.model.AbstractUser;
import com.kgcorner.topspin.persistence.TransactionPersistenceLayer;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

@Service
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);
    @Autowired
    private TransactionPersistenceLayer persistenceLayer;

    @Autowired
    private UserService userService;

    public TransactionDTO awardCashbackToUser(TransactionDTO transactionDTO) {
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_PENDING);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        transactionDTO.setRedeemedAmount(0);
        return createTransaction(transactionDTO);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Assert.notNull(transactionDTO, "Invalid transaction given");
        AbstractTransaction transaction = persistenceLayer.createTransaction(transactionDTO);
        try {
            rebalanceTransactionForUser(transactionDTO.getUserId());
        } catch (Exception x) {
            LOGGER.error(x.getMessage(), x);
            deleteTransaction(transaction.getId());
            throw new IllegalStateException("Failed to rebalance user's transaction, Transaction rollbacked");
        }
        BeanUtils.copyProperties(transaction, transactionDTO);
        return transactionDTO;
    }

    public TransactionDTO updateTransaction(String transactionId, TransactionDTO transactionDTO) {
        TransactionDTO tempTransaction = new TransactionDTO();
        BeanUtils.copyProperties(transactionDTO, tempTransaction);
        Assert.notNull(transactionDTO, "Invalid transaction given");
        AbstractTransaction abstractTransaction = persistenceLayer.updateTransaction(transactionDTO, transactionId);
        try {
            rebalanceTransactionForUser(transactionDTO.getUserId());
        } catch (Exception x) {
            LOGGER.error(x.getMessage(), x);
            persistenceLayer.updateTransaction(tempTransaction, transactionId);
            throw new IllegalStateException("Failed to rebalance user's transaction, Transaction rollbacked");
        }
        BeanUtils.copyProperties(abstractTransaction, transactionDTO);
        return transactionDTO;
    }

    public TransactionDTO getTransaction(String transactionId) {
        AbstractTransaction transaction = persistenceLayer.getTransaction(transactionId);
        if(transaction == null)
            throw new IllegalArgumentException("No transaction found");
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);
        return transactionDTO;
    }

    public List<TransactionDTO> getUsersTransaction(String userId, int page, int max) {
        List<AbstractTransaction> usersTransactions = persistenceLayer.getUsersTransactions(userId, page, max);
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(AbstractTransaction transaction : usersTransactions) {
            TransactionDTO transactionDTO = new TransactionDTO();
            BeanUtils.copyProperties(transaction, transactionDTO);
            transactionDTOS.add(transactionDTO);
        }
        return transactionDTOS;
    }

    public TransactionDTO approveTransaction(String transactionId) {
        TransactionDTO transactionDTO = getTransaction(transactionId);
        TransactionDTO tempTransaction = new TransactionDTO();
        BeanUtils.copyProperties(transactionDTO, tempTransaction);
        if(transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED ||
            transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED )
            throw new IllegalArgumentException("Transaction is already Approved");
        if(transactionDTO.getTransactionKind() == AbstractTransaction.TRANSACTION_KIND.CREDIT)
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED);
        if(transactionDTO.getTransactionKind() == AbstractTransaction.TRANSACTION_KIND.DEBIT)
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED);
        transactionDTO = updateTransaction(transactionId, transactionDTO);
        return transactionDTO;
    }

    public void deleteTransaction(String transactionId) {
        persistenceLayer.deleteTransaction(transactionId);
    }

    private void rebalanceTransactionForUser(String userId) {

        UserDTO userDTO = userService.getUser(userId);

        int totalAmount = 0;
        int totalPendingAmount = 0;
        int totalRedeemedAmount = 0;
        int totalPendingRedeemAmount = 0;
        int totalRedeemableAmountLeft = 0;
        List<TransactionDTO> transactionDTOList = getUsersTransaction(userId, 0, -1);
        if(transactionDTOList.size() > 0) {
            for (TransactionDTO transactionDTO : transactionDTOList) {
                if (transactionDTO.getTransactionKind() == AbstractTransaction.TRANSACTION_KIND.CREDIT) {
                    if (transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED)
                        totalAmount += transactionDTO.getAwardedAmount();
                    if (transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.CREDIT_PENDING) {
                        totalPendingAmount += transactionDTO.getAwardedAmount();
                        totalAmount += transactionDTO.getAwardedAmount();
                    }
                } else {
                    if (transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED)
                        totalRedeemedAmount += transactionDTO.getRedeemedAmount();
                    else if (transactionDTO.getTransactionState() == AbstractTransaction.TRANSACTION_STATE.REDEEM_PENDING)
                        totalPendingRedeemAmount += transactionDTO.getRedeemedAmount();
                }

            }
            totalRedeemableAmountLeft = (totalAmount - totalPendingAmount - totalRedeemedAmount - totalPendingRedeemAmount);
            UserDTO.TransactionSummary transactionSummary = new AbstractUser.TransactionSummary(totalAmount,
                totalPendingAmount, totalRedeemedAmount, totalPendingRedeemAmount, totalRedeemableAmountLeft);
            userDTO.setTransactionSummary(transactionSummary);
            userService.updateUser(userId, userDTO);
        }
    }

    public TransactionDTO approveCashback(String transactionId, String moderatorRemarks) {
        Date date = new Date();
        moderatorRemarks = date.toString() + moderatorRemarks;
        TransactionDTO transactionDTO = getTransaction(transactionId);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED);
        transactionDTO.getModeratorRemark().add(moderatorRemarks);
        return updateTransaction(transactionId, transactionDTO);
    }

    public TransactionDTO rejectCashback(String transactionId, String moderatorRemarks) {
        Date date = new Date();
        moderatorRemarks = date.toString() + moderatorRemarks;
        TransactionDTO transactionDTO = getTransaction(transactionId);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_CANCELLED);
        transactionDTO.getModeratorRemark().add(moderatorRemarks);
        return updateTransaction(transactionId, transactionDTO);
    }

    public TransactionDTO createTransactionHold(String transactionId, String moderatorRemarks) {
        Date date = new Date();
        moderatorRemarks = date.toString() + moderatorRemarks;
        TransactionDTO transactionDTO = getTransaction(transactionId);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.HOLD);
        transactionDTO.getModeratorRemark().add(moderatorRemarks);
        return updateTransaction(transactionId, transactionDTO);
    }

    public TransactionDTO createRedeemRequest(TransactionDTO transactionDTO) {
        UserDTO userDTO = userService.getUser(transactionDTO.getUserId());
        if(userDTO.getTransactionSummary().getTotalRedeemableAmountLeft() < transactionDTO.getRedeemedAmount())
            throw new IllegalArgumentException("Do not have enough redeemable amount");
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_PENDING);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
        transactionDTO.setAwardedAmount(0);
        return createTransaction(transactionDTO);
    }

    public TransactionDTO approveRedeem(String transactionId, String moderatorRemark) {
        Date date = new Date();
        moderatorRemark = date.toString() + moderatorRemark;
        TransactionDTO transactionDTO = getTransaction(transactionId);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED);
        transactionDTO.getModeratorRemark().add(moderatorRemark);
        return updateTransaction(transactionId, transactionDTO);
    }

    public TransactionDTO rejectRedeem(String transactionId, String moderatorRemark) {
        Date date = new Date();
        moderatorRemark = date.toString() + moderatorRemark;
        TransactionDTO transactionDTO = getTransaction(transactionId);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_CANCELLED);
        transactionDTO.getModeratorRemark().add(moderatorRemark);
        return updateTransaction(transactionId, transactionDTO);
    }
}