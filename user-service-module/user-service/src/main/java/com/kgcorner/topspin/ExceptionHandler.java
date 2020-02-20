package com.kgcorner.topspin;


import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.models.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleCustomException(ForbiddenException e) {
        BaseResponse errorResponse = new BaseResponse(e.getLocalizedMessage(), BaseResponse.RESPONSETYPE.ERROR);
        return new ResponseEntity(errorResponse, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<String> handleCustomException(ResourceNotFoundException e) {
        BaseResponse errorResponse = new BaseResponse(e.getLocalizedMessage(), BaseResponse.RESPONSETYPE.ERROR);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
}