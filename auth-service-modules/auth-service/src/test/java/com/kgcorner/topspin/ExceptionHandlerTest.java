package com.kgcorner.topspin;

import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.models.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

public class ExceptionHandlerTest {
    private ExceptionHandler exceptionHandler;

    @Before
    public void setup() {
        exceptionHandler = new ExceptionHandler();
    }

    @Test
    public void handleCustomException() {
        ForbiddenException exception = new ForbiddenException("Forbidden");
        ResponseEntity<String> response = exceptionHandler.handleCustomException(exception);
        Assert.assertNotNull(response);
        Assert.assertEquals("Status is not matching", response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}