package com.kgcorner.models;

/*
Description : BaseResponse
Author: kumar
Created on : 07/11/19
*/

public class BaseResponse {
    public enum RESPONSETYPE {
        ERROR,
        SUCCESS
    }
    private String message;
    private RESPONSETYPE type;
    private int successCode;
    private int status; //Http Status

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public RESPONSETYPE getType() {
        return type;
    }
    public void setType(RESPONSETYPE type) {
        this.type = type;
        if(type == RESPONSETYPE.SUCCESS && (status >= 400 || status == 0))
            status = 200;
        else
            if(status < 400)
                status = 400;
        if(type == RESPONSETYPE.SUCCESS && status < 400) {
            successCode = 0;
        }
        else {
            successCode = 1;
        }
    }
    public BaseResponse(String message, RESPONSETYPE type) {
        this.message = message;
        this.type = type;
        if(type == RESPONSETYPE.SUCCESS && (status >= 400 || status == 0))
            status = 200;
        else
            if(status < 400)
                status = 400;
        if(type == RESPONSETYPE.SUCCESS || status < 400) {
            successCode = 0;
        }
        else {
            successCode = 1;
        }
    }
    public int getSuccessCode() {
        return successCode;
    }
    public int getHttpStatus() {
        return status;
    }
    public void setHttpStatus(int httpStatus) {
        this.status = httpStatus;
        if(type == RESPONSETYPE.SUCCESS && status < 400) {
            successCode = 0;
        }
        else {
            successCode = 1;
        }
    }
}