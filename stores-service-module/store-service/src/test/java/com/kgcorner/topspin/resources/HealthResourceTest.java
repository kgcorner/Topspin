package com.kgcorner.topspin.resources;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/05/21
 */

public class HealthResourceTest {
    @Test
    public void testHealth() {
        assertEquals("ok", new HealthResource().ok());
    }
}