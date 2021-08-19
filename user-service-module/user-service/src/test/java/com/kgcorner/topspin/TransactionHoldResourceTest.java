package com.kgcorner.topspin;

import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.services.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/08/21
 */

public class TransactionHoldResourceTest {

    @Test
    public void createTransactionHold() {
        TransactionService transactionService = PowerMockito.mock(TransactionService.class);
        String tId = "tId";
        String remarks = "remarks";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.HOLD);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        PowerMockito.when(transactionService.createTransactionHold(tId, remarks)).thenReturn(transactionDTO);
        TransactionHoldResource resource = new TransactionHoldResource();
        Whitebox.setInternalState(resource, "transactionService", transactionService);
        ResponseEntity<TransactionDTO> transactionHold = resource.createTransactionHold(tId, remarks);
        Assert.assertEquals(HttpStatus.CREATED, transactionHold.getStatusCode());
        Assert.assertEquals(transactionDTO, transactionHold.getBody());
        Assert.assertTrue(transactionHold.getBody().getLinks().get(0).getHref().contains("cashback"));
    }

    @Test
    public void createTransactionHoldForDebit() {
        TransactionService transactionService = PowerMockito.mock(TransactionService.class);
        String tId = "tId";
        String remarks = "remarks";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.HOLD);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
        PowerMockito.when(transactionService.createTransactionHold(tId, remarks)).thenReturn(transactionDTO);
        TransactionHoldResource resource = new TransactionHoldResource();
        Whitebox.setInternalState(resource, "transactionService", transactionService);
        ResponseEntity<TransactionDTO> transactionHold = resource.createTransactionHold(tId, remarks);
        Assert.assertEquals(HttpStatus.CREATED, transactionHold.getStatusCode());
        Assert.assertEquals(transactionDTO, transactionHold.getBody());
        Assert.assertTrue(transactionHold.getBody().getLinks().get(0).getHref().contains("redeem"));
    }
}