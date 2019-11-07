package com.kgcorner.exceptions;

/*
Description : <Write class Description>
Author: kumar
Created on : 29/10/19
*/

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(){
        this("no such resource found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}