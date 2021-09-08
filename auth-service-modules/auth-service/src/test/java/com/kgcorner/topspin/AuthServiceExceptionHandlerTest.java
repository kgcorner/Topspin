package com.kgcorner.topspin;

import com.kgcorner.topspin.exception.UnAuthorizeException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/21
 */

public class AuthServiceExceptionHandlerTest {

    @Test
    public void handleCustomException() {
        Assert.assertNotNull(new AuthServiceExceptionHandler().handleCustomException(new UnAuthorizeException("Test Exception")));
    }
}