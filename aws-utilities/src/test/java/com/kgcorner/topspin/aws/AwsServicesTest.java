package com.kgcorner.topspin.aws;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 11/08/21
 */

public class AwsServicesTest {

    @Test
    public void getInstance() {
        assertEquals(AwsServices.getInstance(), AwsServices.getInstance());
    }
}