package com.kgcorner.topspin;


import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.services.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

@RestController
public class CashbackResource {

    public static final String CASHBACKS_CASHBACK_ID = "/cashbacks/{cashbackId}";

    @Autowired
    private TransactionService transactionService;

    @ApiOperation("Award a point to user")
    @PostMapping("/cashbacks")
    public ResponseEntity<TransactionDTO> awardCashbackToUser(@RequestBody TransactionDTO transactionDTO) {
        transactionDTO = transactionService.awardCashbackToUser(transactionDTO);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.CREATED);
    }

    @ApiOperation("Award a point to user")
    @GetMapping(CASHBACKS_CASHBACK_ID)
    public ResponseEntity<TransactionDTO> getCashback(
        @PathVariable("transactionId") String transactionId) {
        TransactionDTO transactionDTO = transactionService.getTransaction(transactionId);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }

    @ApiOperation("Award a point to user")
    @PatchMapping(CASHBACKS_CASHBACK_ID)
    public ResponseEntity<TransactionDTO> approveCashback(
        @PathVariable("transactionId") String transactionId, @RequestBody String moderatorRemark) {
        TransactionDTO transactionDTO = transactionService.approveCashback(transactionId, moderatorRemark);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }

    @ApiOperation("Award a point to user")
    @DeleteMapping(CASHBACKS_CASHBACK_ID)
    public ResponseEntity<TransactionDTO> rejectCashback(
        @PathVariable("transactionId") String transactionId, @RequestBody String moderatorRemark) {
        TransactionDTO transactionDTO = transactionService.rejectCashback(transactionId, moderatorRemark);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }

    private ResponseEntity<TransactionDTO> getTransactionDTOResponseEntity(TransactionDTO transactionDTO
        , HttpStatus status) {
        transactionDTO.addLink(CASHBACKS_CASHBACK_ID.replace("{cashbackId}", transactionDTO.getId()),
            Link.REL_SELF);
        return ResponseEntity.status(status).body(transactionDTO);
    }

}