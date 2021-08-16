package com.kgcorner.topspin.resources;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/08/21
 */

public class HealthResourceTest {

    @Test
    public void ok() {
        Assert.assertEquals("ok", new HealthResource().ok());
    }
}