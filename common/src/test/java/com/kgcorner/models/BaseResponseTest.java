package com.kgcorner.models;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

public class BaseResponseTest {

    @Test
    public void baseResponseTest () {
        BaseResponse response = new BaseResponse("Test", BaseResponse.RESPONSETYPE.SUCCESS);
        assertEquals("Test", response.getMessage());
        assertEquals(200, response.getHttpStatus());
        assertEquals(0, response.getSuccessCode());
        assertEquals(BaseResponse.RESPONSETYPE.SUCCESS, response.getType());
        response.setHttpStatus(400);
        assertEquals(400, response.getHttpStatus());
        assertEquals(1, response.getSuccessCode());
        response.setMessage("Test1");
        assertEquals("Test1", response.getMessage());
        response.setType(BaseResponse.RESPONSETYPE.ERROR);
        assertEquals(BaseResponse.RESPONSETYPE.ERROR, response.getType());

        response = new BaseResponse("Test", BaseResponse.RESPONSETYPE.ERROR);
        assertEquals(400, response.getHttpStatus());
        assertEquals(1, response.getSuccessCode());
        response.setType(BaseResponse.RESPONSETYPE.SUCCESS);
        assertEquals(BaseResponse.RESPONSETYPE.SUCCESS, response.getType());
    }

}