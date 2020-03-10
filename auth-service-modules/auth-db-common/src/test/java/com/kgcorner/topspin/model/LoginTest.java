package com.kgcorner.topspin.model;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 10/03/20
 */

public class LoginTest {

    @Test
    public void testUserNameKey() {
        assertEquals("username", Login.USER_NAME_COLUMN);
    }
}