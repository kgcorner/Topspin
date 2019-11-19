package com.kgcorner.crypto;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/11/19
 */

public class BigStringGeneratorTest {

    @Test
    public void generateBigString() {
        String token = BigStringGenerator.generateBigString();
        assertNotNull("generated token is null", token);
    }
}