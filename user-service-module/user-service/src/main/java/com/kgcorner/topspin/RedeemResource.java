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
public class RedeemResource {

    private static final String REDEEMS_REDEEM_ID = "/redeems/{redeemId}";

    @Autowired
    private TransactionService transactionService;

    @ApiOperation("Award a point to user")
    @PostMapping("/redeem")
    public ResponseEntity<TransactionDTO> createRedeemRequest(@RequestBody TransactionDTO transactionDTO) {
        transactionDTO = transactionService.createRedeemRequest(transactionDTO);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.CREATED);
    }

    @ApiOperation("Award a point to user")
    @GetMapping(REDEEMS_REDEEM_ID)
    public ResponseEntity<TransactionDTO> getCashback(
        @PathVariable("transactionId") String transactionId) {
        TransactionDTO transactionDTO = transactionService.getTransaction(transactionId);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }

    @ApiOperation("Award a point to user")
    @PatchMapping(REDEEMS_REDEEM_ID)
    public ResponseEntity<TransactionDTO> approveCashback(
        @PathVariable("transactionId") String transactionId, @RequestBody String moderatorRemark) {
        TransactionDTO transactionDTO = transactionService.approveRedeem(transactionId, moderatorRemark);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }

    @ApiOperation("Award a point to user")
    @DeleteMapping(REDEEMS_REDEEM_ID)
    public ResponseEntity<TransactionDTO> rejectRedeem(
        @PathVariable("transactionId") String transactionId, @RequestBody String moderatorRemark) {
        TransactionDTO transactionDTO = transactionService.rejectRedeem(transactionId, moderatorRemark);
        return getTransactionDTOResponseEntity(transactionDTO, HttpStatus.OK);
    }



    private ResponseEntity<TransactionDTO> getTransactionDTOResponseEntity(TransactionDTO transactionDTO
        , HttpStatus status) {
        transactionDTO.addLink(REDEEMS_REDEEM_ID.replace("{redeemId}", transactionDTO.getId()),
            Link.REL_SELF);
        return ResponseEntity.status(status).body(transactionDTO);
    }
}