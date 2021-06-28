package com.kgcorner.topspin;

import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.models.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

public class ExceptionHandlerTest {
    private AuthServiceExceptionHandler exceptionHandler;

    @Before
    public void setup() {
        exceptionHandler = new AuthServiceExceptionHandler();
    }

    @Test
    public void handleCustomException() {
        ForbiddenException exception = new ForbiddenException("Forbidden");
        ResponseEntity<BaseResponse> response = exceptionHandler.handleCustomException(exception);
        Assert.assertNotNull(response);
        Assert.assertEquals("Status is not matching", HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}