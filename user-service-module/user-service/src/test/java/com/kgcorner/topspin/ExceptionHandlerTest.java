package com.kgcorner.topspin;

import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/03/20
 */

public class ExceptionHandlerTest {

    private ExceptionHandler exceptionHandler;

    @Before
    public void setup() {
            exceptionHandler = new ExceptionHandler();
    }

    @Test
    public void handleForbiddenException() {
        ResponseEntity<String> response = exceptionHandler.handleCustomException(new ForbiddenException());
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testResourceNotFoundException() {
        ResponseEntity<String> response = exceptionHandler.handleCustomException(new ResourceNotFoundException());
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testIllegalArgumentExceptionException() {
        ResponseEntity<String> response = exceptionHandler.handleCustomException(new IllegalArgumentException());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}