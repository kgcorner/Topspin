package com.kgcorner.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

public class ForbiddenExceptionTest {

    @Test
    public void forbiddenException() {
        ForbiddenException exception = new ForbiddenException("Test");
        assertEquals("Test", exception.getMessage());
        exception = new ForbiddenException();
        assertEquals("Forbidden", exception.getMessage());
    }
}