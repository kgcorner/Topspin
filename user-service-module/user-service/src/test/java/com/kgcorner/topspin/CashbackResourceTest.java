package com.kgcorner.topspin;

import com.kgcorner.topspin.dto.TransactionDTO;
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

public class CashbackResourceTest {
    private CashbackResource cashbackResource;
    private TransactionService transactionService;
    public static final String CASHBACKS_CASHBACK_ID = "/cashbacks/{cashbackId}";

    @Before
    public void setUp() throws Exception {
        transactionService = mock(TransactionService.class);
        cashbackResource = new CashbackResource();
        Whitebox.setInternalState(cashbackResource, "transactionService", transactionService);
    }

    @Test
    public void awardCashbackToUser() {
        String tid = "tid";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(tid);
        when(transactionService.awardCashbackToUser(transactionDTO)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> transactionDTOResponseEntity
            = cashbackResource.awardCashbackToUser(transactionDTO);
        assertEquals(HttpStatus.CREATED, transactionDTOResponseEntity.getStatusCode());
        assertEquals(transactionDTO, transactionDTOResponseEntity.getBody());
        assertEquals(CASHBACKS_CASHBACK_ID.replace("{cashbackId}", tid),
            transactionDTOResponseEntity.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void getCashback() {
        String tid = "tid";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(tid);
        when(transactionService.getTransaction(tid)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> transactionDTOResponseEntity
            = cashbackResource.getCashback(tid);
        assertEquals(HttpStatus.OK, transactionDTOResponseEntity.getStatusCode());
        assertEquals(transactionDTO, transactionDTOResponseEntity.getBody());
        assertEquals(CASHBACKS_CASHBACK_ID.replace("{cashbackId}", tid),
            transactionDTOResponseEntity.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void approveCashback() {
        String tid = "tid";
        String remarks = "remarks";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(tid);
        when(transactionService.approveCashback(tid, remarks)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> transactionDTOResponseEntity
            = cashbackResource.approveCashback(tid, remarks);
        assertEquals(HttpStatus.OK, transactionDTOResponseEntity.getStatusCode());
        assertEquals(transactionDTO, transactionDTOResponseEntity.getBody());
        assertEquals(CASHBACKS_CASHBACK_ID.replace("{cashbackId}", tid),
            transactionDTOResponseEntity.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void rejectCashback() {
        String tid = "tid";
        String remarks = "remarks";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(tid);
        when(transactionService.rejectCashback(tid, remarks)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> transactionDTOResponseEntity
            = cashbackResource.rejectCashback(tid, remarks);
        assertEquals(HttpStatus.OK, transactionDTOResponseEntity.getStatusCode());
        assertEquals(transactionDTO, transactionDTOResponseEntity.getBody());
        assertEquals(CASHBACKS_CASHBACK_ID.replace("{cashbackId}", tid),
            transactionDTOResponseEntity.getBody().getLinks().get(0).getHref());
    }
}