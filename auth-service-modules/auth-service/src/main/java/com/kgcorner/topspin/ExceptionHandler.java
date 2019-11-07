package com.kgcorner.topspin;

/*
Description : <Write class Description>
Author: kumar
Created on : 07/11/19
*/

import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.models.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleCustomException(ForbiddenException e) {
        BaseResponse errorResponse = new BaseResponse(e.getLocalizedMessage(), BaseResponse.RESPONSETYPE.ERROR);
        return new ResponseEntity(errorResponse, HttpStatus.FORBIDDEN);
    }
}