package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class SCHEMESTest {

    @Test
    public void schemeTest() {
        Assert.assertEquals( "BASIC", SCHEMES.BASIC);
        Assert.assertEquals( "BEARER", SCHEMES.BEARER);
    }
}