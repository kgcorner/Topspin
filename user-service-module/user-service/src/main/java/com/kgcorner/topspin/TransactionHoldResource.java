package com.kgcorner.topspin;


import com.kgcorner.topspin.dto.TransactionDTO;
import com.kgcorner.topspin.model.AbstractTransaction;
import com.kgcorner.topspin.services.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

@RestController
public class TransactionHoldResource {
    @Autowired
    private TransactionService transactionService;

    @ApiOperation("Create hold on a transaction")
    @PostMapping("/transactions/{transationId}/hold")
    public ResponseEntity<TransactionDTO> createTransactionHold(@ApiParam("id of the transaction")
                                                                    @PathVariable String transactionId,
                                                                @ApiParam("Moderator comment")
                                                                @RequestBody String moderatorRemark
                                                                ) {
        TransactionDTO transactionHold = transactionService.createTransactionHold(transactionId, moderatorRemark);
        if(transactionHold.getTransactionKind() == AbstractTransaction.TRANSACTION_KIND.CREDIT) {
            transactionHold.addLink("/cashbacks/"+transactionId, Link.REL_SELF);
        } else {
            transactionHold.addLink("/redeems/"+transactionId, Link.REL_SELF);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionHold);
    }


}