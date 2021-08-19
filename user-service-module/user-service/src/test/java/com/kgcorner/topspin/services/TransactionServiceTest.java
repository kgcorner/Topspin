package com.kgcorner.topspin.services;

import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.model.AbstractUser;
import com.kgcorner.topspin.persistence.TransactionPersistenceLayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

public class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionPersistenceLayer persistenceLayer;
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        transactionService = new TransactionService();
        userService = mock(UserService.class);
        persistenceLayer = mock(TransactionPersistenceLayer.class);
        Whitebox.setInternalState(transactionService, "persistenceLayer", persistenceLayer);
        Whitebox.setInternalState(transactionService, "userService", userService);
    }

    @Test
    public void awardCashbackToUser() {
        String userId = "userId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        PowerMockito.when(persistenceLayer.createTransaction(transactionDTO)).thenReturn(transactionDTO);
        TransactionDTO result = transactionService.awardCashbackToUser(transactionDTO);
        assertEquals(AbstractTransaction.TRANSACTION_KIND.CREDIT, result.getTransactionKind());
        assertEquals(AbstractTransaction.TRANSACTION_STATE.CREDIT_PENDING, transactionDTO.getTransactionState());
        assertEquals(0, transactionDTO.getRedeemedAmount(), 0.0);
    }

    @Test
    public void createTransaction() {
        String userId = "userId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        PowerMockito.when(persistenceLayer.createTransaction(transactionDTO)).thenReturn(transactionDTO);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);
        assertEquals(transactionDTO, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullTransaction() {
        transactionService.createTransaction(null);
    }

    @Test(expected = IllegalStateException.class)
    public void createNullTransactionRebalanceFailed() {
        String userId = "userId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId("id");
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenThrow(RuntimeException.class);
        PowerMockito.when(persistenceLayer.createTransaction(transactionDTO)).thenReturn(transactionDTO);
        transactionService.createTransaction(transactionDTO);
        Mockito.verify(persistenceLayer).deleteTransaction("id");
    }

    @Test
    public void updateTransaction() {
        String userId = "userId";
        String txId = "txId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(txId);
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        PowerMockito.when(persistenceLayer.updateTransaction(transactionDTO, txId)).thenReturn(transactionDTO);
        TransactionDTO result = transactionService.updateTransaction(txId, transactionDTO);
        assertEquals(transactionDTO, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullTransaction() {
        transactionService.updateTransaction("txId", null);
    }

    @Test(expected = IllegalStateException.class)
    public void updateTransactionRebalanceFailed() {
        String userId = "userId";
        String txId = "txId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(txId);
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenThrow(RuntimeException.class);
        PowerMockito.when(persistenceLayer.updateTransaction(transactionDTO, txId)).thenReturn(transactionDTO);
        transactionService.updateTransaction(txId, transactionDTO);
        Mockito.verify(persistenceLayer).deleteTransaction("id");
    }

    @Test
    public void getTransaction() {
        TransactionDTO transactionDTO = mock(TransactionDTO.class);
        String txId = "txId";
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(transactionDTO.getId()).thenReturn(txId);
        TransactionDTO result = transactionService.getTransaction(txId);
        assertEquals(txId, result.getId());
    }

    @Test
    public void getUsersTransaction() {
        String userId = "userId";
        int page = 0;
        List<AbstractTransaction> transactionDTOS = new ArrayList<>();
        int size = 10;
        for (int i=0; i<size; i++) {
            transactionDTOS.add(new TransactionDTO());
        }
        when(persistenceLayer.getUsersTransactions(userId, page, size)).thenReturn(transactionDTOS);
        List<TransactionDTO> usersTransaction = transactionService.getUsersTransaction(userId, page, size);
        assertEquals(size, usersTransaction.size());
    }

    @Test
    public void approveCreditTransaction() {
        String userId = "userId";
        String txId = "txId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        transactionDTO.setId(txId);
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.approveTransaction(txId);
        assertEquals(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED, result.getTransactionState());
    }

    @Test
    public void approveDebitTransaction() {
        String userId = "userId";
        String txId = "txId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
        transactionDTO.setId(txId);
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.approveTransaction(txId);
        assertEquals(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED, result.getTransactionState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void approveTransactionNotFound() {
        String txId = "txid";
        when(persistenceLayer.getTransaction(txId)).thenReturn(null);
        transactionService.approveTransaction(txId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void approveTransactionAlreadyApprovedTRansaction() {
        String txId = "txid";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED);
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        transactionService.approveTransaction(txId);
    }

    @Test
    public void deleteTransaction() {
        String txId = "txId";
        transactionService.deleteTransaction(txId);
        Mockito.verify(persistenceLayer).deleteTransaction(txId);
    }

    @Test
    public void approveCashback() {
        String txId = "txId";
        String remarks = "Approving cashback";
        TransactionDTO transactionDTO = new TransactionDTO();
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.approveCashback(txId, remarks);
        assertEquals(1, result.getModeratorRemark().size());
        assertTrue(result.getModeratorRemark().get(0).contains(remarks));
        assertEquals(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED, result.getTransactionState());
    }

    @Test
    public void rejectCashback() {
        String txId = "txId";
        String remarks = "Rejecting cashback";
        TransactionDTO transactionDTO = new TransactionDTO();
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.rejectCashback(txId, remarks);
        assertEquals(1, result.getModeratorRemark().size());
        assertTrue(result.getModeratorRemark().get(0).contains(remarks));
        assertEquals(AbstractTransaction.TRANSACTION_STATE.CREDIT_CANCELLED, result.getTransactionState());
    }

    @Test
    public void createTransactionHold() {
        String txId = "txId";
        String remarks = "Holding cashback";
        TransactionDTO transactionDTO = new TransactionDTO();
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.createTransactionHold(txId, remarks);
        assertEquals(1, result.getModeratorRemark().size());
        assertTrue(result.getModeratorRemark().get(0).contains(remarks));
        assertEquals(AbstractTransaction.TRANSACTION_STATE.HOLD, result.getTransactionState());
    }

    @Test
    public void createRedeemRequest() {
        String userId = "userId";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(userId);
        UserDTO userDTO = new UserDTO();
        int page = 0;
        int max = -1;
        List<AbstractTransaction> transactionList = new ArrayList<>();
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionList);
        when(userService.getUser(userId)).thenReturn(userDTO);
        PowerMockito.when(persistenceLayer.createTransaction(transactionDTO)).thenReturn(transactionDTO);
        TransactionDTO result = transactionService.createRedeemRequest(transactionDTO);
        assertEquals(AbstractTransaction.TRANSACTION_KIND.DEBIT, result.getTransactionKind());
        assertEquals(AbstractTransaction.TRANSACTION_STATE.REDEEM_PENDING, transactionDTO.getTransactionState());
        assertEquals(0, transactionDTO.getAwardedAmount(), 0.0);
    }

    @Test
    public void approveRedeem() {
        String txId = "txId";
        String remarks = "Approving redeem";
        TransactionDTO transactionDTO = new TransactionDTO();
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.approveRedeem(txId, remarks);
        assertEquals(1, result.getModeratorRemark().size());
        assertTrue(result.getModeratorRemark().get(0).contains(remarks));
        assertEquals(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED, result.getTransactionState());
    }

    @Test
    public void rejectRedeem() {
        String txId = "txId";
        String remarks = "Approving redeem";
        TransactionDTO transactionDTO = new TransactionDTO();
        when(persistenceLayer.getTransaction(txId)).thenReturn(transactionDTO);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        TransactionDTO result = transactionService.rejectRedeem(txId, remarks);
        assertEquals(1, result.getModeratorRemark().size());
        assertTrue(result.getModeratorRemark().get(0).contains(remarks));
        assertEquals(AbstractTransaction.TRANSACTION_STATE.REDEEM_CANCELLED, result.getTransactionState());
    }

    @Test
    public void testRebalance() {
        List<AbstractTransaction> transactionDTOS = new ArrayList<>();

        //Approved Credits
        for (int i = 0; i < 50; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAwardedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED);
            transactionDTOS.add(transactionDTO);
        }
        //Approved Redeem
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setRedeemedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED);
            transactionDTOS.add(transactionDTO);
        }

        //Pending Redeem
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setRedeemedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_PENDING);
            transactionDTOS.add(transactionDTO);
        }

        //Pending Cashback
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAwardedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_PENDING);
            transactionDTOS.add(transactionDTO);
        }

        //Rejected Cashback
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAwardedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_CANCELLED);
            transactionDTOS.add(transactionDTO);
        }

        //Rejected redeem
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setRedeemedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_CANCELLED);
            transactionDTOS.add(transactionDTO);
        }

        //ON HOld transactions
        for (int i = 0; i < 10; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAwardedAmount(10);
            transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
            transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.HOLD);
            transactionDTOS.add(transactionDTO);
        }
        String userId = "userId";
        UserDTO userDTO = mock(UserDTO.class);
        int page = 0;
        int max = -1;
        when(userService.getUser(userId)).thenReturn(userDTO);
        when(userService.updateUser(userId, userDTO)).thenReturn(userDTO);
        when(persistenceLayer.getUsersTransactions(userId, page, max)).thenReturn(transactionDTOS);
        int totalAmount = 600;
        int totalPendingAmount = 100;
        int totalRedeemedAmount = 100;
        int totalPendingRedeemAmount = 100;
        int totalRedeemableAmountLeft = 300;
        UserDTO.TransactionSummary transactionSummary = new AbstractUser.TransactionSummary(totalAmount,
            totalPendingAmount, totalRedeemedAmount, totalPendingRedeemAmount, totalRedeemableAmountLeft);
        String txId = "txId";
        TransactionDTO dto = new TransactionDTO();
        dto.setUserId(userId);
        when(persistenceLayer.getTransaction(txId)).thenReturn(dto);
        when(persistenceLayer.updateTransaction(any(TransactionDTO.class), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        transactionService.createTransactionHold(txId, "rebalance test");
        Mockito.verify(userDTO).setTransactionSummary(transactionSummary);
    }
}