package com.kgcorner.exceptions;

/*
Description : Should be thrown when user is forbidden from a particular action
Author: kumar
Created on : 07/11/19
*/

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}