package com.kgcorner.topspin;

import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.services.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/08/21
 */

public class RedeemResourceTest {

    private TransactionService transactionService;
    private RedeemResource redeemResource;
    private static final String REDEEMS_REDEEM_ID = "/redeems/{redeemId}";

    @Before
    public void setUp() throws Exception {
        redeemResource = new RedeemResource();
        transactionService = mock(TransactionService.class);
        Whitebox.setInternalState(redeemResource, "transactionService", transactionService);
    }

    @Test
    public void createRedeemRequest() {
        TransactionDTO transactionDTO = new TransactionDTO();
        String tId = "tId";
        transactionDTO.setId(tId);
        when(transactionService.createRedeemRequest(transactionDTO)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> redeemRequest = redeemResource.createRedeemRequest(transactionDTO);
        assertEquals(HttpStatus.CREATED, redeemRequest.getStatusCode());
        assertEquals(transactionDTO, redeemRequest.getBody());
        assertEquals(REDEEMS_REDEEM_ID.replace("{redeemId}", tId),
            redeemRequest.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void getCashback() {
        TransactionDTO transactionDTO = new TransactionDTO();
        String tId = "tId";
        transactionDTO.setId(tId);
        when(transactionService.getTransaction(tId)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> redeemRequest = redeemResource.getCashback(tId);
        assertEquals(HttpStatus.OK, redeemRequest.getStatusCode());
        assertEquals(transactionDTO, redeemRequest.getBody());
        assertEquals(REDEEMS_REDEEM_ID.replace("{redeemId}", tId),
            redeemRequest.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void approveCashback() {
        TransactionDTO transactionDTO = new TransactionDTO();
        String tId = "tId";
        String remark = "remark";
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_COMPLETED);
        transactionDTO.setId(tId);
        when(transactionService.approveRedeem(tId, remark)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> redeemRequest = redeemResource.approveCashback(tId, remark);
        assertEquals(HttpStatus.OK, redeemRequest.getStatusCode());
        assertEquals(transactionDTO, redeemRequest.getBody());
        assertEquals(REDEEMS_REDEEM_ID.replace("{redeemId}", tId),
            redeemRequest.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void rejectCashback() {
        TransactionDTO transactionDTO = new TransactionDTO();
        String tId = "tId";
        String remark = "remark";
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_CANCELLED);
        transactionDTO.setId(tId);
        when(transactionService.rejectRedeem(tId, remark)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> redeemRequest = redeemResource.rejectRedeem(tId, remark);
        assertEquals(HttpStatus.OK, redeemRequest.getStatusCode());
        assertEquals(transactionDTO, redeemRequest.getBody());
        assertEquals(REDEEMS_REDEEM_ID.replace("{redeemId}", tId),
            redeemRequest.getBody().getLinks().get(0).getHref());
    }
}