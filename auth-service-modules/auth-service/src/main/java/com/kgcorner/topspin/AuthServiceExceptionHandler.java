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
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AuthServiceExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<BaseResponse> handleCustomException(ForbiddenException e) {
        var errorResponse = new BaseResponse(e.getLocalizedMessage(), BaseResponse.RESPONSETYPE.ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleCustomException(IllegalArgumentException e) {
        var errorResponse = new BaseResponse(e.getLocalizedMessage(), BaseResponse.RESPONSETYPE.ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}